#!/bin/bash
set -ex

time ./spawner ./benchmark_write.sh _warmup          40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 60
time ./spawner ./benchmark_write.sh _2000f_20b_64K_1 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 300
time ./spawner ./benchmark_write.sh _2000f_20b_64K_2 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 300
time ./spawner ./benchmark_write.sh _2000f_20b_64K_3 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 300
time ./spawner ./benchmark_write.sh _2000f_20b_64K_4 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 300
time ./spawner ./benchmark_write.sh _2000f_20b_64K_5 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 2000 -b 20 -s 65536 -t 300

