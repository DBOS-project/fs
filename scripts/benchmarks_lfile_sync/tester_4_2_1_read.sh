#!/bin/bash
set -ex

time ./spawner ./benchmark_read.sh warmup 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 60
time ./spawner ./benchmark_read.sh _10f_10b_16K_1 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 300
time ./spawner ./benchmark_read.sh _10f_10b_16K_2 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 300
time ./spawner ./benchmark_read.sh _10f_10b_16K_3 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 300
time ./spawner ./benchmark_read.sh _10f_10b_16K_4 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 300
time ./spawner ./benchmark_read.sh _10f_10b_16K_5 40 $1 -h d-10-4-1,d-10-4-2,d-10-5-2,d-10-6-1 -f 40 -b 10 -t 300

