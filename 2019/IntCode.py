from math import prod
from queue import SimpleQueue

class IntCode(object):
    def __init__(self, instructions, input_=None, output=None, name="IntCode"):
        self._instructions = instructions
        self.input = input_ if input_ else SimpleQueue()
        self.output = output if output else SimpleQueue()
        self.name = name

    def _getParams(self, params, modes):
        result = []
        for i in params:
            m = modes % 10
            if m:
                result.append(i)
            else:
                result.append(self._instructions[i])
            modes //= 10
        return result

    def _operfunc(self, index, mode, func):
        return func(self._getParams(self._instructions[index+1:index+3], mode))

    def _jumpfunc(self, index, mode, func):
        results = self._getParams(self._instructions[index+1:index+3], mode)
        if func(results[0]):
            return results[1]
        else:
            return index + 3

    def _comparefunc(self, index, mode, func):
        results = self._getParams(self._instructions[index+1:index+3], mode)
        if func(*results):
            self._instructions[self._instructions[index+3]] = 1
        else:
            self._instructions[self._instructions[index+3]] = 0


    def operate(self, *inputs):
        n = 0
        opcodes = self._instructions
        while n <= len(self._instructions):
            # placeholder for refactor
            opcode = self._instructions[n]
            mode = opcode // 100
            opcode = opcode % 100
            if opcode == 1: # Add
                opcodes[opcodes[n+3]] = self._operfunc(n, mode, sum)
                n += 4
            elif opcode == 2: # Multiply
                opcodes[opcodes[n+3]] = self._operfunc(n, mode, prod)
                n += 4
            elif opcode == 3: # Input
                result = self.input.get()
                opcodes[opcodes[n+1]] = int(result)
                n += 2
            elif opcode == 4: # Output
                output = self._getParams(opcodes[n+1:n+2], mode)
                self.output.put_nowait(*output)
                n += 2
            elif opcode == 5: # Jump-if-True
                n = self._jumpfunc(n, mode, lambda x: x)
            elif opcode == 6: # Jump-if-False
                n = self._jumpfunc(n, mode, lambda x: not x)
            elif opcode == 7: # Less Than
                self._comparefunc(n, mode, lambda x, y: x < y)
                n += 4
            elif opcode == 8: # Equals
                self._comparefunc(n, mode, lambda x, y: x == y)
                n += 4
            elif opcode == 99:
                return
            else:
                print(f"Fuck {n}:{opcodes[n]}")
                return None

    def result(self):
        # This is bad as you can't tell which you are getting
        result = []
        while True:
            try:
                result.append(self.output.get_nowait())
            except:
                break

        if result:
            return result
        else:
            return self._instructions

def operate(opcodes, *inputs):
    ic = IntCode(opcodes)
    for item in inputs:
        ic.input.put_nowait(item)
    ic.operate()
    return ic.result()