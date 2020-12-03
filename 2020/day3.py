import os.path

_map = [
    "..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#",
]

def count_trees(m, r, d):
    wrap = len(m[0])
    hindex = 0
    vindex = 0
    count = 0

    while True:
        vindex += d
        if vindex >= len(m):
            break

        hindex += r
        hindex %= wrap
        if m[vindex][hindex] == "#":
            count += 1
    return count


right = 3
down = 1
assert(count_trees(_map, right, down) == 7)

with open(os.path.join(os.path.dirname(__file__), "day3.txt"), "r") as fp:
    tree_map = fp.readlines()

tree_map = [x.strip() for x in tree_map]

print(count_trees(tree_map, right, down))
print()
print("Part 2")

def many_trees(m):
    product = 1
    for i in [(1, 1), (3, 1), (5, 1), (7,1), (1,2)]:
        product *= count_trees(m, *i)

    return product

assert(many_trees(_map) == 336)
print(many_trees(tree_map))
