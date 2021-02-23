from itertools import product as prod
import sys

prefix = sys.argv[1]
user_cnt = int(sys.argv[2])
user_min = int(sys.argv[3])
user_max = user_min + user_cnt

print("%s aggregation for users %d to %d" %(prefix, user_min, user_max))

data = []
for user_num in range(user_min, user_max):    
    file_name = "../stats/"+ prefix+"_user"+str(user_num)+".out"
    with open(file_name, 'r') as f:
        for l in f:
            if l.startswith("Average throughput"):
                tokens = l.split()
                num = float(tokens[2].replace(',',''))
                data.append(num)
                    
print(data)
print(len(data))
print(sum(data)/len(data))
print(sum(data))

