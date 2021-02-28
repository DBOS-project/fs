#!/bin/bash
set -ex

time ./spawner ./benchmark_read.sh _warmup 40 $1 -h localhost -f 100 -b 128 -t 3
time ./spawner ./benchmark_read.sh _5f1b_1 40 $1 -h localhost -f 100 -b 128 -t 15
time ./spawner ./benchmark_read.sh _5f1b_2 40 $1 -h localhost -f 100 -b 128 -t 15
time ./spawner ./benchmark_read.sh _5f1b_3 40 $1 -h localhost -f 100 -b 128 -t 15
time ./spawner ./benchmark_read.sh _5f1b_4 40 $1 -h localhost -f 100 -b 128 -t 15
time ./spawner ./benchmark_read.sh _5f1b_5 40 $1 -h localhost -f 100 -b 128 -t 15

