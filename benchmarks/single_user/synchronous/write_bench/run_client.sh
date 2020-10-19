#!/usr/bin/env bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

CP="$CLASSPATH:$SCRIPT_DIR/../../../../../VoltDB/voltdb/*"
CP="$CP:$SCRIPT_DIR/../../../../dependencies/commons-cli-1.4/*"

SRC=`find client/src -name "*.java"`

if [ ! -z "$SRC" ]; then
    mkdir -p client/obj
    javac -cp $CP -d client/obj $SRC
    # stop if compilation fails
    if [ $? != 0 ]; then exit; fi

    jar cf client/client.jar -C client/obj .
    rm -rf client/obj

    java -cp "$CP:client/client.jar" org.voltdb.write.WriteBench $*
fi
