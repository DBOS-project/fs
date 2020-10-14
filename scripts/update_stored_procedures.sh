#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

# recompile procedures
cd stored_procedures
all_procs="Create Populate Read Write Overwrite"

if [[ $@ == *'all'* ]]
then
	proc_list=${all_procs}
else
	proc_list="$@"
fi

for class in ${proc_list}
do
	cd "$class"
	javac -classpath "/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/voltdb/*" "$class".java
	jar cvf "$class".jar "$class".class
	cd ..
done
cd ..

# push update to VoltDB
sqlcmd < sql/update_procedures.sql
