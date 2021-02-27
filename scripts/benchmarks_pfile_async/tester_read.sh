#!/bin/bash
set -ex
time ./spawner ./benchmark_read.sh _warmup 40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 10
time ./spawner ./benchmark_read.sh _4n_1  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 120
time ./spawner ./benchmark_read.sh _4n_2  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 120
time ./spawner ./benchmark_read.sh _4n_3  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 120
time ./spawner ./benchmark_read.sh _4n_4  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 120
time ./spawner ./benchmark_read.sh _4n_5  40 $1 -h d-12-15-2,d-9-3-2,d-12-8-2,d-9-4-2 -f 10 -b 1000 -t 120

