/* This file is part of VoltDB.
 * Copyright (C) 2008-2020 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
/*
 * This class can be customized for simple tests or micro-benchmarks by modifying the
 * benchmarkItem method which generates random parameter values and calls a procedure
 *
 */

package org.voltdb.read;

import java.util.Random;
import org.apache.commons.cli.*;
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;
import org.voltdb.util.BenchmarkCallback;
import org.voltdb.util.BenchmarkStats;

public class ReadBench {

    private Client _client;
    private BenchmarkStats _stats;
    private Random _rand;
    private int _time_sec;
    private int _partition;
    private int _filecnt;
    private int _blockcnt;
    // private int _random_blockcnt;
    private String _username;
    
    public ReadBench (String hostlist, int time_sec, int filecnt, int blockcnt,
                      // int random_blockcnt,
                      String username)
		throws Exception {

		_time_sec = time_sec;
        _filecnt = filecnt;
        _blockcnt = blockcnt;
        // _random_blockcnt = random_blockcnt;
        _username = username;

        _rand = new Random(11);
	
		// create client
		_client = ClientFactory.createClient();

		// connect to each server listed (separated by commas) in the first argument
		String[] serverArray = hostlist.split(",");
		for (String server : serverArray)
		    _client.createConnection(server);

		_stats = new BenchmarkStats(_client, true);
    }

    public void preprocess() throws Exception {

        VoltTable user_info = _client.callProcedure("GetUserPartition",
                                                    _username
                                                    ).getResults()[0];
        _partition = (int) user_info.fetchRow(0).getLong(0);
    }

    public void benchmarkItem (int fnm1, int fnm2, int fnm3, int fnm4, int fnm5, int blocknum)
        throws Exception {
		_client.callProcedure("ReadNFiles1Block",
                              _partition,
                              "file" + String.valueOf(fnm1), "file" + String.valueOf(fnm2),
                              "file" + String.valueOf(fnm3), "file" + String.valueOf(fnm4),
                              "file" + String.valueOf(fnm5), blocknum,
                              _username
							  );
    }

    public void runBenchmark() throws Exception {

		// print a heading
		String dashes = new String(new char[80]).replace("\0", "-");
		System.out.println(dashes);
		System.out.println(" Running Performance Benchmark for " + _time_sec + " seconds");
		System.out.println(dashes);

        int txs = 0;
        long start_time = System.currentTimeMillis();
        int fnm1, fnm2, fnm3, fnm4, fnm5, rand_block_idx;

        // start recording statistics for the benchmark, outputting every 5 seconds
        _stats.startBenchmark();

        // main loop for the benchmark
        while (true) {
            // pick blocks at random
            fnm1 = _rand.nextInt(_filecnt);
            fnm2 = _rand.nextInt(_filecnt);
            fnm3 = _rand.nextInt(_filecnt);
            fnm4 = _rand.nextInt(_filecnt);
            fnm5 = _rand.nextInt(_filecnt);

            // pick a block at random
            rand_block_idx = _rand.nextInt(_blockcnt);

            benchmarkItem(fnm1, fnm2, fnm3, fnm4, fnm5, rand_block_idx);
            txs++;

            if (txs % 10000 == 0)
                if (System.currentTimeMillis() - start_time > _time_sec * 1000)
                    break;
        }

		// stop recording, print stats
		_stats.endBenchmark();

		// wait for any outstanding responses to return before closing the client
		_client.drain();
		_client.close();

		// print the transaction results tracked by BenchmarkCallback
		BenchmarkCallback.printAllResults();
    }


    public static void main(String[] args) throws Exception {

        // parse args flags
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
        options.addOption("t", "time_sec", true, "running time of benchmark in seconds");
        options.addOption("f", "filecnt", true, "number of files");
        options.addOption("b", "blockcnt", true, "number of blocks per file");
        // options.addOption("r", "randblockcnt", true, "number of random blocks to access");
        options.addOption("u", "username", true, "file owner");
        CommandLine cmd = parser.parse(options, args);

        String hostlist = "localhost";
        if (cmd.hasOption("hostlist"))
            hostlist = cmd.getOptionValue("hostlist");

        int time_sec = 1;
        if (cmd.hasOption("time_sec"))
            time_sec = Integer.parseInt(cmd.getOptionValue("time_sec"));
        
        int filecnt = 1;
        if (cmd.hasOption("filecnt"))
            filecnt = Integer.parseInt(cmd.getOptionValue("filecnt"));
        
        int blockcnt = 1;
        if (cmd.hasOption("blockcnt"))
            blockcnt = Integer.parseInt(cmd.getOptionValue("blockcnt"));
        
        // int random_blockcnt = 1;
        // if (cmd.hasOption("randblockcnt"))
        //     random_blockcnt = Integer.parseInt(cmd.getOptionValue("randblockcnt"));
        
        String username = "user";
        if (cmd.hasOption("username"))
            username = cmd.getOptionValue("username");

        ReadBench benchmark = new ReadBench(hostlist, time_sec, filecnt, blockcnt,
                                            // random_blockcnt,
                                            username);
        benchmark.preprocess();
        benchmark.runBenchmark();
    }
}
