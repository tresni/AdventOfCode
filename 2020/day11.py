import os.path

OCCUPIED = "#"
EMPTY = "L"
FLOOR = "."

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    data = [line.strip() for line in fp]


def count_closest(seatmap, row, column):
    # function takes a "step" tuple of how much to move (vertical, horizontal)
    #   loop function range(-1, 2) for both axis
    # stop when either we have "not floor" or index out of bounds
    # total occupied
    def step(x, y):
        srow = row
        scolumn = column
        while True:
            srow += x
            scolumn += y
            if srow < 0 or scolumn < 0 or srow == len(seatmap) or scolumn == len(seatmap[row]):
                return 0
            elif seatmap[srow][scolumn] == FLOOR:
                continue
            elif seatmap[srow][scolumn] == OCCUPIED:
                return 1
            else:
                return 0
    count = 0
    for x in range(-1, 2):
        for y in range(-1, 2):
            if x == 0 and y == 0:
                continue
            count += step(x, y)
    return count


def count_neighbors(seatmap, row, column):
    def iterrow(row, column):
        count = 0
        for c in range(-1, 2):
            try:
                # I could do this with an better check but fuck it...
                if column + c >= 0 and seatmap[row][column + c] == OCCUPIED:
                    count += 1
            except IndexError:
                pass
        return count

    count = 0
    if row > 0:
        count += iterrow(row - 1, column)
    if row < len(seatmap) - 1:
        count += iterrow(row + 1, column)
    if column > 0 and seatmap[row][column - 1] == OCCUPIED:
        count += 1
    if column < len(seatmap[row]) - 1 and seatmap[row][column + 1] == OCCUPIED:
        count += 1
    return count


def cycle_seats(seatmap, neighbor_func, to_vacate):
    new_state = []
    for row in range(len(seatmap)):
        new_state.append("")
        for seat in range(len(seatmap[row])):
            if seatmap[row][seat] == FLOOR:
                new_state[row] += FLOOR
                continue
            neighbors = neighbor_func(seatmap, row, seat)
            if neighbors == 0:
                new_state[row] += OCCUPIED
            elif neighbors >= to_vacate:
                new_state[row] += EMPTY
            else:
                new_state[row] += seatmap[row][seat]

    return new_state


def compare_seatmaps(new, old):
    for index, item in enumerate(new):
        if item != old[index]:
            return False
    return True


def print_seatmap(seatmap):
    for row in seatmap:
        print("".join(row))


def count_seats(seatmap, neighbor_func, to_vacate=4):
    current = seatmap
    while True:
        temp = cycle_seats(current, neighbor_func, to_vacate)
        if compare_seatmaps(temp, current):
            break
        print_seatmap(temp)
        print()
        current = temp

    count = 0
    for line in current:
        count += line.count("#")
    return count


test_map = [
    "L.LL.LL.LL",
    "LLLLLLL.LL",
    "L.L.L..L..",
    "LLLL.LL.LL",
    "L.LL.LL.LL",
    "L.LLLLL.LL",
    "..L.L.....",
    "LLLLLLLLLL",
    "L.LLLLLL.L",
    "L.LLLLL.LL",
]

assert count_seats(test_map, count_neighbors) == 37
assert count_seats(data, count_neighbors) == 2113

assert count_seats(test_map, count_closest, 5) == 26
print(count_seats(data, count_closest, 5))
