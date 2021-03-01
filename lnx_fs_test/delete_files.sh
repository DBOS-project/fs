#!/bin/bash
#set -ex

#
# usage:
# (skip flags for simplicity)
# ./create_files <directory> <num_files>
#

dir="dir$1"
n=$[$2-1]

cd $TARGET
cd $dir

for i in $(seq 0 $n); do
    rm "file$i"
done

