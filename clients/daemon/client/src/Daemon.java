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
   private VoltTableRow _file_info;

   public SendToDiskWorker(Client client, VoltTableRow file_info) {
	_client = client;
	_file_info = file_info;
   }

   public void run() {
	long p_key = _file_info.getLong("p_key");
	String user_name = _file_info.getString("user_name");
	String file_name = _file_info.getString("file_name");
	long block_number = _file_info.getLong("block_number");
	try {
		_client.callProcedure("SendToDisk", p_key, user_name, file_name, block_number);
	} catch (Exception e) {
		System.out.println("failed to write");
	}
   }
}

public class Daemon {

	public static void main(String[] args) throws Exception {
		// parse args flags
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
		options.addOption("t", "threshold_gb", true, "the threshold of data in memory");
		options.addOption("p", "period_ms", true, "check period in miliseconds");
		options.addOption("b", "batch_size", true, "how many blocks to move in a 1 round");

		CommandLine cmd = parser.parse(options, args);

		String hostlist = "localhost";
		if (cmd.hasOption("hostlist"))
			hostlist = cmd.getOptionValue("hostlist");

		long threshold_gb = 320;
		if (cmd.hasOption("threshold_gb"))
			threshold_gb = Long.parseLong(cmd.getOptionValue("threshold_gb"));

		int period_ms = 1000;
		if (cmd.hasOption("period_ms"))
			period_ms = Integer.parseInt(cmd.getOptionValue("period_ms"));

		int batch_size = 20;
		if (cmd.hasOption("batch_size"))
			batch_size = Integer.parseInt(cmd.getOptionValue("batch_size"));

		// create client
		Client client = ClientFactory.createClient();

		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) {
		    client.createConnection(server);
		}

		// create clients for workers
		Client[] worker_clients = new Client[batch_size];
		for (int i =0; i < batch_size; i++) {
			Client worker_client = ClientFactory.createClient();
                	for (String server : serverArray) {
                    		worker_client.createConnection(server);
                	}
			worker_clients[i] = worker_client;
		}

        	System.out.println("spill to disk daemon working...");
		while (true) {
			// long startTime = System.currentTimeMillis();

			// if the amount of memory being used is greater than the threshold, then this call will return
			// a list of the size batch_size of the oldest blocks
			VoltTable[] response = client.callProcedure("CheckStorage", threshold_gb * 1024 * 1024 * 1024, batch_size).getResults();
			if (response.length > 0) {
				// send the oldest blocks to disk
				VoltTable oldest = response[0];
				for (int i = 0; i < oldest.getRowCount(); i++) {
					Runnable worker = new SendToDiskWorker(worker_clients[i], oldest.fetchRow(i));
					new Thread(worker).start();
				}
			}
			// long endTime = System.currentTimeMillis();
			// System.out.println("Took " + (endTime - startTime) + " milliseconds");
			Thread.sleep(period_ms);
		}
    }    
}

