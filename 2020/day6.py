import os.path
import string

count = 0
with open(os.path.join(os.path.dirname(__file__), "day6.txt")) as fp:
    group = set(string.ascii_lowercase)
    for line in fp:
        if line == "\n":
            count += len(group)
            group = set(string.ascii_lowercase)
        else:
            group &= set([c for c in line.strip()])

    count += len(group)
print(count)