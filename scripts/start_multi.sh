#!/bin/bash

# usage: ./start_multi.sh -h <hostname1>,<hostname1>,...
# eg. ./start_multi.sh -h 172.31.130.194,d-4-2-17

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/../

while getopts ":h:" opt; do
	case $opt in
		h)
			echo "starting VoltDB with hosts $OPTARG..." >&2
			voltdb start -B --dir=testing --ignore=thp --host=$OPTARG
			exit 1
			;;
		\?)
			echo "Invalid option: -$OPTARG" >&2
			echo "usage: ./start_multi.sh -h <hostname1>,<hostname1>,..."
			exit 1
			;;
		:)
			echo "Option -$OPTARG requires an argument." >&2
			echo "usage: ./start_multi.sh -h <hostname1>,<hostname1>,..."
			exit 1
			;;
	esac
done

echo "[ERROR] hosts not specified"
echo "usage: ./start_multi.sh -h <hostname1>,<hostname1>,..."

