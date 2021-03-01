#!/bin/bash
set -ex

time ./spawner benchmark_create.sh _crt_1 40 0 -h localhost -f 100 -b 1
time ./spawner benchmark_delete.sh _del_1 40 0 -h localhost -f 100

