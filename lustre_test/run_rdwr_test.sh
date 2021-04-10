#!/bin/bash
#set -ex

# usage:
# - Replace /path/to/testdir with an actual path.
#   All files are going to be created in TARGET (this has to be in a Lustre mount point)
# - Run as
#   ./run_rdwr_test.sh <worker_cnt> <files_per_worker> <block_cnt> <block_size> <suffix>

# run the cases
# ./run_rdwr_test.sh 40 100 10 1024

# I need the stats dir

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

# export TARGET="/path/to/testdir"
# export TARGET="/home/askiad/Documents/stanford/DBOS/fs/lustre_test/workdir"

cd src
make clean; make

exec_time=4
rand_blocks=5

./spawner create_files.sh $1 0 $2
./spawner write_files_once $1 0 $2 $[$3*$4]

./spawner write_files $1 0 $2 $3 $4 $exec_time _$5_1_
./spawner write_files $1 0 $2 $3 $4 $exec_time _$5_2_
# ./spawner write_files $1 0 $2 $3 $4 $exec_time _$5_3_
# ./spawner write_files $1 0 $2 $3 $4 $exec_time _$5_4_
# ./spawner write_files $1 0 $2 $3 $4 $exec_time _$5_5_

./spawner write_files_rnd $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_1_
./spawner write_files_rnd $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_2_
# ./spawner write_files_rnd $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_3_
# ./spawner write_files_rnd $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_4_
# ./spawner write_files_rnd $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_5_

./spawner write_files_async $1 0 $2 $3 $4 $exec_time _$5_1_
./spawner write_files_async $1 0 $2 $3 $4 $exec_time _$5_2_
# ./spawner write_files_async $1 0 $2 $3 $4 $exec_time _$5_3_
# ./spawner write_files_async $1 0 $2 $3 $4 $exec_time _$5_4_
# ./spawner write_files_async $1 0 $2 $3 $4 $exec_time _$5_5_

./spawner write_files_rnd_async $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_1_
./spawner write_files_rnd_async $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_2_
# ./spawner write_files_rnd_async $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_3_
# ./spawner write_files_rnd_async $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_4_
# ./spawner write_files_rnd_async $1 0 $2 $3 $rand_blocks $4 $exec_time _$5_5_

