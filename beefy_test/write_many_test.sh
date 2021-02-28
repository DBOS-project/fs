#!/bin/bash

./spawner create_files.sh 40 0 100
./spawner write_files_once 40 0 100 262144
./spawner write_files_many 40 0 100 2048 20 128 10 _write_many_1_
./spawner write_files_many 40 0 100 2048 20 128 10 _write_many_2_
./spawner write_files_many 40 0 100 2048 20 128 10 _write_many_3_
./spawner write_files_many 40 0 100 2048 20 128 10 _write_many_4_
./spawner write_files_many 40 0 100 2048 20 128 10 _write_many_5_
