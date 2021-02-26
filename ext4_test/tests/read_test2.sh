#!/bin/bash

cd ..
./spawner read_files 40 $1 2000 1310720 1 _warmup_
./spawner read_files 40 $1 2000 1310720 2 _read_4_2_2a_1_
./spawner read_files_heavy 40 $1 2000 1310720 2 _read_4_2_2b_4_
