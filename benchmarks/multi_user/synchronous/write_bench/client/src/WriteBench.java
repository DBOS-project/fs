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

package org.voltdb.write;

import java.util.Random;
import org.apache.commons.cli.*;
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;
import org.voltdb.util.BenchmarkCallback;
import org.voltdb.util.BenchmarkStats;

public class WriteBench {

    private Client _client;
    private BenchmarkStats _stats;
    private int _transactions;
    private int _filecnt;
    private int _filesize;
    
    public WriteBench (String hostlist, int transactions, int filecnt, int filesize)
		throws Exception {

		this._transactions = transactions;
		this._filecnt = filecnt;
		this._filesize = filesize;
	
		// create client
		_client = ClientFactory.createClient();

		// connect to each server listed (separated by commas) in the first argument
		String[] serverArray = hostlist.split(",");
		for (String server : serverArray) {
		    _client.createConnection(server);
		}

		_stats = new BenchmarkStats(_client, true);
    }

    public void benchmarkItem(int filenum) throws Exception {

		// To make an asynchronous procedure call, you need a callback object
		// BenchmarkCallback is a generic callback that keeps track of the transaction results
		// for any given procedure name, which should match the procedure called below.
		ProcedureCallback callback = new BenchmarkCallback("Write");
	
		// call the procedure asynchronously, passing in the callback and the procedure name,
		// followed by the input parameters
		_client.callProcedure(callback,
							  "Populate",
							  "user" + String.valueOf(filenum % _filecnt),
							  "file" + String.valueOf(filenum % _filecnt),
							  _filesize
							  );

    }

    public void runBenchmark() throws Exception {

		// print a heading
		String dashes = new String(new char[80]).replace("\0", "-");
		System.out.println(dashes);
		System.out.println(" Running Performance Benchmark for " + _transactions + " Transactions");
		System.out.println(dashes);

		// start recording statistics for the benchmark, outputting every 5 seconds
		_stats.startBenchmark();

		// main loop for the benchmark
		for (int i=0; i<_transactions; i++) {
			benchmarkItem(i);
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
		options.addOption("t", "transactions", true, "number of benchmark executions");
		options.addOption("f", "filecnt", true, "number of files");
		options.addOption("s", "filesize", true, "file size in bytes");
		CommandLine cmd = parser.parse(options, args);

		String hostlist = "localhost";
		if (cmd.hasOption("hostlist"))
			hostlist = cmd.getOptionValue("hostlist");

		int transactions = 1;
		if (cmd.hasOption("transactions"))
			transactions = Integer.parseInt(cmd.getOptionValue("transactions"));
		
		int filecnt = 1;
		if (cmd.hasOption("filecnt"))
			filecnt = Integer.parseInt(cmd.getOptionValue("filecnt"));

		int filesize = 1024*1024;
		if (cmd.hasOption("filesize"))
			filesize = Integer.parseInt(cmd.getOptionValue("filesize"));
		
		WriteBench benchmark = new WriteBench(hostlist, transactions, filecnt, filesize);
		benchmark.runBenchmark();
    }
}
