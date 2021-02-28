#!/bin/bash
set -ex
time ./spawner ./benchmark_write.sh _warmup 40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 3
time ./spawner ./benchmark_write.sh _20f1b_1  40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 10
time ./spawner ./benchmark_write.sh _20f1b_2  40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 10
time ./spawner ./benchmark_write.sh _20f1b_3  40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 10
time ./spawner ./benchmark_write.sh _20f1b_4  40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 10
time ./spawner ./benchmark_write.sh _20f1b_5  40 $1 -h localhost -f 100 -b 128 -r 20 -s 2048 -t 10

