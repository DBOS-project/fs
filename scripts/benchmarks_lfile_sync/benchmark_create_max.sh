#!/bin/bash
set -ex

#
# usage:
# ./benchmark_create_max.sh <output suffix> [flags]
# flags: -h hostname -t running_time -b blockcnt -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/lfile_sync
benchmarks="create_bench_max"

for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/$bench$1.out
    cd ..
done

