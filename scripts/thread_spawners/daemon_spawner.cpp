#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>

using namespace std; 
  
/*
 *  compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
 *  usage: ./spawner <user_cnt> [flags]
 *
 *  example: ./spawner 2 -h hostlist -t threshold_gb -p period -b batch_size
 */

void start_client(string args, int user_num) {
    string cmd = "./daemon.sh _user" + to_string(user_num) + " " +
        args;

    system(cmd.c_str());
}

int main(int argc, char** argv) {
    int user_cnt = atoi(argv[1]);

    string args = "";
    int arg_cnt = 2;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        args += " ";
    }

    vector<thread> clients;
    for (int i = 0; i < user_cnt; i++)
        clients.push_back(thread(start_client, args, i));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
