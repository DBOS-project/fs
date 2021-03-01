#!/bin/bash
set -ex

# usage: ./tester.sh <users> <workers> <output_suffix>
hostlist="d-9-1-1,d-9-2-1,d-9-2-2,d-9-3-2,d-9-4-1,d-12-16-1,d-12-8-2,d-14-2-2"

time ./range_spawner benchmark_read.sh _$3_1 $1 $2 -h $hostlist -f 32 -b 16384 -t 15
time ./range_spawner benchmark_read.sh _$3_2 $1 $2 -h $hostlist -f 32 -b 16384 -t 15
time ./range_spawner benchmark_read.sh _$3_3 $1 $2 -h $hostlist -f 32 -b 16384 -t 15
time ./range_spawner benchmark_read.sh _$3_4 $1 $2 -h $hostlist -f 32 -b 16384 -t 15
time ./range_spawner benchmark_read.sh _$3_5 $1 $2 -h $hostlist -f 32 -b 16384 -t 15
