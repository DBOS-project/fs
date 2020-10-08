#!/bin/bash
set -ex

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/../

export PATH=$PATH:/home/gridsan/dhong98/DBOS_shared/daniel/VoltDB/bin
export PATH=$PATH:/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/bin

voltdb init -f --dir=testing
voltdb start -B --dir=testing
sleep 5
sqlcmd < sql/create_tables.sql

