#!/bin/bash
set -ex

txs=500000

time ./benchmark_read.sh _1_$1_1 -h d-7-10-1,d-7-10-2 -f 40 -t $txs
time ./benchmark_read.sh _1_$1_2 -h d-7-10-1,d-7-10-2 -f 40 -t $txs
time ./benchmark_read.sh _1_$1_3 -h d-7-10-1,d-7-10-2 -f 40 -t $txs
time ./benchmark_read.sh _1_$1_4 -h d-7-10-1,d-7-10-2 -f 40 -t $txs
time ./benchmark_read.sh _1_$1_5 -h d-7-10-1,d-7-10-2 -f 40 -t $txs

