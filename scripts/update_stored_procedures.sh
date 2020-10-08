#!/bin/bash
set -ex

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/..

# recompile procedures
cd stored_procedures
for class in "$@"
do
	cd "$class"
	javac -classpath "/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/voltdb/*" "$class".java
	jar cvf "$class".jar "$class".class
	cd ..
done
cd ..

# push update to VoltDB
sqlcmd < sql/update_procedures.sql
