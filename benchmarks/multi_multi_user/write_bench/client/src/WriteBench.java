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
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;
import org.voltdb.util.BenchmarkCallback;
import org.voltdb.util.BenchmarkStats;

public class WriteBench {

    private Client client;
    private BenchmarkStats stats;
    private int benchmarkSize;
	private int kiloBytes;
    
    public WriteBench (String servers, int size, int kb) throws Exception {
	this.benchmarkSize = size;
	this.kiloBytes = kb;
	
	// create client
	client = ClientFactory.createClient();

	// connect to each server listed (separated by commas) in the first argument
	// String[] serverArray = servers.split(",");
	// for (String server : serverArray) {
	//     // client.createConnection(server);
	//     client.createConnection("172.31.130.209");
	// }
	client.createConnection("localhost");

	stats = new BenchmarkStats(client, true);
    }


    public void benchmarkItem(int file_num) throws Exception {

	// To make an asynchronous procedure call, you need a callback object
	// BenchmarkCallback is a generic callback that keeps track of the transaction results
	// for any given procedure name, which should match the procedure called below.
	ProcedureCallback callback = new BenchmarkCallback("Write");
	
	// call the procedure asynchronously, passing in the callback and the procedure name,
	// followed by the input parameters
	client.callProcedure(callback,
			     "Populate",
			     "file" + String.valueOf(file_num % 24),
				 kiloBytes,
			     "user" + String.valueOf(file_num % 24)
			     );
    }

    public void runBenchmark() throws Exception {

	// print a heading
	String dashes = new String(new char[80]).replace("\0", "-");
	System.out.println(dashes);
	System.out.println(" Running Performance Benchmark for " + benchmarkSize + " Transactions");
	System.out.println(dashes);

	// start recording statistics for the benchmark, outputting every 5 seconds
	stats.startBenchmark();

	// main loop for the benchmark
	for (int i=0; i<benchmarkSize; i++) {

	    benchmarkItem(i);

	}

	// stop recording, print stats
	stats.endBenchmark();

	// wait for any outstanding responses to return before closing the client
	client.drain();
	client.close();

	// print the transaction results tracked by BenchmarkCallback
	BenchmarkCallback.printAllResults();
    }


    public static void main(String[] args) throws Exception {

	// the first parameter can be a comma-separated list of hostnames or IPs
	String serverlist = "localhost";
	if (args.length > 0) {
	    serverlist = args[0];
	}

	// the second parameter can be the number of transactions to execute
	int transactions = 5;
	if (args.length > 1) {
	    transactions = Integer.parseInt(args[1]);
	}

	// the fourth parameter can be the number of kilobytes we write
	int kb = 1024;
	if (args.length > 2) {
	    kb = Integer.parseInt(args[2]);
	}

	WriteBench benchmark = new WriteBench(serverlist, transactions, kb);
	benchmark.runBenchmark();

    }
}
