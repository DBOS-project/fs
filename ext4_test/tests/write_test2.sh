#!/bin/bash

cd ..
./spawner write_files 40 $1 1000 2621440 1 _warmup_
# ./spawner write_files 40 $1 1000 2621440 2 _write_4_2_2a_1_
# ./spawner write_files 40 $1 1000 2621440 2 _write_4_2_2a_2_
# ./spawner write_files 40 $1 1000 2621440 2 _write_4_2_2a_3_
# ./spawner write_files 40 $1 1000 2621440 2 _write_4_2_2a_4_
# ./spawner write_files_heavy 40 $1 1000 2621440 2 _write_4_2_2b_1_
# ./spawner write_files_heavy 40 $1 1000 2621440 2 _write_4_2_2b_2_
# ./spawner write_files_heavy 40 $1 1000 2621440 2 _write_4_2_2b_3_
# ./spawner write_files_heavy 40 $1 1000 2621440 2 _write_4_2_2b_4_
