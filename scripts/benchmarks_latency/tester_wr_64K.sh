#!/bin/bash
set -ex

txs=250000

time ./benchmark_write.sh _64_$1_1 -h d-7-10-1,d-7-10-2 -f 40 -s 65536 -t $txs
time ./benchmark_write.sh _64_$1_2 -h d-7-10-1,d-7-10-2 -f 40 -s 65536 -t $txs
time ./benchmark_write.sh _64_$1_3 -h d-7-10-1,d-7-10-2 -f 40 -s 65536 -t $txs
time ./benchmark_write.sh _64_$1_4 -h d-7-10-1,d-7-10-2 -f 40 -s 65536 -t $txs
time ./benchmark_write.sh _64_$1_5 -h d-7-10-1,d-7-10-2 -f 40 -s 65536 -t $txs

