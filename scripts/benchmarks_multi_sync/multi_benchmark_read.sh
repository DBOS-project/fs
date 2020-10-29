#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_read.sh <output suffix> [flags]
# flags: -h hostname, -f filecnt, -t transactions
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/multi_user_sync
benchmarks="read_bench"

# read files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh ${*:2} &> ../../../testing/stats/multi_$bench$1.out
	cd ..
done

