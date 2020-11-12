from itertools import product as prod
import sys

benchmarks = ["read", "write"]
sizes = ["1B", "1KB", "256KB", "512KB", "768KB", "1MB"]

data = {
    bench: {
        size: {
            "aggregate": 0
        } for size in sizes
    } for bench in benchmarks
}

for (bench, size) in prod(benchmarks, sizes):
    for user_num in range(40):
        file_name = "../stats/single_"+bench+"_bench_spawn_"+ \
                    size+"_user"+str(user_num)+".out"

        with open(file_name, 'r') as f:
            for l in f:
                if l.startswith("Average throughput"):
                    tokens = l.split()
                    num = float(tokens[2].replace(',',''))
                    data[bench][size]["aggregate"] += num
                    
for x in data:
    print(x)
    for y in data[x]:
        print(y)
        print(data[x][y])
