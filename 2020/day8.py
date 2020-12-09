import os.path
__INSTRUCTIONS = [
"acc",
"jmp",
"nop"
]

test = [
    "nop +0\n",
    "acc +1\n",
    "jmp +4\n",
    "acc +3\n",
    "jmp -3\n",
    "acc -99\n",
    "acc +1\n",
    "jmp -4\n",
    "acc +6\n"
]

def accumulate(data):
    instructions = []
    for line in data:
        instructions.append([line, False])

    accumulator = 0
    index = 0
    terminated = False
    while True:
        if instructions[index][1] == True:
            break
        instructions[index][1] = True
        cmd = instructions[index][0][:3]
        value = int(instructions[index][0][4:])
        if cmd == "acc":
            accumulator += value
            index += 1
        elif cmd == "jmp":
            index += value
        else:  # cmd == "nop":
            index += 1
            pass
        if index > len(instructions):
            print(f"Possible? {index} {accumulator}")
            break
        elif index == len(instructions):
            terminated = True
            break

    return accumulator, terminated

assert accumulate(test)[0] == 5
with open(os.path.join(os.path.dirname(__file__), "day8.txt")) as fp:
    data = fp.readlines()
    print(accumulate(data)[0])

    for index in range(len(data)):
        line = data[index]
        cmd = line[:3]
        if cmd == "jmp":
            data[index] = f"nop{line[4:]}"
            ret = accumulate(data)
            if ret[1] == True:
                print(ret)
                break
            data[index] = line
        elif cmd == "nop":
            data[index] = f"jmp{line[4:]}"
            ret = accumulate(data)
            if ret[1] == True:
                print(ret)
                break
            data[index] = line