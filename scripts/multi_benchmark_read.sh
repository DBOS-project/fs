#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_read.sh <num_transactions> <output suffix>
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

cd benchmarks/multi_multi_user
benchmarks="read_bench"

# read files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh localhost $1 &> ../../../testing/stats/stat_multi_$bench$2.out
	cd ..
done

