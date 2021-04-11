CC=g++
CFLAGS=-std=c++11
PFLAGS=-pthread

objects = write_files_once write_files write_files_rnd read_files read_files_rnd 
par_objects = spawner write_files_async write_files_rnd_async read_files_async read_files_rnd_async

all: $(objects) $(par_objects)

$(objects): %: %.cpp
	$(CC) $(CFLAGS) -o $@ $<

$(par_objects): %: %.cpp
	$(CC) $(CFLAGS) $(PFLAGS) -o $@ $<


clean:
	rm -f $(objects) $(par_objects)

.PHONY: all clean

