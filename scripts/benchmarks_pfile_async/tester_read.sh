#!/bin/bash
set -ex

hostlist="d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2,d-9-4-1,d-12-16-1,d-12-8-2,d-12-15-2"

time ./spawner benchmark_read.sh _warmup $2 $1 -h $hostlist -f 1 -b 4000 -t 3
time ./spawner benchmark_read.sh _$3_1  $2 $1 -h $hostlist -f 1 -b 4000 -t 15
time ./spawner benchmark_read.sh _$3_2  $2 $1 -h $hostlist -f 1 -b 4000 -t 15
time ./spawner benchmark_read.sh _$3_3  $2 $1 -h $hostlist -f 1 -b 4000 -t 15
time ./spawner benchmark_read.sh _$3_4  $2 $1 -h $hostlist -f 1 -b 4000 -t 15
time ./spawner benchmark_read.sh _$3_5  $2 $1 -h $hostlist -f 1 -b 4000 -t 15

