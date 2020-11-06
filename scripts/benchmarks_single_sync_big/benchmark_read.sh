#!/bin/bash
set -ex

#
# usage:
# ./benchmark_read.sh <output suffix> [flags]
# flags: -h hostname, -t transactions -f filecnt, -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/single_user_sync_big
benchmarks="read_bench"

# read files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh ${*:2} &> ../../../testing/stats/big_files/single_$bench$1.out
	cd ..
done

