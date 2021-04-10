#include <iostream>
#include <cstdlib>
#include <memory>
#include <fstream>
#include <cstring>
#include <chrono>
#include <vector>
#include <random>
#include <future>

using namespace std;

/*
 * blocks refer to logic partition of a file
 * randomly choose a file and write random blocks asynchronously
 */

void write_block(shared_ptr<fstream> file, char *buffer, int block_size) {
    file->write(buffer, block_size);
}

int main (int argc, char **argv) {
    if (argc != 8) {
        printf("[Error] usage:\n./write_files <dir_id> <file_cnt> <block_cnt> "
               "<block_rnd_cnt> <block_size> <exec_time> <output_suffix>\n");
        return -1;
    }

    const string dir_id =  argv[1];
    const int file_cnt = atoi(argv[2]);
    const int block_cnt = atoi(argv[3]);
    const int block_rnd_cnt = atoi(argv[4]);
    const int block_size = atoi(argv[5]);
    const int time_sec = atoi(argv[6]);
    const string suffix = argv[7];
    long long unsigned txs = 0;

    chrono::duration<double> elapsed;
    ofstream stats;
    string sfname = "../stats/wr_rnd_asc" + suffix + dir_id + ".out";
    stats.open(sfname.c_str());

    // create data buffer to write
    char *buffer = new char[block_size];
    memset(buffer, '1', block_size);

    // mount point
    const string tmpdir = getenv("TARGET");
   
    // transactions
    printf("worker %s randomly writes asynchronously...\n", dir_id.c_str());
    auto start = chrono::high_resolution_clock::now();
    for (;;) {
        // open a random file
        int file_id = rand() % file_cnt;
        shared_ptr<fstream> file(new fstream);
        string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(file_id);
        file->open(fname.c_str());

        // iterate random blocks
        future<void> fut[block_rnd_cnt];
        for (int i=0; i < block_rnd_cnt; i++) {
            // modify what we write
            int change_index = rand() % block_size;
            buffer[change_index]++;

            // randomly select a block
            int block_id = rand() % block_cnt;
            file->seekp(block_id * block_size);
            fut[i] = async(launch::async, write_block, file, buffer, block_size);
        }
        for (int i=0; i < block_rnd_cnt; i++) {
            fut[i].get();
        }
        
        txs++;

        // close file
        file->close();

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
