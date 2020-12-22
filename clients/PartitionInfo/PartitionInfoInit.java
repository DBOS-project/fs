import java.util.HashMap;
import org.apache.commons.cli.*;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ProcCallException;

public class PartitionInfoInit {

    private Client _client;

	// static HashMap<Integer, Integer> m1 = new HashMap<>();

	public PartitionInfoInit (String hostlist) throws Exception {
		// create client
		_client = ClientFactory.createClient();

		// connect to each server listed (separated by commas) in the first argument
		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) 
		    _client.createConnection(server);
	}

	public void populate () throws Exception {
		VoltTable partition_map = _client.callProcedure("@GetPartitionKeys",
														"integer").getResults()[0];
		while(partition_map.advanceRow()) {
			int p_id = (int) partition_map.getLong(0);
			int p_key = (int) partition_map.getLong(1);

			_client.callProcedure("PartitionInfoInsert",
								  p_id, p_key, -1, "");
		}

		String query = "SELECT partition_id, host_id, hostname" +
			" from statistics(table, 0) where table_name = 'FILE';";
		VoltTable host_map = _client.callProcedure("@QueryStats",
												   query).getResults()[0];
		while(host_map.advanceRow()) {
			int p_id = (int) host_map.getLong(0);
			int host_id  = (int) host_map.getLong(1);
			String host_name  = host_map.getString(2);

			_client.callProcedure("PartitionInfoUpdate",
								  p_id, host_id, host_name);
		}
	}
	
	public static void main(String[] args) throws Exception {		
		// parse args flags
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
		CommandLine cmd = parser.parse(options, args);

		String hostlist = "localhost";
		if (cmd.hasOption("hostlist"))
			hostlist = cmd.getOptionValue("hostlist");

		PartitionInfoInit pinfo = new PartitionInfoInit(hostlist);
		pinfo.populate();
	}
}
		
