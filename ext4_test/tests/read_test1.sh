#!/bin/bash

i=0

cd ..
./spawner read_files 40 $i 10 163840 10 _warmup_
./spawner read_files 40 $i 10 163840 100 _read_421_1a_
./spawner read_files 40 $i 10 163840 100 _read_421_2a_
./spawner read_files 40 $i 10 163840 100 _read_421_3a_
./spawner read_files 40 $i 10 163840 100 _read_421_4a_
./spawner read_files 40 $i 10 163840 100 _read_421_5a_
./spawner read_files_heavy 40 $i 10 163840 100 _read_421_1b_
./spawner read_files_heavy 40 $i 10 163840 100 _read_421_2b_
./spawner read_files_heavy 40 $i 10 163840 100 _read_421_3b_
./spawner read_files_heavy 40 $i 10 163840 100 _read_421_4b_
./spawner read_files_heavy 40 $i 10 163840 100 _read_421_5b_

