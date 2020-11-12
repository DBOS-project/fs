#!/bin/bash
set -ex

#
# usage:
# ./benchmark_write.sh <output suffix> [flags]
# flags: -h hostname, -t transactions, -f filecnt, -s filesize, -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/single_user_sync
benchmarks="write_bench"

# write files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh ${*:2} &> ../../../testing/stats/single_$bench$1.out
	cd ..
done

