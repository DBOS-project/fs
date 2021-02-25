CC=g++
CFLAGS=-std=c++11 -O0
PFLAGS=-pthread

all: spawner write_files read_files

spawner: client_spawner.o
	$(CC) $(CFLAGS) $(PFLAGS) -o spawner client_spawner.cpp

write_files: write_files.o 
	$(CC) $(CFLAGS) -o write_files write_files.o 

read_files: read_files.o 
	$(CC) $(CFLAGS) -o read_files read_files.o 

.PHONY: clean
clean:
	-rm *.o
