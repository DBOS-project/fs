#!/bin/bash
set -ex

#
# usage:
# ./benchmark_create.sh <output suffix> [flags]
# flags: -h hostname -f filecnt -b blockcnt -p firstpartition -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/pfile_async
benchmarks="create_bench"

for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/$bench$1.out
    cd ..
done

