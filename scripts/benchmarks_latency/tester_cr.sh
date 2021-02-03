#!/bin/bash
set -ex

txs=500000

time ./benchmark_create.sh _$1_1 -h d-7-10-1,d-7-10-2 -u u1 -t $txs
time ./benchmark_create.sh _$1_2 -h d-7-10-1,d-7-10-2 -u u2 -t $txs
time ./benchmark_create.sh _$1_3 -h d-7-10-1,d-7-10-2 -u u3 -t $txs
time ./benchmark_create.sh _$1_4 -h d-7-10-1,d-7-10-2 -u u4 -t $txs
time ./benchmark_create.sh _$1_5 -h d-7-10-1,d-7-10-2 -u u5 -t $txs

