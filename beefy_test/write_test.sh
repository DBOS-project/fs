#!/bin/bash

./spawner create_files.sh 40 0 100
./spawner write_files_once 40 0 100 262144
./spawner write_files 40 0 100 2048 5 128 10 _write_1_
./spawner write_files 40 0 100 2048 5 128 10 _write_2_
./spawner write_files 40 0 100 2048 5 128 10 _write_3_
./spawner write_files 40 0 100 2048 5 128 10 _write_4_
./spawner write_files 40 0 100 2048 5 128 10 _write_5_
