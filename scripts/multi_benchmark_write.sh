#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_write.sh <num_transactions> <kilobytes> <output suffix>
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

cd benchmarks/multi_user
benchmarks="write_bench"

# write files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh localhost $1 $2 &> ../../../testing/stats/stat_multi_$bench$3.out
	cd ..
done

