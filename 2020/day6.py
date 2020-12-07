import os.path

count = 0
with open(os.path.join(os.path.dirname(__file__), "day6.txt")) as fp:
    group = set()
    for line in fp:
        if line == "\n":
            count += len(group)
            group = set()
        [group.add(c) for c in line.strip()]
    count += len(group)
print(count)