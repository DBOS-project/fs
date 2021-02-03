#!/bin/bash
set -ex

#
# usage:
# ./benchmark_create.sh <output suffix> [flags]
# flags: -h hostname -u username -t filecnt
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/latency_sync
benchmarks="create_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/$bench$1.out
    cd ..
done

