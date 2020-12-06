import os.path

def field_to_coords(field):
    coords = list()
    for y in range(len(field)):
        for x in range(len(field[y].strip())):
            if field[y][x] == "#":
                coords.append((x, y))
    return coords

def find_best(field):
    asteroids = field_to_coords(field)
    counter = {}
    for index in range(len(asteroids) - 1):
        asteroid = asteroids[index]
        slopes = list()
        for target in asteroids[index + 1:]:
            run = asteroid[0] - target[0]
            if run != 0:
                slope = (asteroid[1] - target[1]) / run
            else:
                slope = None

            if slope in slopes:
                continue
            slopes.append(slope)
            if asteroid not in counter:
                counter[asteroid] = 1
            else:
                counter[asteroid] += 1
            if target not in counter:
                counter[target] = 1
            else:
                counter[target] += 1

    best = max(counter, key=lambda x: counter[x])
    return (best, counter[best])

for i in range(5):
    with open(os.path.join(os.path.dirname(__file__), f"test{i}.txt"), "r") as fp:
        assertion = fp.readline()
        coord, count = assertion.split(" ")
        coord = coord.split(",")

        field = fp.readlines()
        assert(((int(coord[0]), int(coord[1])), int(count)) == find_best(field))

with open(os.path.join(os.path.dirname(__file__), "day10.txt"), "r") as fp:
    field = fp.readlines()
    print(find_best(field))