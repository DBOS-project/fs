#!/bin/bash
#set -ex

#
# usage:
# ./create_files <directory> <num_files>
#

dir="dir$1"
n=$[$2-1]

cd $TARGET
mkdir -p $dir
cd $dir

for i in $(seq 0 $n); do
    touch "file$i"
done

