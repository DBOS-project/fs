#!/bin/bash
set -ex

#
# usage:
# ./benchmark_create.sh <output suffix> [flags]
# flags: -h hostname, -t filecnt, -u username
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..

cd benchmarks/single_user_async
benchmarks="create_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
	cd $bench
	./compile.sh
	./run_client.sh ${*:2} &> ../../../testing/stats/single_$bench$1.out
	cd ..
done

