#!/bin/bash

./spawner create_files.sh 40 0 100
./spawner write_files_once 40 0 100 262144
./spawner read_files 40 0 100 2048 20 128 10 _read_1_
./spawner read_files 40 0 100 2048 20 128 10 _read_2_
./spawner read_files 40 0 100 2048 20 128 10 _read_3_
./spawner read_files 40 0 100 2048 20 128 10 _read_4_
./spawner read_files 40 0 100 2048 20 128 10 _read_5_
