from collections import defaultdict
from enum import Enum

ACTIVE = "#"
INACTIVE = "."

INIT_CYCLES = 6

class Coord(object):
    X = 0
    Y = 1
    Z = 2

class PocketDimension(object):
    __initSize: tuple[int, int, int] = [0, 0, 0]
    __cycle: int = 0
    __state: dict[tuple[int, int, int],  str] = defaultdict(lambda: INACTIVE)

    def __init__(self, data: str):
        yIndex: int = 0
        for line in data.splitlines(keepends=False):
            lineLength = len(line)
            self.__initSize[Coord.X] = max(self.__initSize[Coord.X], lineLength)
            if not line:
                self.__initSize[Coord.Z] += 1
                self.__initSize[Coord.Y] = max(self.__initSize[Coord.Y], yIndex)
                yIndex = 0
            for index in range(lineLength):
                self.__state[index, yIndex, self.__initSize[Coord.Z]] = line[index]
            yIndex += 1
        self.__initSize[Coord.Y] = max(self.__initSize[Coord.Y], yIndex)

    def __active_neighbor_count(self, coordinates: tuple[int, int, int]) -> int:
        count = 0
        offsets = [-1, 0 , 1]

        for zOffset in offsets:
            for yOffset in offsets:
                for xOffset in offsets:
                    if not zOffset and not yOffset and not xOffset: continue
                    if self.__state[(
                        coordinates[Coord.X] + xOffset,
                        coordinates[Coord.Y] + yOffset,
                        coordinates[Coord.Z] + zOffset,
                    )] == ACTIVE:
                        count += 1
        return count


    def cycle(self):
        self.__cycle += 1
        newState: dict[tuple[int, int, int], str] = defaultdict(lambda: INACTIVE)
        cycle = self.__cycle
        for x in range(-cycle, cycle + self.__initSize[Coord.X]):
            for y in range(-cycle, cycle + self.__initSize[Coord.Y]):
                for z in range(-cycle, cycle + self.__initSize[Coord.Z] + 1):
                    cell = (x, y, z)
                    active = self.__active_neighbor_count(cell)
                    currentState = self.__state[cell]
                    if currentState == INACTIVE and active == 3:
                        newState[cell] = ACTIVE
                    elif currentState == ACTIVE and active not in [2, 3]:
                        newState[cell] = INACTIVE
                    else:
                        newState[cell] = currentState
        self.__state = newState

    def countActive(self, ) -> int:
        return len(list(filter(lambda val: val == ACTIVE, self.__state.values())))

    def printState(self):
        cycle = self.__cycle
        for z in range(-cycle, cycle + self.__initSize[Coord.Z] + 1):
            print(f"z={z}")
            for y in range(-cycle, cycle + self.__initSize[Coord.Y]):
                for x in range(-cycle, cycle + self.__initSize[Coord.X]):
                    print(self.__state[(x, y, z)], end="")
                print()
            print()

test = """
.#.
..#
###
""".strip()
    
test = PocketDimension(test)
test.printState()

for i in range(6):
    test.cycle()

assert test.countActive() == 112

full = """
.#######
#######.
###.###.
#....###
.#..##..
#.#.###.
###..###
.#.#.##.
""".strip()

full = PocketDimension(full)

for i in range(6):
    full.cycle()

print(full.countActive())