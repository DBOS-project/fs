// compile with g++ -o <output_file_name> -std=c++11 -pthread multi_user_multi_cores.cpp

#include <stdlib.h>
#include <iostream> 
#include <thread> 
#include <cstdlib>
#include <vector> 
#include <string>
using namespace std; 
  
void start_client(int user, int transactions, int all_users) { 
	string cmd = "single_user/synchronous/read_bench/run_client.sh -u user" + to_string(user) + " -t " + to_string(transactions) + " > outputs3/" + to_string(all_users) + "/user" + to_string(user) + "r.out";
    system(cmd.c_str());
}

int main(int argc, char** argv) { 
    int users = atoi(argv[1]);
    int transactions = atoi(argv[2]);
    vector<thread> ths;
    for (int i = 0; i < users; i++) {
        ths.push_back(thread(start_client, i, transactions, users));;
    }

    for (thread & th : ths) {
    th.join();
    }
  
    return 0; 
} 
