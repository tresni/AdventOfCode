data = [15, 5, 1, 4, 7, 0]


def elvish(start, rounds):
    known = {}
    for n in range(len(start)):
        known[start[n]] = [n, None]
    target = start[-1]
    for r in range(len(start), rounds):
        n = known[target]
        if n[1] is None:
            target = 0
        else:
            target = n[0] - n[1]
        known[target] = [r, known[target][0] if target in known else None]

    return target


assert elvish([0, 3, 6], 2020) == 436
assert elvish([1, 3, 2], 2020) == 1
assert elvish([2, 1, 3], 2020) == 10
assert elvish([1, 2, 3], 2020) == 27
assert elvish([2, 3, 1], 2020) == 78
assert elvish([3, 2, 1], 2020) == 438
assert elvish([3, 1, 2], 2020) == 1836

print(elvish(data, 2020))

assert elvish([0, 3, 6], 30000000) == 175594
assert elvish([1, 3, 2], 30000000) == 2578
assert elvish([2, 1, 3], 30000000) == 3544142
assert elvish([1, 2, 3], 30000000) == 261214
assert elvish([2, 3, 1], 30000000) == 6895259
assert elvish([3, 2, 1], 30000000) == 18
assert elvish([3, 1, 2], 30000000) == 362

print(elvish(data, 30000000))
