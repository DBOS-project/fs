CC=g++
CFLAGS=-std=c++11 
PFLAGS=-pthread

all: spawner write_files read_files read_files_heavy write_files_heavy

spawner: client_spawner.o client_spawner.cpp
	$(CC) $(CFLAGS) $(PFLAGS) -o spawner client_spawner.cpp

write_files: write_files.o write_files.cpp
	$(CC) $(CFLAGS) -o write_files write_files.o 

write_files_heavy: write_files_heavy.o write_files_heavy.cpp
	$(CC) $(CFLAGS) -o write_files_heavy write_files_heavy.o 

read_files: read_files.o read_files.cpp
	$(CC) $(CFLAGS) -o read_files read_files.o 

read_files_heavy: read_files_heavy.o read_files_heavy.cpp
	$(CC) $(CFLAGS) -o read_files_heavy read_files_heavy.o 

.PHONY: clean
clean:
	-rm *.o
