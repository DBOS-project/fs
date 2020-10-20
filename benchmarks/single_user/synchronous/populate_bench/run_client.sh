#!/usr/bin/env bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

CP="$CLASSPATH:$SCRIPT_DIR/../../../../../VoltDB/voltdb/*"
CP="$CP:$SCRIPT_DIR/../../../../dependencies/commons-cli-1.4/*"

SRC=`find client/src -name "*.java"`

java -cp "$CP:client/client.jar" org.voltdb.populate.PopulateBench $*
