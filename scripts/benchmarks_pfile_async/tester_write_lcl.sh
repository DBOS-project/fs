./spawner benchmark_create.sh _cr 40 $2 -h localhost -f 1 -b 4000 

time ./spawner benchmark_write.sh _n$1_1 40 $2 -h localhost -f 1 -b 4000 -t 20 -s 16384
time ./spawner benchmark_write.sh _n$1_2 40 $2 -h localhost -f 1 -b 4000 -t 20 -s 16384
time ./spawner benchmark_write.sh _n$1_3 40 $2 -h localhost -f 1 -b 4000 -t 20 -s 16384