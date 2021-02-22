package org.voltdb.daemon;

import org.apache.commons.cli.*;
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;
import java.nio.charset.StandardCharsets;



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

		int batch_size = 10;
		if (cmd.hasOption("batch_size"))
			batch_size = Integer.parseInt(cmd.getOptionValue("batch_size"));

		// create client
		Client client = ClientFactory.createClient();

		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) {
		    client.createConnection(server);
		}

        System.out.println("spill to disk daemon working...");
		while (true) {
			// long startTime = System.currentTimeMillis();
			client.callProcedure("CheckStorage", threshold_gb, batch_size);
			// long endTime = System.currentTimeMillis();
			// System.out.println("Took " + (endTime - startTime) + " milliseconds");
			Thread.sleep(period_ms);
		}
    }    
}

