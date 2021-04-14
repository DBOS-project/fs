from itertools import product as prod
import sys

prefix = sys.argv[1]
user_cnt = int(sys.argv[2])
user_min = int(sys.argv[3])
user_max = user_min + user_cnt

print("%s aggregation for users %d to %d" %(prefix, user_min, user_max))

data_best = []
data_avg = []
for user_num in range(user_min, user_max):    
    file_name = "./stats/"+ prefix+"_"+str(user_num)+".out"
    with open(file_name, 'r') as f:
        for l in f:
            if l.startswith("throughput (best)"):
                tokens = l.split()
                num = float(tokens[2].replace(',',''))
                data_best.append(num)
                    
            if l.startswith("throughput (avg)"):
                tokens = l.split()
                num = float(tokens[2].replace(',',''))
                data_avg.append(num)
                    
print(data_best)
print(len(data_best))
print(sum(data_best)/len(data_best))
print(sum(data_best))

print(data_avg)
print(len(data_avg))
print(sum(data_avg)/len(data_avg))
print(sum(data_avg))
