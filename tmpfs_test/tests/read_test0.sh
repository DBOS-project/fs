#!/bin/bash

i=0

cd ..
./spawner read_files 40 $i 10000 1024 10 _warmup_
./spawner read_files 40 $i 10000 1024 100 _read_420_1a_
./spawner read_files 40 $i 10000 1024 100 _read_420_2a_
./spawner read_files 40 $i 10000 1024 100 _read_420_3a_
./spawner read_files 40 $i 10000 1024 100 _read_420_4a_
./spawner read_files 40 $i 10000 1024 100 _read_420_5a_
./spawner read_files_heavy 40 $i 10000 1024 100 _read_420_1b_
./spawner read_files_heavy 40 $i 10000 1024 100 _read_420_2b_
./spawner read_files_heavy 40 $i 10000 1024 100 _read_420_3b_
./spawner read_files_heavy 40 $i 10000 1024 100 _read_420_4b_
./spawner read_files_heavy 40 $i 10000 1024 100 _read_420_5b_

