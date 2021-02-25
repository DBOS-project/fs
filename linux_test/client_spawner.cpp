#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>
#include <iostream>

using namespace std; 
  
/*
 *  compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
 *  usage: ./spawner <benchmark> <user_cnt> <user_min> [flags]
 *
 *  example: ./spawner create 2 10 arg1 arg2
 *  executes 2 threads:
 *    ./benchmark 10 arg1 arg2
 *    ./benchmark 11 arg1 arg2
 */

void start_client(string bench, string args, int user_num) {
    string cmd = "./" + bench + " " + args + to_string(user_num);
    std::cout << cmd << std::endl;
    system(cmd.c_str());
}

int main(int argc, char** argv) {
    string bench = argv[1]; 
    int user_cnt = atoi(argv[2]);

    string args = "";
    int arg_cnt = 3;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        if (arg_cnt == 8)
            break;
        args += " ";
    }

    vector<thread> clients;
    for (int i = 0; i < user_cnt; i++)
        clients.push_back(thread(start_client, bench, args, i));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
