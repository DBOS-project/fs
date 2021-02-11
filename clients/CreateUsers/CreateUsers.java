import org.apache.commons.cli.*;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ProcCallException;

public class CreateUsers {

    private Client _client;
    private int _usercnt;
    private int _sites;

    public CreateUsers (String hostlist, int usercnt, int totalsites)
        throws Exception {

        _usercnt = usercnt;
        _sites = totalsites;

        // create client
        _client = ClientFactory.createClient();

        // connect to each server listed (separated by commas) in the first argument
        String[] serverArray = hostlist.split(",");
        for (String server : serverArray) 
            _client.createConnection(server);
    }

    public void create () throws Exception {
        VoltTable partition_map = _client.callProcedure("GetPartitionInfo").getResults()[0];
        while (partition_map.advanceRow()) {
            int p_key = (int) partition_map.getLong(0);
            int p_id  = (int) partition_map.getLong(1);

            int user_num = p_id;
            while (user_num < _usercnt) {
                create_one(p_key, user_num);
                user_num += _sites;
            }
        }               
    }

    public void create_one (int partition, int i) throws Exception {
        _client.callProcedure("CreateUser",
                              partition,
                              "user" + String.valueOf(i)
                              );
    }
    
    public static void main(String[] args) throws Exception {       
        // parse args flags
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
        options.addOption("u", "usercnt", true, "number of users to create");
        options.addOption("p", "totalsites", true, "number of total system sites/partitions");
        CommandLine cmd = parser.parse(options, args);

        String hostlist = "localhost";
        if (cmd.hasOption("hostlist"))
            hostlist = cmd.getOptionValue("hostlist");

        int usercnt = 1;
        if (cmd.hasOption("usercnt"))
            usercnt = Integer.parseInt(cmd.getOptionValue("usercnt"));

        int totalsites = 40;
        if (cmd.hasOption("totalsites"))
            totalsites = Integer.parseInt(cmd.getOptionValue("totalsites"));

        CreateUsers user_creator = new CreateUsers(hostlist, usercnt, totalsites);
        user_creator.create();
    }
}
        
