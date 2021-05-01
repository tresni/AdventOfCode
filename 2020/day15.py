data = [15, 5, 1, 4, 7, 0]


def elvish(start, rounds):
    spoken = start
    spoken.reverse()
    for r in range(len(spoken), rounds):
        target = spoken[0]
        try:
            index = spoken.index(target, 1)
            spoken.insert(0, r - (len(spoken) - index))
        except ValueError:
            spoken.insert(0, 0)

    return spoken[0]


assert elvish([0, 3, 6], 2020) == 436
assert elvish([1, 3, 2], 2020) == 1
assert elvish([2, 1, 3], 2020) == 10
assert elvish([1, 2, 3], 2020) == 27
assert elvish([2, 3, 1], 2020) == 78
assert elvish([3, 2, 1], 2020) == 438
assert elvish([3, 1, 2], 2020) == 1836

print(elvish(data, 2020))