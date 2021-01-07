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
		options.addOption("t", "threshold", true, "the threshold at which files should be moved to the raw disk");
		options.addOption("f", "frequency", true, "how often to check");

		CommandLine cmd = parser.parse(options, args);

		String hostlist = "localhost";
		if (cmd.hasOption("hostlist"))
			hostlist = cmd.getOptionValue("hostlist");

		int threshold = 5;
		if (cmd.hasOption("threshold"))
			threshold = Integer.parseInt(cmd.getOptionValue("threshold"));

		int freq = 5;
		if (cmd.hasOption("frequency"))
			freq = Integer.parseInt(cmd.getOptionValue("frequency"));

		// create client
		Client client = ClientFactory.createClient();

		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) {
		    client.createConnection(server);
		}

		while (true) {
			VoltTable[] results = client.callProcedure("Check_Storage", threshold).getResults();
			if (results.length > 0) {
				VoltTableRow oldest = results[0].fetchRow(0);
				byte[] bytes = oldest.getVarbinary("bytes");
				String data = new String(bytes, StandardCharsets.UTF_8);
				long p_key = oldest.getLong("p_key");
				String user = oldest.getString("user_name");
				String file_name = oldest.getString("file_name");
				String file = file_name.substring(file_name.indexOf(user) + user.length() + 1);

				client.callProcedure("Create_Big", user, file);
				client.callProcedure("Write_Big", user, file, data);
				client.callProcedure("Delete", p_key, user, file_name);
			}
			
			Thread.sleep(freq * 1000);
		}
    }
    
}

