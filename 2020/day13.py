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


assert(find_bus(test) == 295)
print(find_bus(data))
