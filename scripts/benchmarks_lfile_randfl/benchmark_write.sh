#!/bin/bash
set -ex

#
# usage:
# ./benchmark_write.sh <output suffix> [flags]
# flags: -h hostname -t exec_time -f filecnt -b blockcnt -s filesize -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/lfile_sync_rand_nf1b
benchmarks="write_bench"

# write files cyclically
for bench in $benchmarks;
do
    cd $bench
    ./run_client.sh ${*:2} &> ../../../testing/stats/$bench$1.out
    cd ..
done

