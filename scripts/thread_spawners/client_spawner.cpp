#include <cstdlib>
#include <vector> 
#include <string>
#include <thread>

using namespace std; 
  
// compile with g++ -o <output> -std=c++11 -pthread client_spawner.cpp
// usage: ./spawner <benchmark> <output_suffix> <user_cnt> [flags]

void start_client(string bench, string output_suffix, string args, int user_num) {
    string cmd = "./benchmark_" + bench + ".sh " +
        output_suffix + "_user" + to_string(user_num) + " " +
        args + " -u user" + to_string(user_num) + " -f file" + to_string(user_num);

    system(cmd.c_str());
}

int main(int argc, char** argv) {
    string bench = argv[1]; 
    string suffix = argv[2];    
    int user_cnt = atoi(argv[3]);

    string args = "";
    int arg_cnt = 4;
    while (arg_cnt < argc) {
        args += argv[arg_cnt++];
        args += " ";
    }

    vector<thread> clients;
    for (int i = 0; i < user_cnt; i++)
        clients.push_back(thread(start_client, bench, suffix, args, i));

    for (thread & client : clients)
        client.join();
  
    return 0; 
} 
