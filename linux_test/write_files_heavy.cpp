#include <iostream>
#include <cstdlib>
#include <memory>
#include <fstream>
#include <cstring>
#include <chrono>
#include <vector>
#include <random>

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

    srand(time(NULL));
    const string dir_id =  argv[1];
    const int file_cnt = atoi(argv[2]);
    const int byte_cnt = atoi(argv[3]);
    const int time_sec = atoi(argv[4]);
    const string suffix = argv[5];
    long long unsigned txs = 0;    

    chrono::duration<double> elapsed_best;
    chrono::duration<double> elapsed_avg;
    ofstream stats;
    string sfname = "./stats/lnx" + suffix + dir_id + ".out";
    stats.open(sfname.c_str());

    // create data buffer to write
    char *buffer = new char[byte_cnt];
    memset(buffer, '1', byte_cnt);

    // ext4 mount point
    const string tmpdir = getenv("TMPDIR");

    
    // measure time including file open/close
    auto start_avg = chrono::high_resolution_clock::now();

    // open all files
    /*vector<std::shared_ptr<ofstream>> files;
    for (int i=0; i<file_cnt; i++) {
        shared_ptr<ofstream> file(new ofstream);
        string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(i);
        file->open(fname.c_str());
        files.push_back(file);
    }*/

    
    // measure read/write time only
    auto start_best = chrono::high_resolution_clock::now();
   
    // transactions
    for (;;) {
        for (int i=0; i<file_cnt; i++) {
            int randInd = rand() % file_cnt;
            string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(randInd);
            ofstream filex;
            filex.open(fname.c_str());
            filex.seekp(0);
            filex.write(buffer, byte_cnt);
            filex.close();
            //files[randInd]->seekp(0);
            //files[randInd]->write(buffer, byte_cnt);
            txs++;
        }
        
        if (txs % (file_cnt) == 0) {
            auto stop_best = chrono::high_resolution_clock::now();
            elapsed_best = stop_best - start_best;
            if (elapsed_best.count() > time_sec)
                break;
        }
    }    


    // close all files
    /*for (int i=0; i<file_cnt; i++) {
        files[i]->close();
    }*/

    auto stop_avg = chrono::high_resolution_clock::now();
    elapsed_avg = stop_avg - start_avg;

    stats << "transactions: " << txs << endl;
    stats << "elapsed time (best): " << elapsed_best.count() << endl;
    stats << "elapsed time (avg): " << elapsed_avg.count() << endl << endl;
    stats << "throughput (best) " << 1.0*txs / (1.0*elapsed_best.count()) << endl;
    stats << "throughput (avg) " << 1.0*txs / (1.0*elapsed_avg.count()) << endl;
    stats.close();
    
    return 0;
}
