#!/bin/bash

i=0

cd ..
./spawner read_files 40 $i 4000 655360 10 _warmup_
./spawner read_files 40 $i 4000 655360 100 _read_422_1a_
./spawner read_files 40 $i 4000 655360 100 _read_422_2a_
./spawner read_files 40 $i 4000 655360 100 _read_422_3a_
./spawner read_files 40 $i 4000 655360 100 _read_422_4a_
./spawner read_files 40 $i 4000 655360 100 _read_422_5a_
./spawner read_files_heavy 40 $i 4000 655360 100 _read_422_1b_
./spawner read_files_heavy 40 $i 4000 655360 100 _read_422_2b_
./spawner read_files_heavy 40 $i 4000 655360 100 _read_422_3b_
./spawner read_files_heavy 40 $i 4000 655360 100 _read_422_4b_
./spawner read_files_heavy 40 $i 4000 655360 100 _read_422_5b_
