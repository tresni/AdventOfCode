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

    def operate(self, *inputs):
        n = 0
        opcodes = self._instructions
        while n <= len(self._instructions):
            # placeholder for refactor
            opcode = self._instructions[n]
            mode = opcode // 100
            opcode = opcode % 100
            if opcode == 1: # Add
                opcodes[opcodes[n+3]] = sum(self._getParams(opcodes[n+1:n+3], mode))
                n += 4
            elif opcode == 2: # Multiply
                opcodes[opcodes[n+3]] = prod(self._getParams(opcodes[n+1:n+3], mode))
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
                results = self._getParams(opcodes[n+1:n+3], mode)
                if results[0]:
                    n = results[1]
                else:
                    n += 3
            elif opcode == 6: # Jump-if-False
                results = self._getParams(opcodes[n+1:n+3], mode)
                if not results[0]:
                    n = results[1]
                else:
                    n += 3
            elif opcode == 7: # Less Than
                results = self._getParams(opcodes[n+1:n+3], mode)
                if results[0] < results[1]:
                    opcodes[opcodes[n+3]] = 1
                else:
                    opcodes[opcodes[n+3]] = 0
                n += 4
            elif opcode == 8: # Equals
                results = self._getParams(opcodes[n+1:n+3], mode)
                if results[0] == results[1]:
                    opcodes[opcodes[n+3]] = 1
                else:
                    opcodes[opcodes[n+3]] = 0
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