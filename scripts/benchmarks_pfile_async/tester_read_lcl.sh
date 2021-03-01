#!/bin/bash
set -ex

time ./spawner benchmark_read.sh _n$1_1 40 $2 -h localhost -f 4 -b 8192 -t 15
time ./spawner benchmark_read.sh _n$1_2 40 $2 -h localhost -f 4 -b 8192 -t 15
time ./spawner benchmark_read.sh _n$1_3 40 $2 -h localhost -f 4 -b 8192 -t 15
time ./spawner benchmark_read.sh _n$1_4 40 $2 -h localhost -f 4 -b 8192 -t 15
time ./spawner benchmark_read.sh _n$1_5 40 $2 -h localhost -f 4 -b 8192 -t 15
