from itertools import tee

small_test = [
    16,
    10,
    15,
    5,
    1,
    11,
    7,
    19,
    6,
    12,
    4,
]

test = [
    28,
    33,
    18,
    42,
    31,
    14,
    46,
    20,
    48,
    47,
    24,
    23,
    49,
    45,
    19,
    38,
    39,
    11,
    1,
    32,
    25,
    35,
    8,
    17,
    7,
    9,
    4,
    2,
    34,
    10,
    3,
]

data = [
    99,
    3,
    1,
    11,
    48,
    113,
    131,
    43,
    82,
    19,
    4,
    153,
    105,
    52,
    56,
    109,
    27,
    119,
    147,
    31,
    34,
    13,
    129,
    17,
    61,
    10,
    29,
    24,
    12,
    104,
    152,
    103,
    80,
    116,
    79,
    73,
    21,
    133,
    44,
    18,
    74,
    112,
    136,
    30,
    146,
    100,
    39,
    130,
    91,
    124,
    70,
    115,
    81,
    28,
    151,
    2,
    122,
    87,
    143,
    62,
    7,
    126,
    95,
    75,
    20,
    123,
    63,
    125,
    53,
    45,
    141,
    14,
    67,
    69,
    60,
    114,
    57,
    142,
    150,
    42,
    78,
    132,
    66,
    88,
    140,
    139,
    106,
    38,
    85,
    37,
    51,
    94,
    98,
    86,
    68,
]


def pairwise(iterable):
    "s -> (s0,s1), (s1,s2), (s2, s3), ..."
    a, b = tee(iterable)
    next(b, None)
    return zip(a, b)


small_test.append(0)
small_test = sorted(small_test)
assert (
    len(list(filter(lambda x: x[1] - x[0] == 1, pairwise(small_test))))
    * (len(list(filter(lambda x: x[1] - x[0] == 3, pairwise(small_test)))) + 1)
    == 35
)

test.append(0)
test = sorted(test)
assert (
    len(list(filter(lambda x: x[1] - x[0] == 1, pairwise(test))))
    * (len(list(filter(lambda x: x[1] - x[0] == 3, pairwise(test)))) + 1)
    == 220
)


# This is the answer to day 1
data.append(0)
data = sorted(data)
assert (
    len(list(filter(lambda x: x[1] - x[0] == 1, pairwise(data))))
    * (len(list(filter(lambda x: x[1] - x[0] == 3, pairwise(data)))) + 1)
    == 1980
)


def permutations(data, cache):
    count = 0
    for i in range(min(3, len(data)-1)):
        if data[1+i] - data[0] <= 3:
            if 1+i == len(data) - 1:
                return 1
            else:
                if data[1+i] not in cache:
                    cache[data[1+i]] = permutations(data[1+i:], cache)
                count += cache[data[1+i]]
        else:
            continue

    return count


small_test.append(small_test[-1] + 3)
test.append(test[-1] + 3)
data.append(data[-1] + 3)

assert permutations(small_test, {}) == 8
assert permutations(test, {}) == 19208
print(permutations(data, {}))
