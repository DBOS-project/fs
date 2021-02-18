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

		// create client
		Client client = ClientFactory.createClient();

		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) {
		    client.createConnection(server);
		}

        System.out.println("spill to disk daemon working...");
		while (true) {
			client.callProcedure("CheckStorage", threshold_gb * 1024 * 1024 * 1024);
			Thread.sleep(period_ms);
		}
    }    
}

