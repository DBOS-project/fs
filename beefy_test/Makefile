CC=g++
CFLAGS=-std=c++11
PFLAGS=-pthread

all: spawner write_files write_files_many read_files read_files_many write_files_once

spawner: client_spawner.o client_spawner.cpp
	$(CC) $(CFLAGS) $(PFLAGS) -o spawner client_spawner.cpp

write_files: write_files.o write_files.cpp
	$(CC) $(CFLAGS) -o write_files write_files.o

write_files_many: write_files_many.o write_files_many.cpp
	$(CC) $(CFLAGS) -o write_files_many write_files_many.o

write_files_once: write_files_once.o write_files_once.cpp
	$(CC) $(CFLAGS) -o write_files_once write_files_once.o

read_files: read_files.o read_files.cpp
	$(CC) $(CFLAGS) -o read_files read_files.o

read_files_heavy: read_files_many.o read_files_many.cpp
	$(CC) $(CFLAGS) -o read_files_many read_files_many.o

.PHONY: clean
clean:
	rm *.o
	rm write_files write_files_many read_files read_files_many write_files_once
