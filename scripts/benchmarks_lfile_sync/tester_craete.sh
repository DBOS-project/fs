#!/bin/bash
set -ex
time ./spawner benchmark_create_max.sh _1 40 $1 -h d-9-2-2,d-9-4-2,d-9-4-1,d-9-3-2 -b 100000 -t 130