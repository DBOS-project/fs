#!/bin/bash
set -ex

#
# usage:
# ./benchmark_delete.sh <output suffix> [flags]
# flags: -h hostname -f filecnt -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/lfile_sync
benchmarks="delete_bench"

for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/$bench$1.out
    cd ..
done

