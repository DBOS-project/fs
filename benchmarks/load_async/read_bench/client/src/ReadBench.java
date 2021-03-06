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
import java.util.HashMap;
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
    private HashMap<Integer, Integer> _p_map;
    private BenchmarkStats _stats;
    private String _username;
    private int _load;
    private int _filecnt;
    private int _filemin;
    private int _partcnt;
    
    public ReadBench (String hostlist, String username, int load, 
                      int filecnt, int filemin, int partcnt)
		throws Exception {

        _username = username;
		_load = load;
		_filecnt = filecnt;
		_filemin = filemin;
        _partcnt = partcnt;
	
        _p_map = new HashMap<>();
	
		// create client
		_client = ClientFactory.createClient();

		// connect to each server listed (separated by commas) in the first argument
		String[] serverArray = hostlist.split(",");
		for (String server : serverArray)
		    _client.createConnection(server);

		_stats = new BenchmarkStats(_client, true);
    }

    public void preprocess () throws Exception {

        VoltTable p_map = _client.callProcedure("PartitionInfoSelect").getResults()[0];

        while (p_map.advanceRow()) {
            int p_key = (int) p_map.getLong(0);
            int p_id = (int) p_map.getLong(1);

            _p_map.put(p_id, p_key);
        }
    }

    public void benchmarkItem(int filenum) throws Exception {

		ProcedureCallback callback = new BenchmarkCallback("Read");
		_client.callProcedure(callback,
							  "Read",
                              _p_map.get((_filemin + filenum % _filecnt) % _partcnt),
							  _username,
							  "file" + String.valueOf(_filemin + filenum % _filecnt)
							  );
    }

    public void runBenchmark() throws Exception {

		// print a heading
		String dashes = new String(new char[80]).replace("\0", "-");
		System.out.println(dashes);
        System.out.println(" Running Performance Benchmark for " + _load + " txs per ms");
		System.out.println(dashes);

		// start recording statistics for the benchmark, outputting every 5 seconds
		_stats.startBenchmark();
        int tx = 0;

        // main loop for the benchmark
        while (true) {

            long next_time = System.currentTimeMillis() + 1;

            // issue _load requests every 1 ms
            for (int i=0; i<_load; i++) {
                benchmarkItem(tx);
                tx++;
            }

            while (System.currentTimeMillis() < next_time)
                ; // busy wait

        }
    }


    public static void main(String[] args) throws Exception {

		// parse args flags
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "hostlist", true, "host servers list, e.g. localhost");
		options.addOption("u", "username", true, "file owner");
		options.addOption("l", "load", true, "load in txpms to be generated");
		options.addOption("f", "filecnt", true, "number of files");
		options.addOption("m", "filemin", true, "first file idx");
		options.addOption("p", "partcnt", true, "number of system partitions");
		CommandLine cmd = parser.parse(options, args);

		String hostlist = "localhost";
		if (cmd.hasOption("hostlist"))
			hostlist = cmd.getOptionValue("hostlist");

		int load = 1;
		if (cmd.hasOption("load"))
			load = Integer.parseInt(cmd.getOptionValue("load"));
		
		String username = "user";
		if (cmd.hasOption("username"))
			username = cmd.getOptionValue("username");

		int filecnt = 1;
		if (cmd.hasOption("filecnt"))
			filecnt = Integer.parseInt(cmd.getOptionValue("filecnt"));
		
		int filemin = 0;
		if (cmd.hasOption("filemin"))
			filemin = Integer.parseInt(cmd.getOptionValue("filemin"));
		
		int partcnt = 1;
		if (cmd.hasOption("partcnt"))
			partcnt = Integer.parseInt(cmd.getOptionValue("partcnt"));
		
		ReadBench benchmark = new ReadBench(hostlist, username, load, 
                                            filecnt, filemin, partcnt);
        benchmark.preprocess();
		benchmark.runBenchmark();
    }
}
