#!/bin/bash
#set -ex

#
# usage:
# ./delete_files <directory> <num_files>
#

dir="dir$1"
n=$[$2-1]

cd $TARGET
rm -r $dir

