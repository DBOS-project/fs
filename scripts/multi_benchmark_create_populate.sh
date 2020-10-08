#!/bin/bash
set -ex

#
# usage:
# ./multi_benchmark_create_populate.sh <num_files> <output suffix>
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

cd benchmarks/multi_multi_user
benchmarks="create_bench populate_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh localhost $1 &> ../../../testing/stats/stat_multi_$bench$2.out
	cd ..
done

