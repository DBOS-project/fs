#!/bin/bash
set -ex

./spawner benchmark_create.sh _cr 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20

time ./spawner ./benchmark_write.sh _free            40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 0 -t 30
time ./spawner ./benchmark_write.sh _warmup          40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 10
time ./spawner ./benchmark_write.sh _2000f_20b_64Kb_1 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _2000f_20b_64Kb_2 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _2000f_20b_64Kb_3 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _2000f_20b_64Kb_4 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _2000f_20b_64Kb_5 40 $1 -h d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2 -f 2000 -b 20 -s 65536 -t 100

