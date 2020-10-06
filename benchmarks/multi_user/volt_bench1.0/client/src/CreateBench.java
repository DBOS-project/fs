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

import java.util.Random;
import org.voltdb.*;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

public class CreateBench {

    private Client client;
    private int benchmarkSize;
    
    public CreateBench (String servers, int size) throws Exception {
	this.benchmarkSize = size;
	
	// create client
	client = ClientFactory.createClient();

	// connect to each server listed (separated by commas) in the first argument
	String[] serverArray = servers.split(",");
	for (String server : serverArray) {
	    client.createConnection(server);
	}
    }


    static class CreateCallback implements ProcedureCallback {
	  @Override
	  public void clientCallback(ClientResponse clientResponse) {
	      if (clientResponse.getStatus() != ClientResponse.SUCCESS) {
		  System.err.println(clientResponse.getStatusString());
	      } else {
		  ;
		  // myEvaluateResultsProc(clientResponse.getResults());
	      }
	  }
    }

    
    public void benchmarkItem(int state, int county) throws Exception {

	// To make an asynchronous procedure call, you need a callback object
	// BenchmarkCallback is a generic callback that keeps track of the transaction results
	// for any given procedure name, which should match the procedure called below.
	
	
	// call the procedure asynchronously, passing in the callback and the procedure name,
	// followed by the input parameters
	client.callProcedure(new CreateCallback(),
			     "CreateFile",
			     "file",
			     1,
			     "askiad"
			     );

    }

    public void runBenchmark() throws Exception {

	// print a heading
	String dashes = new String(new char[80]).replace("\0", "-");
	System.out.println(dashes);
	System.out.println(" Running Performance Benchmark for " + benchmarkSize + " Transactions");
	System.out.println(dashes);

	// main loop for the benchmark
	for (int i=0; i<benchmarkSize; i++) {

	    benchmarkItem(0, 0);

	}

	// wait for any outstanding responses to return before closing the client
	client.drain();
	client.close();
    }


    public static void main(String[] args) throws Exception {

	// the first parameter can be a comma-separated list of hostnames or IPs
	String serverlist = "d-14-9-2";
	if (args.length > 0) {
	    serverlist = args[0];
	}

	// the second parameter can be the number of transactions to execute
	int transactions = 2;
	if (args.length > 1) {
	    transactions = Integer.parseInt(args[1]);
	}

	CreateBench benchmark = new CreateBench(serverlist, transactions);
	benchmark.runBenchmark();

    }
}
