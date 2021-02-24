package org.voltdb.daemon;

import org.apache.commons.cli.*;
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;
import java.nio.charset.StandardCharsets;

class SendToDiskWorker implements Runnable {

   private Client _client;
   private int[] _p_keys;
   private String[] _user_names;
   private String[] _file_names;
   private int[] _block_numbers;

   public SendToDiskWorker(Client client, int[] p_keys, String[] user_names, String[] file_names, int[] block_numbers) {
   	_client = client;
	_p_keys = p_keys;
    _user_names = user_names;
    _file_names = file_names;
    _block_numbers = block_numbers;
   }

   public void run() {
	for (int i = 0; i < _p_keys.length; i++) {
		if (_user_names[i] == null) {
			return;
		}
		// send this file to disk
		try {
			_client.callProcedure("SendToDisk", _p_keys[i], _user_names[i], _file_names[i], _block_numbers[i]);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.format("failed to write: p_key = %d, user_name = %s, file_name = %s, block_number = %d\n",
				_p_keys[i], _user_names[i], _file_names[i], _block_numbers[i]);
		}
	}
   }
}

public class Daemon {

	public static void main(String[] args) throws Exception {
		// parse args flags
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "host", true, "host server, e.g. localhost");
		options.addOption("t", "threshold_gb", true, "the threshold of data in memory");
		options.addOption("p", "period_ms", true, "check period in miliseconds");
		options.addOption("w", "workers_cnt", true, "how many workers to use");
		options.addOption("b", "batch_size", true, "how many blocks to move in a 1 round per worker");

		CommandLine cmd = parser.parse(options, args);

		String host = "localhost";
		if (cmd.hasOption("host"))
			host = cmd.getOptionValue("host");

		long threshold_gb = 320;
		if (cmd.hasOption("threshold_gb"))
			threshold_gb = Long.parseLong(cmd.getOptionValue("threshold_gb"));

		int period_ms = 1000;
		if (cmd.hasOption("period_ms"))
			period_ms = Integer.parseInt(cmd.getOptionValue("period_ms"));

		int batch_size = 10;
		if (cmd.hasOption("batch_size"))
			batch_size = Integer.parseInt(cmd.getOptionValue("batch_size"));

		int worker_cnt = 40;
		if (cmd.hasOption("worker_cnt"))
			worker_cnt = Integer.parseInt(cmd.getOptionValue("worker_cnt"));

		// create client
		Client client = ClientFactory.createClient();

		client.createConnection(host);

		// create clients for workers
		Client[] worker_clients = new Client[worker_cnt];
		for (int i = 0; i < worker_cnt; i++) {
			Client worker_client = ClientFactory.createClient();
            worker_client.createConnection(host);
			worker_clients[i] = worker_client;
		}

        System.out.println("spill to disk daemon working...");
		while (true) {
			// long startTime = System.currentTimeMillis();

			// if the amount of memory being used is greater than the threshold, then this call will return
			// a list of the size batch_size of the oldest blocks
			VoltTable[] response = client.callProcedure("CheckStorage", host, threshold_gb * 1024 * 1024 * 1024, batch_size * worker_cnt).getResults();
			if (response.length > 0) {
				// send the oldest blocks to disk
				VoltTable oldest = response[0];
				int total = oldest.getRowCount();
				Thread[] worker_threads = new Thread[worker_cnt];
				// have each worker process <batch_size> files
				for (int i = 0; i < worker_cnt; i++) {
					if (total > i * batch_size) {
						// get file info to send to worker
						int[] p_keys = new int[batch_size];
						String[] user_names = new String[batch_size];
						String[] file_names = new String[batch_size];
						int[] block_numbers = new int[batch_size];

						VoltTableRow file_info = oldest.fetchRow(i * batch_size);						
						for (int j = 0; j < batch_size; j++) {
							p_keys[j] = (int)file_info.getLong("p_key");
							user_names[j] = file_info.getString("user_name");
							file_names[j] = file_info.getString("file_name");
							block_numbers[j] = (int)file_info.getLong("block_number");
							if (!file_info.advanceRow()) {
								break;
							}
						}
						Runnable worker = new SendToDiskWorker(worker_clients[i], p_keys, user_names, file_names, block_numbers);
						Thread worker_thread = new Thread(worker);
						worker_thread.start();
						worker_threads[i] = worker_thread;
					} else {
						break;
					}
				}
				// wait for workers
				for (int i = 0; i < worker_cnt; i++) {
					if (worker_threads[i] != null) {
						worker_threads[i].join();
					} else {
						break;
					}
				}
			}
			// long endTime = System.currentTimeMillis();
			// System.out.println("Took " + (endTime - startTime) + " milliseconds");
			Thread.sleep(period_ms);
		}
    }    
}

