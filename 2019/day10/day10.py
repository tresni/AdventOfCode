import os.path
from math import atan2, dist, degrees
from itertools import cycle


def field_to_coords(field):
    coords = list()
    for y in range(len(field)):
        for x in range(len(field[y].strip())):
            if field[y][x] == "#":
                coords.append((x, y))
    return coords


info = {}


def add_slope_target(asteroid, slope, target):
    if asteroid not in info:
        info[asteroid] = {
            "counter": 1,
            "slopes": {
                slope: [
                    target,
                ]
            },
        }
    elif slope not in info[asteroid]["slopes"]:
        info[asteroid]["counter"] += 1
        info[asteroid]["slopes"][slope] = [
            target,
        ]
    else:
        info[asteroid]["slopes"][slope].append(target)


def find_best(field):
    info.clear()
    asteroids = field_to_coords(field)
    for index in range(len(asteroids) - 1):
        asteroid = asteroids[index]
        for target in asteroids[index + 1 :]:

            rad = degrees(atan2(asteroid[1] - target[1], asteroid[0] - target[0]))
            reverse = degrees(atan2(target[1] - asteroid[1], target[0] - asteroid[0]))

            add_slope_target(asteroid, rad, target)
            add_slope_target(target, reverse, asteroid)

    best = max(info, key=lambda x: info[x]["counter"])
    return (best, info[best]["counter"], info[best]["slopes"])


assert find_best(["#", ".", "#", ".", "#"])[0] == (0, 2)
assert find_best(["#.#.#"])[0] == (2, 0)

for i in range(5):
    with open(os.path.join(os.path.dirname(__file__), f"test{i}.txt"), "r") as fp:
        assertion = fp.readline()
        coord, count = assertion.split(" ")
        coord = coord.split(",")

        field = fp.readlines()
        best = find_best(field)
        assert ((int(coord[0]), int(coord[1])), int(count)) == best[:2]

with open(os.path.join(os.path.dirname(__file__), "day10.txt"), "r") as fp:
    field = fp.readlines()
    best = find_best(field)
    assert best[1] == 299

    for x in best[2]:
        best[2][x] = sorted(best[2][x], key=lambda x: dist(x, best[0]))

    slopes = sorted(best[2].keys())
    cycler = cycle(slopes)
    slope = None
    for slope in cycler:
        if slope >= 90:
            break

    exploded = 0
    while True:
        if len(best[2][slope]) > 0:
            exploded += 1
            if exploded == 200:
                print(best[2][slope][0])
                break

            del best[2][slope][0]
        slope = next(cycler)
