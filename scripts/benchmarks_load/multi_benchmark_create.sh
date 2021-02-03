#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_create.sh <output suffix> [flags]
# flags: -h hostname, -u username -t filecnt
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/load_async
benchmarks="create_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/load_$bench$1.out
    cd ..
done

