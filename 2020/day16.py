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
            fields[options[0]] = []
            for r in options[1].strip().split(" or "):
                fields[options[0]].append(tuple(int(x) for x in r.split("-")))
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
for ticket in nearby:
    for num in ticket:
        if not any(in_ranges(ranges, num) for _, ranges in fields.items()):
            error += num

print(error)
