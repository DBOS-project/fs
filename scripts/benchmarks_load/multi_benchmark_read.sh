#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_read.sh <output suffix> [flags]
# flags: -h hostname, -u username -f filecnt, -m filemin -t transactions -p partcnt
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/load_async
benchmarks="read_bench"

# read files cyclically
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/load_$bench$1.out
    cd ..
done

