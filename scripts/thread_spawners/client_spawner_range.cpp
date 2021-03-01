#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>

using namespace std; 
  
/*
 *  compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
 *  usage: ./spawner <benchmark> <output_suffix> <user_cnt> <thread_cnt> [flags]
 *
 *  example: ./spawner create _test 2 10 -h hostlist -t txs
 *  executes 2 threads:
 *    ./benchmark _test_user10 -h hostlist -t txs -u user10
 *    ./benchmark _test_user11 -h hostlist -t txs -u user11
 */

void start_client(string bench, string output_suffix, string args,
                  int thread_id, int user_min, int user_max) {
    string cmd = "./" + bench + " " + output_suffix + "_worker" + to_string(thread_id) + " " +
        args + " -u " + to_string(user_min) + " -m " + to_string(user_max);
    system(cmd.c_str());
}

int main(int argc, char** argv) {
    string bench = argv[1]; 
    string suffix = argv[2];    
    int user_cnt = atoi(argv[3]);
    int thread_cnt = atoi(argv[4]);

    string args = "";
    int arg_cnt = 5;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        args += " ";
    }

    int users_per_thread = user_cnt / thread_cnt;
    
    vector<thread> clients;
    for (int i = 0; i < thread_cnt; i++)
        clients.push_back(thread(start_client, bench, suffix, args,
                                 i, i*users_per_thread, (i+1)*users_per_thread));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
