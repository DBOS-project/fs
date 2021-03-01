#!/bin/bash

time ./spawner create_files.sh 40 0 10000 _cr_1
time ./spawner delete_files.sh 40 0 10000 _dl_1
time ./spawner create_files.sh 40 0 10000 _cr_2
time ./spawner delete_files.sh 40 0 10000 _dl_2
time ./spawner create_files.sh 40 0 10000 _cr_3
time ./spawner delete_files.sh 40 0 10000 _dl_3
time ./spawner create_files.sh 40 0 10000 _cr_4
time ./spawner delete_files.sh 40 0 10000 _dl_4
time ./spawner create_files.sh 40 0 10000 _cr_5
time ./spawner delete_files.sh 40 0 10000 _dl_5

