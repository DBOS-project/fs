#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/..

all_procs="Create CreateAt Delete PartitionInfoSelect PartitionInfoInsert PartitionInfoUpdate Create_Big Read Read_Big Populate Populate_Big Write Write_Big Empty CountFiles CountBytes CountLargerThan CountLargestK CheckStorage SendToDisk"

if [ -z $@ ]
then
    proc_list=${all_procs}
else
    proc_list="$@"
fi

# recompile procedures
cd stored_procedures
for class in ${proc_list}
do
    cd "$class"
    javac -classpath "$SCRIPT_DIR/../../VoltDB/voltdb/*" "$class".java
    jar cvf "$class".jar "$class".class
    cd ..
done
cd ..

# push update to VoltDB
sqlcmd < sql/update_procedures.sql
