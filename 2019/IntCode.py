from math import prod

class IntCode(object):
    def __init__(self, instructions):
        self._instructions = instructions
        self.output = []

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

    @property
    def inputs(self):
        val = next(self._input)
        if val is not None:
            return val
        else:
            return input("Input: ")

    @inputs.setter
    def inputs(self, inputs):
        self._input = iter(inputs)


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
                opcodes[opcodes[n+1]] = int(self.inputs)
                n += 2
            elif opcode == 4: # Output
                self.output.append(*self._getParams(opcodes[n+1:n+2], mode))
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
        if self.output:
            return self.output
        else:
            return self._instructions

def operate(opcodes, *inputs):
    ic = IntCode(opcodes)
    ic.inputs = inputs
    ic.operate()
    return ic.result()