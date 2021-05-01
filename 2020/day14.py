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
    "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
    "mem[8] = 11",
    "mem[7] = 101",
    "mem[8] = 0",
]


def process(commands):
    registers = {}
    mask = 0
    for line in commands:
        command, arg = line.split(" = ")
        if command == "mask":
            mask = arg

        elif command[:3] == "mem":
            register = command[4:-1]
            value = int(arg)
            index = 0
            while (index := mask.find("1", index)) != -1:
                value |= 1 << len(mask) - index - 1
                index = index + 1
            index = 0
            while (index := mask.find("0", index)) != -1:
                value &= ~(1 << len(mask) - index - 1)
                index = index + 1

            registers[register] = value

    return(sum(registers.values()))


assert(process(test) == 165)
print(process(data))
