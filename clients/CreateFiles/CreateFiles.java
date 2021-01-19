import org.apache.commons.cli.*;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ProcCallException;

public class CreateFiles {

    private Client _client;
    private int _filecnt;
    private int _sites;

    public CreateFiles (String hostlist, int filecnt, int totalsites) throws Exception {
        _filecnt = filecnt;
        _sites = totalsites;

        // create client
        _client = ClientFactory.createClient();

        // connect to each server listed (separated by commas) in the first argument
        String[] serverArray = hostlist.split(",");
        for (String server : serverArray) 
            _client.createConnection(server);
    }

    public void create () throws Exception {
        for (int i=0; i<_filecnt; i++) {
            _client.callProcedure("CreateAt",
                                  i % _sites,
                                  "user" + String.valueOf(i % _sites),
                                  "file" + String.valueOf(i)
                                  );
        }
    }
    
    public static void main(String[] args) throws Exception {       
        // parse args flags
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
        options.addOption("f", "filecnt", true, "number of files to create");
        options.addOption("t", "totalsites", true, "number of total system sites/partitions");
        CommandLine cmd = parser.parse(options, args);

        String hostlist = "localhost";
        if (cmd.hasOption("hostlist"))
            hostlist = cmd.getOptionValue("hostlist");

        int filecnt = 1;
        if (cmd.hasOption("filecnt"))
            filecnt = Integer.parseInt(cmd.getOptionValue("filecnt"));

        int totalsites = 40;
        if (cmd.hasOption("totalsites"))
            totalsites = Integer.parseInt(cmd.getOptionValue("totalsites"));

        CreateFiles file_creator = new CreateFiles(hostlist, filecnt, totalsites);
        file_creator.create();
    }
}
        
