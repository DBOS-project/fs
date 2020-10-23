#!/bin/bash

#SBATCH -o outputs/1/user%a.out
#SBATCH -a 0-0
#SBATCH -n 1
#SBATCH -N 1

# Enter the root dir of the repo.
cd /home/gridsan/dhong98/DBOS_shared/daniel/fs

if [[ $SLURM_ARRAY_TASK_ID == 0 ]];
then
	voltdb init -f --dir=testing --config=testing/deployment_p1.xml
	sleep 3
	# need to change NODELIST for multiple nodes
	voltdb start -B --dir=testing --ignore=thp --host=$SLURM_JOB_NODELIST
else
	sleep 6
fi

cd benchmarks/single_user/synchronous
benchmarks="create_bench populate_bench"

# create files and populate them with 1MB data
for bench in $benchmarks;
do
	cd $bench
	./run_client.sh ${*:2}
	cd ..
done
