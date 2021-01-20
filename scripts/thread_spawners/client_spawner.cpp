#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>

using namespace std; 
  
/*
 *  compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
 *  usage: ./spawner <benchmark> <output_suffix> <user_cnt> <user_min> [flags]
 *
 *  example: ./spawner create _test 2 10 -h hostlist -t txs
 *  executes 2 threads:
 *    ./benchmark _test_user10 -h hostlist -t txs -u user10
 *    ./benchmark _test_user11 -h hostlist -t txs -u user11
 */

void start_client(string bench, string output_suffix, string args, int user_num) {
    string cmd = "./" + bench + " " + output_suffix + "_user" + to_string(user_num) + " " +
        args + " -u user" + to_string(user_num);

    system(cmd.c_str());
}

int main(int argc, char** argv) {
    string bench = argv[1]; 
    string suffix = argv[2];    
    int user_cnt = atoi(argv[3]);
    int user_min = atoi(argv[4]);

    string args = "";
    int arg_cnt = 5;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        args += " ";
    }

    vector<thread> clients;
    for (int i = 0; i < user_cnt; i++)
        clients.push_back(thread(start_client, bench, suffix, args, user_min + i));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
