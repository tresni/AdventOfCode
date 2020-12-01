def is_valid_password(password):
    has_double = False
    previous = " "
    for c in str(password):
        if c < previous:
            return False
        if c == previous:
            has_double = True
        previous = c
    return has_double

assert(is_valid_password(122345))
assert(is_valid_password(111123))
assert(is_valid_password(111111))
assert(not is_valid_password(223450))
assert(not is_valid_password(123789))

count = 0
for password in range(246540, 787419):
    if is_valid_password(password):
        count +=1

print(count)