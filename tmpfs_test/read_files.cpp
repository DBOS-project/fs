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
 * ./read_files <dir_id> <file_cnt> <byte_cnt> <exec_time>
 */

char *operation(char *buffer, int size, shared_ptr<ifstream> file) {
    file->seekg(0);
    file->read(buffer, size);
    return buffer;
}

int main (int argc, char **argv) {
    if (argc != 6) {
        printf("[Error] usage:\n./write_files <dir_id> <file_cnt> <byte_cnt> <exec_time> <output suffix>\n");
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
    char *inbuffer = new char[byte_cnt];

    // ext4 mount point
    const string tmpdir = getenv("SHM");
    
    // open all files
    vector<shared_ptr<ifstream>> files;
    for (int i=0; i<file_cnt; i++) {
        shared_ptr<ifstream> file(new ifstream);
        string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(i);
        file->open(fname.c_str());
        files.push_back(file);
    }

    
    // measure read/write time only
    auto start = chrono::high_resolution_clock::now();

    // cerr.setstate(ios_base::badbit);
    // transactions
    for (;;) {
        for (int i=0; i<file_cnt; i++) {
            // files[i]->seekg(0);
            // files[i]->read(buffer, byte_cnt);
            // memset(buffer, '0', byte_cnt);
            buffer = operation(inbuffer, byte_cnt, files[i]);
            // cerr << buffer[160000] << endl;
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
    for (int i=0; i<file_cnt; i++) {
        files[i]->close();
    }

    stats << "transactions: " << txs << endl;
    stats << "elapsed time: " << elapsed.count() << endl << endl;
    stats << "throughput " << 1.0*txs / (1.0*elapsed.count()) << endl;
    stats.close();
    
    return 0;
}
