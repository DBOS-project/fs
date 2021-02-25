#!/bin/bash

./spawner write_files 40 $1 2000 1310720 1 _warmup_
./spawner write_files 40 $1 2000 1310720 2 _write_4_2_2a_1_
./spawner write_files 40 $1 2000 1310720 2 _write_4_2_2a_2_
./spawner write_files 40 $1 2000 1310720 2 _write_4_2_2a_3_
./spawner write_files 40 $1 2000 1310720 2 _write_4_2_2a_4_
#./spawner write_files 40 $1 10 163840 300 _write_4_2_1_5_
