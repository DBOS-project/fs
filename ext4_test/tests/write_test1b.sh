#!/bin/bash

./spawner write_files_heavy 40 $1 10 163840 10 _warmup_
./spawner write_files_heavy 40 $1 10 163840 100 _write_4_2_1b_1_
./spawner write_files_heavy 40 $1 10 163840 100 _write_4_2_1b_2_
./spawner write_files_heavy 40 $1 10 163840 100 _write_4_2_1b_3_
./spawner write_files_heavy 40 $1 10 163840 300 _write_4_2_1b_4_
#./spawner write_files_heavy 40 $1 10 163840 300 _write_4_2_1b_5_
