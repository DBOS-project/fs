#!/bin/bash
set -ex

time ./spawner benchmark_create.sh _crt_1 40 0 -h localhost -f $1 -b 1
time ./spawner benchmark_delete.sh _del_1 40 0 -h localhost -f $1
time ./spawner benchmark_create.sh _crt_2 40 0 -h localhost -f $1 -b 1
time ./spawner benchmark_delete.sh _del_2 40 0 -h localhost -f $1
time ./spawner benchmark_create.sh _crt_3 40 0 -h localhost -f $1 -b 1
time ./spawner benchmark_delete.sh _del_3 40 0 -h localhost -f $1
time ./spawner benchmark_create.sh _crt_4 40 0 -h localhost -f $1 -b 1
time ./spawner benchmark_delete.sh _del_4 40 0 -h localhost -f $1
time ./spawner benchmark_create.sh _crt_5 40 0 -h localhost -f $1 -b 1
time ./spawner benchmark_delete.sh _del_5 40 0 -h localhost -f $1

