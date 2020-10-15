#!/usr/bin/env bash

CP="$CLASSPATH:/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/voltdb/*"
CP="$CP:/home/gridsan/askiad/homebin/commons-cli-1.4/*"

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

SRC=`find client/src -name "*.java"`

if [ ! -z "$SRC" ]; then
    mkdir -p client/obj
    javac -cp $CP -d client/obj $SRC
    # stop if compilation fails
    if [ $? != 0 ]; then exit; fi

    jar cf client/client.jar -C client/obj .
    rm -rf client/obj

    java -cp "$CP:client/client.jar*" org.voltdb.write.WriteBench $*
fi
