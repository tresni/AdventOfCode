import os.path
import re

data = """ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in
""".split("\n\n")

def validate_passport(data):
    data = data.strip()
    pairs = dict([x.split(":") for x in data.split(" ")])
    if not set(pairs.keys()) >= set(["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]):
        return False
    if int(pairs["byr"]) < 1920 or int(pairs["byr"]) > 2002:
        return False
    if int(pairs["iyr"]) < 2010 or int(pairs["iyr"]) > 2020:
        return False
    if int(pairs["eyr"]) < 2020 or int(pairs["eyr"]) > 2030:
        return False
    if pairs["hgt"][-2:] == "cm" and (int(pairs["hgt"][:-2]) < 150 or int(pairs["hgt"][:-2]) > 193):
        return False
    elif pairs["hgt"][-2:] == "in" and (int(pairs["hgt"][:-2]) < 59 or int(pairs["hgt"][:-2]) > 76):
        return False
    elif pairs["hgt"][-2:] != "cm" and pairs["hgt"][-2:] != "in":
        return False
    if not re.match(r'#[0-9a-f]{6}', pairs["hcl"]):
        return False
    if pairs["ecl"] not in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]:
        return False
    if not re.match(r'\d{9}$', pairs["pid"]):
        return False
    return True

def read_passports(data):
    valid = 0
    for passport in data:
        passport = passport.replace("\n", " ")
        if validate_passport(passport):
            valid += 1
    return valid

valid_passports = read_passports(data)
#assert(valid_passports == 2)

with open(os.path.join(os.path.dirname(__file__), "day4.txt"), "r") as fp:
    data = fp.read().split("\n\n")
    print(read_passports(data))