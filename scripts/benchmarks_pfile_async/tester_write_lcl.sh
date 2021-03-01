#!/bin/bash
set -ex

./spawner benchmark_create.sh _cr 40 $2 -h localhost -f 4 -b 8192 
time ./spawner benchmark_write.sh _n$1_1 40 $2 -h localhost -f 4 -b 8192 -t 25 -s 16384
time ./spawner benchmark_write.sh _n$1_2 40 $2 -h localhost -f 4 -b 8192 -t 25 -s 16384
time ./spawner benchmark_write.sh _n$1_3 40 $2 -h localhost -f 4 -b 8192 -t 25 -s 16384
time ./spawner benchmark_write.sh _n$1_4 40 $2 -h localhost -f 4 -b 8192 -t 25 -s 16384
time ./spawner benchmark_write.sh _n$1_5 40 $2 -h localhost -f 4 -b 8192 -t 25 -s 16384
