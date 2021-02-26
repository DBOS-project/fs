#!/bin/bash

cd ..
./spawner read_files 40 $1 10 163840 10 _warmup_
./spawner read_files 40 $1 10 163840 100 _read_4_2_1_1_
./spawner read_files 40 $1 10 163840 100 _read_4_2_1_2_
./spawner read_files 40 $1 10 163840 100 _read_4_2_1_3_
./spawner read_files 40 $1 10 163840 100 _read_4_2_1_4_
#./spawner read_files 40 $1 10 163840 300 _read_4_2_1_5_
