import os.path

OCCUPIED = "#"
EMPTY = "L"

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    data = [line.strip() for line in fp]


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


def cycle_seats(seatmap):
    new_state = []
    for row in range(len(seatmap)):
        new_state.append("")
        for seat in range(len(seatmap[row])):
            if seatmap[row][seat] == ".":
                new_state[row] += "."
                continue
            neighbors = count_neighbors(seatmap, row, seat)
            if neighbors == 0:
                new_state[row] += OCCUPIED
            elif neighbors >= 4:
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


def count_seats(seatmap):
    current = seatmap
    while True:
        temp = cycle_seats(current)
        if compare_seatmaps(temp, current):
            break
        #print_seatmap(temp)
        #print()
        current = temp

    count = 0
    for line in current:
        for char in line:
            if char == OCCUPIED:
                count += 1
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

assert count_seats(test_map) == 37
print(count_seats(data))
