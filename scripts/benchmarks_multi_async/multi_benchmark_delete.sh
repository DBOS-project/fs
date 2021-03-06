#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_delete.sh <output suffix> [flags]
# flags: -h hostname
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/multi_user_async
benchmarks="delete_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/multi_$bench$1.out
    cd ..
done

