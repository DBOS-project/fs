#!/usr/bin/env bash

CP="/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/voltdb/*"

SRC=`find client/src -name "*.java"`

if [ ! -z "$SRC" ]; then
    mkdir -p client/obj
    javac -classpath $CP -d clien/obj $SRC
    # stop if compilation fails
    if [ $? != 0 ]; then exit; fi

    jar cf client/client.jar -C client/obj .
    rm -rf client/obj

    java -classpath "client/client.jar:$CP" org.voltdb.create.CreateBench $*

fi
