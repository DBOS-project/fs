#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

CP="$CLASSPATH:/home/gridsan/groups/DBOS/shared/VoltDB/voltdb-ent-9.3.2/voltdb/*"
CP="$CP:$SCRIPT_DIR/../../../dependencies/commons-cli-1.4/*"

java -cp "$CP:client/client.jar" org.voltdb.sumquery.SumQueryBench $*

