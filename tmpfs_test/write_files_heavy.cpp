#include <iostream>
#include <cstdlib>
#include <memory>
#include <fstream>
#include <cstring>
#include <chrono>
#include <vector>

using namespace std;

/*
 * usage:
 * ./write_files <dir_id> <file_cnt> <byte_cnt> <exec_time>
 */

int main (int argc, char **argv) {
    if (argc != 6) {
        printf("[Error] usage:\n./write_files <dir_id> <file_cnt> <byte_cnt> <exec_time> <output suffix>b\n");
        return -1;
    }

    const string dir_id =  argv[1];
    const int file_cnt = atoi(argv[2]);
    const int byte_cnt = atoi(argv[3]);
    const int time_sec = atoi(argv[4]);
    const string suffix = argv[5];
    long long unsigned txs = 0;    

    chrono::duration<double> elapsed;
    ofstream stats;
    string sfname = "./stats/lnx" + suffix + dir_id + ".out";
    stats.open(sfname.c_str());

    // create data buffer to write
    char *buffer = new char[byte_cnt];
    memset(buffer, '1', byte_cnt);

    // ext4 mount point
    const string tmpdir = getenv("SHM");
    
    // open all files
    /*vector<std::shared_ptr<ofstream>> files;
    for (int i=0; i<file_cnt; i++) {
        shared_ptr<ofstream> file(new ofstream);
        string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(i);
        file->open(fname.c_str());
        files.push_back(file);
    }*/

    
    // measure open + read/write + close time
    auto start = chrono::high_resolution_clock::now();
   
    // transactions
    for (;;) {
        for (int i=0; i<file_cnt; i++) {
            string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(i);
            ofstream file;
            file.open(fname.c_str());
            file.seekp(0);
            file.write(buffer, byte_cnt);
            file.close();
            txs++;
        }
        
        if (txs % (file_cnt) == 0) {
            auto stop = chrono::high_resolution_clock::now();
            elapsed = stop - start;
            if (elapsed.count() > time_sec)
                break;
        }
    }    


    // close all files
    /*for (int i=0; i<file_cnt; i++) {
        files[i]->close();
    }*/

    stats << "transactions: " << txs << endl;
    stats << "elapsed time: " << elapsed.count() << endl;
    stats << "throughput: " << 1.0*txs / (1.0*elapsed.count()) << endl;
    stats.close();
    
    return 0;
}
