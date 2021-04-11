#!/bin/bash
#set -ex

# usage:
# - Replace /path/to/testdir with an actual path.
#   All files are going to be created in TARGET (this has to be in a Lustre mount point)
# - Run as
#   ./run_rdwr_test.sh <worker_cnt> <first_worker_id> <files_per_worker> <block_cnt> <block_size> <suffix>

# run the cases
# ./run_rdwr_test.sh 40 0 100 10 1024

# I need the stats dir

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

# export TARGET="/path/to/testdir"

cd src
# make clean; make  ### run once somewhere

exec_time=3
rand_blocks=5

./spawner create_files.sh $1 $2 $3
./spawner write_files_once $1 $2 $3 $[$4*$5]

# ./spawner write_files $1 $2 $3 $4 $5 $exec_time _$6_1_
# ./spawner write_files $1 $2 $3 $4 $5 $exec_time _$6_2_
# # ./spawner write_files $1 $2 $3 $4 $5 $exec_time _$6_3_
# # ./spawner write_files $1 $2 $3 $4 $5 $exec_time _$6_4_
# # ./spawner write_files $1 $2 $3 $4 $5 $exec_time _$6_5_

# ./spawner write_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_1_
# ./spawner write_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_2_
# # ./spawner write_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_3_
# # ./spawner write_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_4_
# # ./spawner write_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_5_

./spawner write_files_async $1 $2 $3 $4 $5 $exec_time _$6_1_
./spawner write_files_async $1 $2 $3 $4 $5 $exec_time _$6_2_
# ./spawner write_files_async $1 $2 $3 $4 $5 $exec_time _$6_3_
# ./spawner write_files_async $1 $2 $3 $4 $5 $exec_time _$6_4_
# ./spawner write_files_async $1 $2 $3 $4 $5 $exec_time _$6_5_

# ./spawner write_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_1_
# ./spawner write_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_2_
# # ./spawner write_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_3_
# # ./spawner write_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_4_
# # ./spawner write_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_5_


# ./spawner read_files $1 $2 $3 $4 $5 $exec_time _$6_1_
# ./spawner read_files $1 $2 $3 $4 $5 $exec_time _$6_2_
# # ./spawner read_files $1 $2 $3 $4 $5 $exec_time _$6_3_
# # ./spawner read_files $1 $2 $3 $4 $5 $exec_time _$6_4_
# # ./spawner read_files $1 $2 $3 $4 $5 $exec_time _$6_5_

# ./spawner read_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_1_
# ./spawner read_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_2_
# # ./spawner read_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_3_
# # ./spawner read_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_4_
# # ./spawner read_files_rnd $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_5_

./spawner read_files_async $1 $2 $3 $4 $5 $exec_time _$6_1_
./spawner read_files_async $1 $2 $3 $4 $5 $exec_time _$6_2_
# ./spawner read_files_async $1 $2 $3 $4 $5 $exec_time _$6_3_
# ./spawner read_files_async $1 $2 $3 $4 $5 $exec_time _$6_4_
# ./spawner read_files_async $1 $2 $3 $4 $5 $exec_time _$6_5_

./spawner read_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_1_
./spawner read_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_2_
# ./spawner read_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_3_
# ./spawner read_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_4_
# ./spawner read_files_rnd_async $1 $2 $3 $4 $rand_blocks $5 $exec_time _$6_5_

