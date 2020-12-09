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

with open(os.path.join(os.path.dirname(__file__), os.path.splitext(os.path.basename(__file__))[0] + ".txt")) as fp:
    print(xmas([int(line) for line in  fp.readlines()]))
