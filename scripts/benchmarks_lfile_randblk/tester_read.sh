#!/bin/bash
set -ex

time ./spawner ./benchmark_read.sh _warmup 40 $1 -h localhost -f 100 -b 128 -r 20 -t 3
time ./spawner ./benchmark_read.sh _1f5b_1 40 $1 -h localhost -f 100 -b 128 -r 20 -t 10
time ./spawner ./benchmark_read.sh _1f5b_2 40 $1 -h localhost -f 100 -b 128 -r 20 -t 10
time ./spawner ./benchmark_read.sh _1f5b_3 40 $1 -h localhost -f 100 -b 128 -r 20 -t 10
time ./spawner ./benchmark_read.sh _1f5b_4 40 $1 -h localhost -f 100 -b 128 -r 20 -t 10
time ./spawner ./benchmark_read.sh _1f5b_5 40 $1 -h localhost -f 100 -b 128 -r 20 -t 10

