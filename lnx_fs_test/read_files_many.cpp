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
 * ./read_files <dir_id> <file_cnt> <block_size> <files_per_operation> <total_blocks> <exec_time>
 */

int main (int argc, char **argv) {
    if (argc != 8) {
        printf("[Error] usage:\n./read_files <dir_id> <file_cnt> <block_size> <files_per_operation> <total_blocks> <exec_time> <output suffix>b\n");
        return -1;
    }

    const string dir_id =  argv[1];
    const int file_cnt = atoi(argv[2]);
    const int block_size = atoi(argv[3]);
    const int files_per_operation = atoi(argv[4]);
    const int total_blocks = atoi(argv[5]);
    const int time_sec = atoi(argv[6]);
    const string suffix = argv[7];
    long long unsigned txs = 0;

    chrono::duration<double> elapsed;
    ofstream stats;
    string sfname = "./stats/lnx" + suffix + dir_id + ".out";
    stats.open(sfname.c_str());

    // create data buffer to write
    char *buffer = new char[block_size];
    memset(buffer, '1', block_size);

    // Mount point
    const string tmpdir = getenv("TARGET");

    // measure read/write time only
    auto start = chrono::high_resolution_clock::now();
   
    // transactions
    for (;;) {
        for (int i=0; i < files_per_operation; i++) {
            // Open a random file.
            int file_id = rand() % file_cnt;
            shared_ptr<fstream> file(new fstream);
            string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(file_id);
            file->open(fname.c_str());

            // Randomly select a block.
            int block_id = rand() % total_blocks;
            file->seekg(block_id *  block_size);
            file->read(buffer, block_size);

            // Close file.
            file->close();
        }
        txs++;

        if (txs % (file_cnt) == 0) {
            auto stop = chrono::high_resolution_clock::now();
            elapsed = stop - start;
            if (elapsed.count() > time_sec)
                break;
        }
    }    

    stats << "transactions: " << txs << endl;
    stats << "elapsed time: " << elapsed.count() << endl;
    stats << "throughput " << 1.0*txs / (1.0*elapsed.count()) << endl;
    stats.close();
    
    return 0;
}
