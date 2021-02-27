#!/bin/bash

i=0

cd ..
./spawner create_files.sh 40 $i 4000
./spawner write_files 40 $i 4000 655360 10 _warmup_
./spawner write_files 40 $i 4000 655360 100 _write_422_1a_
./spawner write_files 40 $i 4000 655360 100 _write_422_2a_
./spawner write_files 40 $i 4000 655360 100 _write_422_3a_
./spawner write_files 40 $i 4000 655360 100 _write_422_4a_
./spawner write_files 40 $i 4000 655360 100 _write_422_5a_
./spawner write_files_heavy 40 $i 4000 655360 100 _write_422_1b_
./spawner write_files_heavy 40 $i 4000 655360 100 _write_422_2b_
./spawner write_files_heavy 40 $i 4000 655360 100 _write_422_3b_
./spawner write_files_heavy 40 $i 4000 655360 100 _write_422_4b_
./spawner write_files_heavy 40 $i 4000 655360 100 _write_422_5b_
