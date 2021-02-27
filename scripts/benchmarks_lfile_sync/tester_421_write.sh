#!/bin/bash
set -ex
./spawner benchmark_create.sh           _cr 40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10
time ./spawner ./benchmark_write.sh _warmup 40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 10
time ./spawner ./benchmark_write.sh _421_1  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 100
time ./spawner ./benchmark_write.sh _421_2  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 100
time ./spawner ./benchmark_write.sh _421_3  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 100
time ./spawner ./benchmark_write.sh _421_4  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 100
time ./spawner ./benchmark_write.sh _421_5  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 10 -s 16384 -t 100

