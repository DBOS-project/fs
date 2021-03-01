#!/bin/bash
set -ex

time ./benchmark_create.sh _ppr_1 -h localhost -u user1 -f $1 -b 1
time ./benchmark_delete.sh _ppr_1 -h localhost -u user1 -f $1
time ./benchmark_create.sh _ppr_2 -h localhost -u user1 -f $1 -b 1
time ./benchmark_delete.sh _ppr_2 -h localhost -u user1 -f $1
time ./benchmark_create.sh _ppr_3 -h localhost -u user1 -f $1 -b 1
time ./benchmark_delete.sh _ppr_3 -h localhost -u user1 -f $1
time ./benchmark_create.sh _ppr_4 -h localhost -u user1 -f $1 -b 1
time ./benchmark_delete.sh _ppr_4 -h localhost -u user1 -f $1
time ./benchmark_create.sh _ppr_5 -h localhost -u user1 -f $1 -b 1
time ./benchmark_delete.sh _ppr_5 -h localhost -u user1 -f $1

