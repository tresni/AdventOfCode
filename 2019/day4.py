def is_valid_password(password):
    counts = [0, ] * 10
    has_double = 0
    previous = " "
    count = 0
    for c in str(password):
        if c < previous:
            return False
        if c == previous:
            count += 1
        elif previous != " ":
            counts[ord(previous) - ord("0")] = count
            count = 0
        previous = c
    counts[ord(previous) - ord("0")] = count

    try:
        return min(filter(lambda x: x > 0, counts)) == 1
    except ValueError:
        return False

assert(is_valid_password(122345))
#assert(is_valid_password(111123)) # No longer valid
#assert(is_valid_password(111111))
assert(is_valid_password(112233))
assert(is_valid_password(111122))
assert(not is_valid_password(123444))
assert(not is_valid_password(223450))
assert(not is_valid_password(123789))

count = 0
for password in range(246540, 787419):
    if is_valid_password(password):
        count +=1

print(count)