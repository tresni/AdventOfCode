import os.path

fields = {}
mine = []
nearby = []

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    storing = "mine"
    for line in fp:
        options = line.strip().split(":")
        if options[0] == '':
            continue

        if len(options) == 2 and options[1] != '':
            fields[options[0]] = {"ranges": []}
            for r in options[1].strip().split(" or "):
                fields[options[0]]["ranges"].append(tuple(int(x) for x in r.split("-")))
        elif options[0] == "your ticket":
            storing = "mine"
        elif options[0] == "nearby tickets":
            storing = "nearby"
        elif len(options) == 1:
            if storing == "mine":
                mine = [int(x) for x in options[0].split(",")]
            elif storing == "nearby":
                nearby.append([int(x) for x in options[0].split(",")])


def in_ranges(ranges, num):
    for r in ranges:
        if r[0] <= num and r[1] >= num:
            return True
    return False


error = 0
for ticket in nearby.copy():
    for num in ticket:
        if not any(in_ranges(d["ranges"], num) for _, d in fields.items()):
            nearby.remove(ticket)
            error += num
            break  # can stop processing this ticket

print(error)

gathered = []
for i in range(len(fields)):
    gathered.append([x[i] for x in nearby])

for key, d in fields.items():
    d["targets"] = []
    for i in range(len(gathered)):
        if all(in_ranges(d["ranges"], num) for num in gathered[i]):
            d["targets"].append(i)

# while we have fields with more than 1 target
cleaned = fields.copy()
while cleaned:
    target, d = min(cleaned.items(), key=lambda x: len(x[1]['targets']))
    for k, v in fields.items():
        if k != target:
            try:
                v['targets'].remove(d['targets'][0])
                cleaned[k]['targets'] = v['targets']
            except ValueError:
                continue
    del cleaned[target]

value = 1
for k, v in fields.items():
    if "departure" in k:
        print(f"{k} value is {mine[v['targets'][0]]}")
        value *= mine[v['targets'][0]]

print(value)


#print(gathered)
