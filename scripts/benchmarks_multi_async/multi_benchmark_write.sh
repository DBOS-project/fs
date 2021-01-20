#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_write.sh <output suffix> [flags]
# flags: -h hostname, -u username -f filecnt, -m filemin -t transactions, -s filesize
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/multi_user_async
benchmarks="write_bench"

# write files cyclically
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/multi_$bench$1.out
    cd ..
done

