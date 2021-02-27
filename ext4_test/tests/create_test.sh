#!/bin/bash

i=0

cd ..
{ time ./spawner create_files.sh 40 $i 100000 ; } &> stats/420/100K_each_$1
