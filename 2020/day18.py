import os

def solver(problem: str) -> int:
    stack = problem.split(" ")
    result = int(stack[0])
    for i in range(1, len(stack), 2):
        oper = stack[i]
        rValue = int(stack[i+1])
        print(f"{result} {oper} {rValue}")

        if oper == "+":
            result += rValue
        elif oper == "*":
            result *= rValue
        else:
            raise ValueError("What the shit?!")

    print(result)
    return result

def math(problem: str) -> int:
    for e in range(len(problem) - 1, -1, -1):
        if (problem[e] != "("): continue
        end = problem.find(")", e)
        problem = f"{problem[:e]}{solver(problem[e+1:end])}{problem[end+1:]}"
        print(problem)

    return solver(problem)
    

assert math("1 + 2 * 3 + 4 * 5 + 6") == 71
assert math("1 + (2 * 3) + (4 * (5 + 6))") == 51
assert math("2 * 3 + (4 * 5)") == 26
assert math("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 437
assert math("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 12240
assert math("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 13632

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    print(sum([math(line) for line in fp]))