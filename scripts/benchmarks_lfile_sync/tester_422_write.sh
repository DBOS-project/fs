#!/bin/bash
set -ex

time ./spawner ./benchmark_write.sh _warmup 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 10
time ./spawner ./benchmark_write.sh _422_1 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _422_2 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _422_3 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _422_4 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 100
time ./spawner ./benchmark_write.sh _422_5 40 $1 -h localhost -f 4000 -b 10 -s 65536 -t 100
