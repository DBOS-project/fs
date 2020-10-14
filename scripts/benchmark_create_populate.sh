#!/bin/bash
set -ex

#
# usage:
# ./benchmark_create_populate.sh <num_files> <user_name> <output suffix>
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

cd benchmarks/multi_user
benchmarks="create_bench populate_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh localhost $1 $2 &> ../../../testing/stats/stat_$bench$3.out
	cd ..
done

