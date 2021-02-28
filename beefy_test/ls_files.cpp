#include <stdio.h>
#include <dirent.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>

#include <chrono>
#include <string>
#include <iostream>

using namespace std;

/*
 * usage:
 * ./ls_files <size> <uid> [-p]
 *
 * One can find their user id by running $ id
 */

void listFilesRecursively(string path);

int size;
int uid;
bool print = false;

int main(int argc, char **argv) {
  if (argc < 3) {
    cout << "Usage: ./ls_file <size> <uid> [-p]" << endl;
    return -1;
  }

  // Return files greater than <size>.
  size = atoi(argv[1]);

  // Return files with userid <uid>.
  uid = atoi(argv[2]);

  // If -p is provided, print output.
  if (argc > 3)
    print = true;

  // Directory root
  string target_dir = getenv("TARGET");

  // measure total time only
  auto start = chrono::high_resolution_clock::now();
  listFilesRecursively(target_dir);
  auto stop = chrono::high_resolution_clock::now();
  cout << (stop - start).count() / 1e6 << " msec" << endl;

  return 0;
}

long GetFileSize(std::string filename)
{
    struct stat stat_buf;
    int rc = stat(filename.c_str(), &stat_buf);
    return rc == 0 ? stat_buf.st_size : -1;
}

/**
 * Lists all files and sub-directories recursively 
 * considering path as base path.
 */
void listFilesRecursively(string basePath)
{
    string path;
    struct dirent *dp;
    DIR *dir = opendir(basePath.c_str());

    // Unable to open directory stream
    if (!dir)
        return;

    while ((dp = readdir(dir)) != NULL)
    {
        if (strcmp(dp->d_name, ".") != 0 && strcmp(dp->d_name, "..") != 0)
        {
            string file_path = string(basePath) + '/' + string(dp->d_name);
	    
	    // Stat the file to get info.
	    struct stat info;
            stat(file_path.c_str(), &info);
	    int file_size = info.st_size;
	    int file_uid = info.st_uid;
	    if (file_size > size && uid == file_uid && print) {
	      cout << file_path << " " << file_size << endl;
            }
            // Construct new path from our base path
	    path = basePath + "/" + string(dp->d_name);

            listFilesRecursively(path);
        }
    }

    closedir(dir);
}
