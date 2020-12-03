from IntCode import operate, IntCode


# Python ensures large numbers just work :)
result = operate([1102,34915192,34915192,7,4,7,99,0])
assert(len(str(result[0])) == 16)
assert(operate([104,1125899906842624,99])[0] == 1125899906842624)

result = operate([109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99])
print(result)
assert(result == [109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99])