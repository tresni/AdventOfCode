import os

def solver(problem: str) -> int:
    stack = problem.split(" ")
    print(stack)
    try:
        # Addition first, but only if it exists
        while True:
            start = stack.index("+")
            stack = stack[:start - 1] + [int(stack[start - 1]) + int(stack[start + 1])] + stack[start + 2:]
    except ValueError:
        pass

    print(stack)

    result = int(stack[0])
    for i in range(1, len(stack), 2):
        rValue = int(stack[i+1])
        result *= rValue

    print(result)
    return result

def math(problem: str) -> int:
    for e in range(len(problem) - 1, -1, -1):
        if (problem[e] != "("): continue
        end = problem.find(")", e)
        problem = f"{problem[:e]}{solver(problem[e+1:end])}{problem[end+1:]}"
        print(problem)

    return solver(problem)
    

assert math("1 + 2 * 3 + 4 * 5 + 6") == 231
assert math("1 + (2 * 3) + (4 * (5 + 6))") == 51
assert math("2 * 3 + (4 * 5)") == 46
assert math("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 1445
assert math("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 669060
assert math("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 23340

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    print(sum([math(line) for line in fp]))