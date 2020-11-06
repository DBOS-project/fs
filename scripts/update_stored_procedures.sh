#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

# recompile procedures
cd stored_procedures

all_procs="Create CreateDummy Create_Big Populate PopulateDummy Read Empty CountFiles CountBytes CountLargerThan CountLargestK"

if [[ $@ == *'all'* ]]
then
	proc_list=${all_procs}
else
	proc_list="$@"
fi

for class in ${proc_list}
do
	cd "$class"
	javac -classpath "$SCRIPT_DIR/../../VoltDB/voltdb/*" "$class".java
	# javac -cp "/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/voltdb/*" "$class".java
	# javac -cp "/home/gridsan/askiad/DBOS_shared/shared/VoltDB/voltdb-ent-9.3.2/voltdb/* " "$class".java
	jar cvf "$class".jar "$class".class
	cd ..
done
cd ..

# push update to VoltDB
sqlcmd < sql/update_procedures.sql
