#!/bin/bash
#set -ex

# INSTRUCTIONS
#
# - Run make in the src directory
# - Replace /path/to/testdir with an actual path.
#     The benchmark will generate files in this (shared) directory.
#     This has to be in a Lustre mount point.
#     Make sure you create this directory before running.
# - Execute the experiments following their instructions
# - After the experiments have run, I only need the stats directory

# EXPERIMENTS
#
# There are 9 experiments in total.
# Run each command in the specified node only.
# Issue commands of the same experiment (roughly) at the same time,
# and run next experiment when the previous has run to completion in all nodes.
#
# - Experiment 1: 4 nodes, 20 workers per node, 12.5GB per node
# node 1: ./run_test.sh 20 0 16 4n_20w_16f
# node 2: ./run_test.sh 20 40 16 4n_20w_16f
# node 3: ./run_test.sh 20 80 16 4n_20w_16f
# node 4: ./run_test.sh 20 120 16 4n_20w_16f

# - Experiment 2: 8 nodes, 20 workers per node, 6.25GB per node
# node 1: ./run_test.sh 20 0 8 8n_20w_8f
# node 2: ./run_test.sh 20 40 8 8n_20w_8f
# node 3: ./run_test.sh 20 80 8 8n_20w_8f
# node 4: ./run_test.sh 20 120 8 8n_20w_8f
# node 5: ./run_test.sh 20 160 8 8n_20w_8f
# node 6: ./run_test.sh 20 200 8 8n_20w_8f
# node 7: ./run_test.sh 20 240 8 8n_20w_8f
# node 8: ./run_test.sh 20 280 8 8n_20w_8f

# - Experiment 3: 4 nodes, 40 workers per node, 12.5GB per node
# node 1: ./run_test.sh 40 0 8 4n_40w_8f
# node 2: ./run_test.sh 40 40 8 4n_40w_8f
# node 3: ./run_test.sh 40 80 8 4n_40w_8f
# node 4: ./run_test.sh 40 120 8 4n_40w_8f

# - Experiment 4: 8 nodes, 40 workers per node, 6.25GB per node
# node 1: ./run_test.sh 40 0 4 8n_40w_4f
# node 2: ./run_test.sh 40 40 4 8n_40w_4f
# node 3: ./run_test.sh 40 80 4 8n_40w_4f
# node 4: ./run_test.sh 40 120 4 8n_40w_4f
# node 5: ./run_test.sh 40 160 4 8n_40w_4f
# node 6: ./run_test.sh 40 200 4 8n_40w_4f
# node 7: ./run_test.sh 40 240 4 8n_40w_4f
# node 8: ./run_test.sh 40 280 4 8n_40w_4f

# - Experiment 5: 4 nodes, 20 workers per node, 125GB per node
# node 1: ./run_test.sh 20 0 160 4n_20w_160f
# node 2: ./run_test.sh 20 40 160 4n_20w_160f
# node 3: ./run_test.sh 20 80 160 4n_20w_160f
# node 4: ./run_test.sh 20 120 160 4n_20w_160f

# - Experiment 6: 8 nodes, 20 workers per node, 62.5GB per node
# node 1: ./run_test.sh 20 0 80 8n_20w_80f
# node 2: ./run_test.sh 20 40 80 8n_20w_80f
# node 3: ./run_test.sh 20 80 80 8n_20w_80f
# node 4: ./run_test.sh 20 120 80 8n_20w_80f
# node 5: ./run_test.sh 20 160 80 8n_20w_80f
# node 6: ./run_test.sh 20 200 80 8n_20w_80f
# node 7: ./run_test.sh 20 240 80 8n_20w_80f
# node 8: ./run_test.sh 20 280 80 8n_20w_80f

# - Experiment 7: 4 nodes, 40 workers per node, 125GB per node
# node 1: ./run_test.sh 40 0 80 4n_40w_80f
# node 2: ./run_test.sh 40 40 80 4n_40w_80f
# node 3: ./run_test.sh 40 80 80 4n_40w_80f
# node 4: ./run_test.sh 40 120 80 4n_40w_80f

# - Experiment 8: 8 nodes, 40 workers per node, 62.5GB per node
# node 1: ./run_test.sh 40 0 40 8n_40w_40f
# node 2: ./run_test.sh 40 40 40 8n_40w_40f
# node 3: ./run_test.sh 40 80 40 8n_40w_40f
# node 4: ./run_test.sh 40 120 40 8n_40w_40f
# node 5: ./run_test.sh 40 160 40 8n_40w_40f
# node 6: ./run_test.sh 40 200 40 8n_40w_40f
# node 7: ./run_test.sh 40 240 40 8n_40w_40f
# node 8: ./run_test.sh 40 280 40 8n_40w_40f

# - Experiment 9: 8 nodes, 40 workers per node, 125GB per node
# node 1: ./run_test.sh 40 0 80 8n_40w_80f
# node 2: ./run_test.sh 40 40 80 8n_40w_80f
# node 3: ./run_test.sh 40 80 80 8n_40w_80f
# node 4: ./run_test.sh 40 120 80 8n_40w_80f
# node 5: ./run_test.sh 40 160 80 8n_40w_80f
# node 6: ./run_test.sh 40 200 80 8n_40w_80f
# node 7: ./run_test.sh 40 240 80 8n_40w_80f
# node 8: ./run_test.sh 40 280 80 8n_40w_80f

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

export TARGET="/path/to/testdir"

cd src

exec_time=20
rand_blocks=10
block_size=1048576
block_cnt=40

./spawner create_files.sh $1 $2 $3
./spawner write_files_once $1 $2 $3 $[$block_cnt*$block_size]

./spawner write_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_1_
./spawner write_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_2_
./spawner write_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_3_
./spawner write_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_4_

./spawner write_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_1_
./spawner write_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_2_
./spawner write_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_3_
./spawner write_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_4_

./spawner write_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_1_
./spawner write_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_2_
./spawner write_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_3_
./spawner write_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_4_

./spawner write_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_1_
./spawner write_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_2_
./spawner write_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_3_
./spawner write_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_4_

./spawner read_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_1_
./spawner read_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_2_
./spawner read_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_3_
./spawner read_files $1 $2 $3 $block_cnt $block_size $exec_time _$4_4_

./spawner read_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_1_
./spawner read_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_2_
./spawner read_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_3_
./spawner read_files_rnd $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_4_

./spawner read_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_1_
./spawner read_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_2_
./spawner read_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_3_
./spawner read_files_async $1 $2 $3 $block_cnt $block_size $exec_time _$4_4_

./spawner read_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_1_
./spawner read_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_2_
./spawner read_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_3_
./spawner read_files_rnd_async $1 $2 $3 $block_cnt $rand_blocks $block_size $exec_time _$4_4_

./spawner delete_files.sh $1 $2 $3
