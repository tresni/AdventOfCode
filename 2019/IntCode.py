from math import prod
from queue import SimpleQueue

class IntCode(object):
    def __init__(self, instructions, input_=None, output=None, name="IntCode"):
        self._instructions = instructions
        self.input = input_ if input_ else SimpleQueue()
        self.output = output if output else SimpleQueue()
        self.name = name
        self.relative = 0

    def readRegister(self, index, count=1):
        end = index + count
        l = len(self._instructions)
        if end >= l:
            size = (end - l) * 2
            self._instructions.extend([0, ] * size)
        return self._instructions[index:end]

    def writeRegister(self, index, value):
        l = len(self._instructions)
        if index >= l:
            size = ((index - l) + 1) * 2
            self._instructions.extend([0,] * size)

        self._instructions[index] = value

    def getAddress(self, param, modes):
        if modes == 2:
            return self.relative + param
        else:
            return param


    def _getParams(self, params, modes):
        result = []
        for i in params:
            m = modes % 10
            if m == 1:
                result.append(i)
            elif m == 2:
                result.append(self.readRegister(self.relative + i)[0])
            else:
                result.append(self.readRegister(i)[0])
            modes //= 10
        return result

    def _operfunc(self, index, mode, func):
        return func(self._getParams(self.readRegister(index+1, 2), mode))

    def _jumpfunc(self, index, mode, func):
        results = self._getParams(self.readRegister(index+1, 2), mode)
        if func(results[0]):
            return results[1]
        else:
            return index + 3

    def _comparefunc(self, index, mode, func):
        results = self._getParams(self.readRegister(index+1, 2), mode)
        if func(*results):
            self.writeRegister(self.getAddress(self.readRegister(index+3)[0], mode//100), 1)
        else:
            self.writeRegister(self.getAddress(self.readRegister(index+3)[0], mode//100), 0)


    def operate(self, *inputs):
        n = 0
        opcodes = self._instructions
        while n <= len(self._instructions):
            # placeholder for refactor
            opcode = self._instructions[n]
            mode = opcode // 100
            opcode = opcode % 100
            if opcode == 1: # Add
                self.writeRegister(self.getAddress(self.readRegister(n+3)[0], mode//100), self._operfunc(n, mode, sum))
                n += 4
            elif opcode == 2: # Multiply
                self.writeRegister(self.getAddress(self.readRegister(n+3)[0], mode//100), self._operfunc(n, mode, prod))
                n += 4
            elif opcode == 3: # Input
                result = self.input.get()
                self.writeRegister(self.getAddress(self.readRegister(n+1)[0], mode), int(result))
                n += 2
            elif opcode == 4: # Output
                output = self._getParams(self.readRegister(n+1), mode)
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
            elif opcode == 9: # Relative Positioning
                self.relative += self._getParams(self.readRegister(n+1), mode)[0]
                n += 2
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