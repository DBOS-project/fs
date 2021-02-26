#!/bin/bash

./spawner write_files 40 $1 10 163840 10 _warmup_
./spawner write_files 40 $1 10 163840 100 _write_4_2_1_1_
./spawner write_files 40 $1 10 163840 100 _write_4_2_1_2_
./spawner write_files 40 $1 10 163840 100 _write_4_2_1_3_
./spawner write_files 40 $1 10 163840 300 _write_4_2_1_4_
#./spawner write_files 40 $1 10 163840 300 _write_4_2_1_5_
