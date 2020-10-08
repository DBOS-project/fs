#!/bin/bash
set -ex

#
# usage:
# ./create_and_populate_files.sh <num_files> <user_name>
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
	./run_client.sh localhost $@ &> ../../../testing/stats/stat_$bench
	cd ..
done

