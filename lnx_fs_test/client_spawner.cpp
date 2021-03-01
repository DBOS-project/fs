#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>

using namespace std; 
  
/*
 *  compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
 *  usage: ./spawner <benchmark> <user_cnt> <user_min> [flags]
 *
 *  example: ./spawner create 2 10 arg1 arg2
 *  executes 2 threads:
 *    ./benchmark 10 arg1 arg2 (pass thread is as first argument to benchmark)
 *    ./benchmark 11 arg1 arg2 (pass thread is as first argument to benchmark)
 */

void start_client(string bench, string args, int user_num) {
    string cmd = "./" + bench + " " + to_string(user_num) + " " + args;
    system(cmd.c_str());
}

int main(int argc, char** argv) {
    string bench = argv[1]; 
    int user_cnt = atoi(argv[2]);
    int user_min = atoi(argv[3]);

    string args = "";
    int arg_cnt = 4;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        args += " ";
    }

    vector<thread> clients;
    for (int i = 0; i < user_cnt; i++)
        clients.push_back(thread(start_client, bench, args, user_min + i));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
