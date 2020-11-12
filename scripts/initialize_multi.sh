#!/bin/bash

# usage: ./initialize_multi.sh [-c node_cnt]
# -c 1 for 1 node, 20 partitions
# -c 2 for 1 node, 40 partitions each
# to support other options, create a new testing/deployment_.xml

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/../

while getopts ":c:" opt; do
	case $opt in
		c)
			if [ $OPTARG = "1" ]; then
				echo "Initializing VoltDB for 1 node..." >&2
				voltdb init -f --dir=testing --config=testing/deployment_p40.xml
			fi
			if [ $OPTARG = "2" ]; then
				echo "Initializing VoltDB for 2 nodes..." >&2
				voltdb init -f --dir=testing --config=testing/deployment_n2.xml
			fi
			if [ $OPTARG = "3" ]; then
				echo "Initializing VoltDB for 3 nodes..." >&2
				voltdb init -f --dir=testing --config=testing/deployment_n3.xml
			fi
			exit 1
			;;
		\?)
			echo "Invalid option: -$OPTARG" >&2
			echo "usage: ./Initialize_multi [-c node_cnt]"
			exit 1
			;;
		:)
			echo "Option -$OPTARG requires an argument." >&2
			echo "usage: ./Initialize_multi [-c node_cnt]"
			exit 1
			;;
	esac
done

# default option
echo "Initializing VoltDB for 1 node..." >&2
voltdb init -f --dir=testing --config=testing/deployment_config.xml

