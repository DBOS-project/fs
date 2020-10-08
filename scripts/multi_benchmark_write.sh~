#!/bin/bash
set -ex

#
# usage:
# ./write_files.sh <num_transactions> <user_name> <kilobytes> <output suffix>
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
	./run_client.sh localhost $1 $2 $3 &> ../../../testing/stats/stat_$bench$4.out
	cd ..
done

