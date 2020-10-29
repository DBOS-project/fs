#!/bin/bash
set -ex

#
# usage:
# ./benchmark_empty.sh <output suffix> [flags]
# flags: -h hostname, -t transactions
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/single_user_async
benchmarks="empty_bench"

# read files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh ${*:2} &> ../../../testing/stats/single_$bench$1.out
	cd ..
done

