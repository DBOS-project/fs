#!/bin/bash
set -ex
./spawner benchmark_create.sh           _cr 40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 
time ./spawner ./benchmark_write.sh _warmup 40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 10
time ./spawner ./benchmark_write.sh _4n_1  40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 180
time ./spawner ./benchmark_write.sh _4n_2  40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 180
time ./spawner ./benchmark_write.sh _4n_3  40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 180
time ./spawner ./benchmark_write.sh _4n_4  40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 180
time ./spawner ./benchmark_write.sh _4n_5  40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -f 1 -b 4000 -s 8192 -t 180
