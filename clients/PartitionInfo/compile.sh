#!/usr/bin/env bash

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}

CP="$CLASSPATH:/home/gridsan/groups/DBOS/shared/VoltDB/voltdb-ent-9.3.2/voltdb/*"
CP="$CP:$SCRIPT_DIR/../../dependencies/commons-cli-1.4/*"

SRC="PartitionInfoInit"

javac -cp $CP "$SRC.java"
jar cvf "$SRC.jar" "$SRC.class"

# if [ ! -z "$SRC" ]; then
#     mkdir obj
#     javac -cp $CP -d obj $SRC
#     # stop if compilation fails
#     if [ $? != 0 ]; then exit; fi

#     jar cf client.jar -C obj .
#     rm -rf obj
# fi