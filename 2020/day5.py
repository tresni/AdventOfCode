ROWS = 128
COLUMNS = 8
SEATS = ROWS * COLUMNS

from math import ceil

def locator(codes, divider, offset = 1):
    start = 2**(len(codes) - offset)
    offset = start / 2
    start -= 1
    for index in range(len(codes)):
        if codes[index] == divider:
            start -= offset
        offset /= 2
    return int(start)


def find_seat(seat_code):
    row = locator(seat_code[:8], 'F')
    column = locator(seat_code[7:], 'L', 0)
    return (row, column, row * 8 + column)

assert(find_seat("FBFBBFFRLR") == (44, 5, 357))
assert(find_seat("BFFFBBFRRR") == (70, 7, 567))
assert(find_seat("FFFBBBFRRR") == (14, 7, 119))
assert(find_seat("BBFFBBFRLL") == (102, 4, 820))

seats = ["E",]*ROWS*COLUMNS
import os.path
with open(os.path.join(os.path.dirname(__file__), "day5.txt")) as fp:
    max_seat = max([find_seat(line.strip())[2] for line in fp])
    assert(max_seat == 818)
    fp.seek(0, 0)
    for line in fp:
        seats[find_seat(line.strip())[2]] = "O"
    seats = "".join(seats)
    print(seats.find("E", seats.find("O")))