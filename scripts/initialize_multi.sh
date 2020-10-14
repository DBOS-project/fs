#!/bin/bash

# usage: ./initialize_multi.sh [-c node_cnt]
# -c 1 for 1 node, 24 partitions
# -c 2 for 2 nodes, 24 partitions each
# to support other options, create a new testing/deployment_.xml

SCRIPT_DIR=$(dirname $(readlink -f $0))
# Enter the root dir of the repo.
cd ${SCRIPT_DIR}/../

export PATH=$PATH:/home/gridsan/dhong98/DBOS_shared/daniel/VoltDB/bin
export PATH=$PATH:/home/gridsan/askiad/DBOS_shared/askiad/VoltDB/bin

while getopts ":c:" opt; do
	case $opt in
		c)
			if [ $OPTARG = "1" ]; then
				echo "Initializing VoltDB for 1 node..." >&2
				voltdb init -f --dir=testing --config=testing/deployment_1node.xml
			fi
			if [ $OPTARG = "2" ]; then
				echo "Initializing VoltDB for 2 nodes..." >&2
				voltdb init -f --dir=testing --config=testing/deployment_2node.xml
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
echo "Initializing VoltDB for 2 nodes..." >&2
voltdb init -f --dir=testing --config=testing/deployment_2node.xml

