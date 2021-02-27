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
 * ./write_files_once <dir_id> <file_cnt> <byte_cnt>
 */

int main (int argc, char **argv) {
    if (argc != 4) {
        printf("[Error] usage:\n./write_files <dir_id> <file_cnt> <byte_cnt>\n");
        return -1;
    }

    const string dir_id =  argv[1];
    const int file_cnt = atoi(argv[2]);
    const int byte_cnt = atoi(argv[3]);
    long long unsigned txs = 0;

    // create data buffer to write
    char *buffer = new char[byte_cnt];
    memset(buffer, '1', byte_cnt);

    // ext4 mount point
    const string tmpdir = getenv("TARGET");

    // Write all files once.
    vector<std::shared_ptr<ofstream>> files;
    for (int i=0; i<file_cnt; i++) {
        shared_ptr<ofstream> file(new ofstream);
        string fname = tmpdir + "/dir" + dir_id + "/file" + to_string(i);
        file->open(fname.c_str());
        file->seekp(0);
        file->write(buffer, byte_cnt);
        file->close();
    }

    return 0;
}
