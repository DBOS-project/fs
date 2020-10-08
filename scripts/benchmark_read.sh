#!/bin/bash
set -ex

#
# usage:
# ./read_files.sh <num_transactions> <user_name> <output suffix>
#

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

cd benchmarks/multi_user
benchmarks="read_bench"

# read files cyclically
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh localhost $1 $2 &> ../../../testing/stats/stat_$bench$3.out
	cd ..
done

