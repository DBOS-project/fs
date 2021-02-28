#!/bin/bash

# usage: ./initialize_multi.sh [-c node_cnt]
# node_cnt can be 1, 2, 3, 4, 6, 8

ulimit -n 32768

SCRIPT_DIR=$(dirname $(readlink -f $0))
cd ${SCRIPT_DIR}/..

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
            if [ $OPTARG = "2s" ]; then
                echo "Initializing VoltDB for 2 nodes, with 20 partitions in each one..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n2s.xml
            fi
            if [ $OPTARG = "3" ]; then
                echo "Initializing VoltDB for 3 nodes..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n3.xml
            fi
            if [ $OPTARG = "4" ]; then
                echo "Initializing VoltDB for 4 nodes..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n4.xml
            fi
            if [ $OPTARG = "6" ]; then
                echo "Initializing VoltDB for 6 nodes..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n6.xml
            fi
            if [ $OPTARG = "8" ]; then
                echo "Initializing VoltDB for 8 nodes..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n8.xml
            fi
            if [ $OPTARG = "10" ]; then
                echo "Initializing VoltDB for 10 nodes..." >&2
                voltdb init -f --dir=testing --config=testing/deployment_n10.xml
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

