#!/bin/bash
set -ex
time ./spawner benchmark_create_max.sh _1 40 $1 -h localhost -b 100000 -t 10
