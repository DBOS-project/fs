#!/bin/bash
#set -ex

#
# usage:
# (skip flags for simplicity)
# ./create_files <directory> <num_files>
#

dir="dir$1"
n=$[$2-1]

cd $SHM
mkdir -p $dir
cd $dir

for i in $(seq 0 $n); do
    touch "file$i"
done

