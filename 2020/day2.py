import os.path

a = ord('a')
def policy_requirements(policy):
    limits, character = policy.split()
    _min, _max = (int(x) for x in limits.split("-"))
    return character, _min, _max

def validate_sled_password(password):
    policy, password = password.split(": ")
    characters = [0,] * 26
    for c in password:
        characters[ord(c)-a] += 1

    policy = policy_requirements(policy)
    c = ord(policy[0]) - a
    if not (characters[c] >= policy[1] and characters[c] <= policy[2]):
        return False
    return True

assert(validate_sled_password("1-3 a: abcde") == True)
assert(validate_sled_password("1-3 b: cdefg") == False)
assert(validate_sled_password("2-9 c: ccccccccc") == True)

def validate_toboggan_password(password):
    policy, password = password.split(": ")
    letter, first, second = policy_requirements(policy)

    if ((password[first - 1] == letter and not password[second - 1] == letter) or
        (not password[first - 1] == letter and password[second - 1] == letter )):
        return True
    return False


count = 0
toboggan = 0
with open(os.path.join(os.path.dirname(__file__), "day2.txt"), "r") as fp:
    for line in fp:
        if validate_sled_password(line.strip()):
            count += 1

        if validate_toboggan_password(line):
            toboggan += 1

print(f"Sled: {count}\nToboggan: {toboggan}")