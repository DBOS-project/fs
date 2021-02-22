#!/bin/bash
set -ex

#
# usage:
# ./daemon.sh <output suffix> [flags]
# flags: -h hostname -t threshold_gb -p period_ms -b batch_size
#

SCRIPT_DIR=$(dirname $(readlink -f $0))

cd ${SCRIPT_DIR}/../../clients/daemon

./run_client.sh ${*:2} &> ../../testing/stats/daemon$1.out

