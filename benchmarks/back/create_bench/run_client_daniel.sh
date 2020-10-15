#!/usr/bin/env bash

CP="$CLASSPATH:/home/gridsan/dhong98/DBOS_shared/daniel/VoltDB/voltdb/*"

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}

SRC=`find client/src -name "*.java"`

if [ ! -z "$SRC" ]; then
    mkdir -p client/obj
    javac -cp "$CLASSPATH:/home/gridsan/dhong98/DBOS_shared/daniel/VoltDB/voltdb/*" -d client/obj $SRC
    # stop if compilation fails
    if [ $? != 0 ]; then exit; fi

    jar cf client/client.jar -C client/obj .
    rm -rf client/obj

    java -classpath "client/client.jar:$CLASSPATH:/home/gridsan/dhong98/DBOS_shared/daniel/VoltDB/voltdb/*" org.voltdb.create.CreateBench $*

fi
