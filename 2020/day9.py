import os.path
from day1 import find_two

PREAMBE = 25

def xmas(stream, preamble=PREAMBE):
    fifo = []
    istream = iter(stream)
    for i in range(preamble):
        fifo.append(next(istream))

    for i in istream:
        f = sorted(fifo)
        if find_two(f, i) == (None, None):
            return i
        else:
            del fifo[0]
            fifo.append(i)

test = [
    35,
    20,
    15,
    25,
    47,
    40,
    62,
    55,
    65,
    95,
    102,
    117,
    150,
    182,
    127,
    219,
    299,
    277,
    309,
    576
]
assert xmas(test, 5) == 127

def weakness(key, data):
    block = []
    for i in range(len(data)):
        value = data[i]
        for j in range(i+1, len(data)):
            value += data[j]
            if value == key:
                block = data[i:j+1]
    block = sorted(block)
    return block[0], block[-1]

with open(os.path.join(os.path.dirname(__file__), os.path.splitext(os.path.basename(__file__))[0] + ".txt")) as fp:
    lines = [int(line) for line in  fp.readlines()]
    key = xmas(lines)
    assert key == 466456641
    weak = weakness(key, lines)
    print(weak[0] + weak[1])



