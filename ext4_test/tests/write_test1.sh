#!/bin/bash

i=0

cd ..
./spawner create_files.sh 40 $i 10
./spawner write_files 40 $i 10 163840 10 _warmup_
./spawner write_files 40 $i 10 163840 100 _write_421_1a_
./spawner write_files 40 $i 10 163840 100 _write_421_2a_
./spawner write_files 40 $i 10 163840 100 _write_421_3a_
./spawner write_files 40 $i 10 163840 100 _write_421_4a_
./spawner write_files 40 $i 10 163840 100 _write_421_5a_
./spawner write_files_heavy 40 $i 10 163840 100 _write_421_1b_
./spawner write_files_heavy 40 $i 10 163840 100 _write_421_2b_
./spawner write_files_heavy 40 $i 10 163840 100 _write_421_3b_
./spawner write_files_heavy 40 $i 10 163840 100 _write_421_4b_
./spawner write_files_heavy 40 $i 10 163840 100 _write_421_5b_

