#!/bin/bash

i=0

cd ..
./spawner create_files.sh 40 $i 10000
./spawner write_files 40 $i 10000 1024 10 _warmup_
./spawner write_files 40 $i 10000 1024 100 _write_420_1a_
./spawner write_files 40 $i 10000 1024 100 _write_420_2a_
./spawner write_files 40 $i 10000 1024 100 _write_420_3a_
./spawner write_files 40 $i 10000 1024 100 _write_420_4a_
./spawner write_files 40 $i 10000 1024 100 _write_420_5a_
./spawner write_files_heavy 40 $i 10000 1024 100 _write_420_1b_
./spawner write_files_heavy 40 $i 10000 1024 100 _write_420_2b_
./spawner write_files_heavy 40 $i 10000 1024 100 _write_420_3b_
./spawner write_files_heavy 40 $i 10000 1024 100 _write_420_4b_
./spawner write_files_heavy 40 $i 10000 1024 100 _write_420_5b_

