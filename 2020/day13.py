import math
import os.path
from sys import maxsize as MAX_INT  # noqa

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    data = [line.strip() for line in fp]


test = [
    "939",
    "7,13,x,x,59,x,31,19",
]


def find_bus(test):
    earliest = int(test[0])
    buses = [int(x) for x in filter(lambda x: x != "x", test[1].split(","))]

    minimum = [MAX_INT, MAX_INT]
    for bus in buses:
        next_bus = math.ceil(earliest / bus) * bus
        if next_bus < minimum[1]:
            minimum = [bus, next_bus]
    print(minimum)
    delta = minimum[1] - earliest
    return delta * minimum[0]


def contest(data):
    pass
    # find max number in list
    # find detla from current to max number
    # iterate on that?
    schedule = [int(x) if x != "x" else x for x in data[1].split(",")]
    buses = list(filter(lambda x: x != "x", schedule))
    premium = max(buses)

    firstbus = premium - schedule.index(premium)
    while True:
        time = firstbus
        found = True
        for bus in schedule:
            if type(bus) == int and time % bus:
                found = False
                break
            time += 1
        if found:
            return firstbus

        firstbus += premium


assert(find_bus(test) == 295)
print(find_bus(data))

assert(contest(test) == 1068781)
assert(contest(["", "17,x,13,19"]) == 3417)
assert(contest(["", "67,7,59,61"]) == 754018)
assert(contest(["", "67,x,7,59,61"]) == 779210)
assert(contest(["", "67,7,x,59,61"]) == 1261476)
assert(contest(["", "1789,37,47,1889"]) == 1202161486)
print(contest(data))
