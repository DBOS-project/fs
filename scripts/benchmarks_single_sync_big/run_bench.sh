#!/bin/bash
set -ex

./spawner create _u$1c $1 -t $2
sleep 2
./spawner write _u$1p $1 -t $2

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../..
rm -r tmpfiles
mkdir tmpfiles
