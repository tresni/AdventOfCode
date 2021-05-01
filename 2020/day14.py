import os.path

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    data = [line.strip() for line in fp]

test = [
    "mask = 000000000000000000000000000000X1001X",
    "mem[42] = 100",
    "mask = 00000000000000000000000000000000X0XX",
    "mem[26] = 1",
]


def gen_register(register, mask):
    for mask, negate in flipper(mask):
        treg = register
        index = 0
        while (index := mask.find("1", index)) != -1:
            treg |= 1 << (len(mask) - index - 1)
            index += 1
        treg &= ~(negate)
        yield treg


def flipper(mask, negate=0):
    if (index := mask.find("X")) != -1:
        tmask = mask[:index] + "1" + mask[index+1:]
        yield from flipper(tmask, negate)
        tmask = mask[:index] + "0" + mask[index+1:]
        negate |= 1 << (len(mask) - index - 1)
        yield from flipper(tmask, negate)
    else:
        yield mask, negate


def process(commands):
    registers = {}
    mask = 0
    for line in commands:
        command, arg = line.split(" = ")
        if command == "mask":
            mask = arg

        elif command[:3] == "mem":
            register = int(command[4:-1])
            value = int(arg)
            bits = list(mask)
            bits.reverse()

            for r in gen_register(register, mask):
                registers[r] = value

    return(sum(registers.values()))


assert(process(test) == 208)
print(process(data))
