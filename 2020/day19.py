import os
import regex as re

class RulesParser(object):
    _rules: dict[int, str]
    _parsed: dict[int, str]
    _regex: re.Pattern

    def __init__(self, rules: str):
        self._parsed = {}
        self._rules = {}

        for line in rules.split("\n"):
            index, rule = line.split(":")
            self._rules[int(index)] = rule.split()
        
        self._parsed[0] = self.create(self._rules[0])
        self._regex = re.compile(f"^{self._parsed[0]}$")

    def create(self, rule: list[str]) -> str:
        parsed = ""
        wrap = False
        for x in rule:
            if x.isnumeric():
                x = int(x)
                if x not in self._parsed:
                    if x == 8:
                        self._parsed[8] = f"(?:{self.create(self._rules[42])})+"
                    elif x == 11:
                        # Set the rule once ...
                        parsed += f"(?<rule11>{self.create(self._rules[42])}(?&rule11)?{self.create(self._rules[31])})" 
                        # reference it in the future
                        self._parsed[11] = "(?&rule11)"
                        continue
                    else:
                        self._parsed[x] = self.create(self._rules[x])
                parsed += self._parsed[x]
            elif x == "|":
                wrap = True
                parsed += "|"
            else:
                parsed += x.strip("\"")
        if wrap:
            parsed = f"(?:{parsed})"
        return parsed
                
    def regex(self) -> str:
        return self._parsed[0]

    def match(self, target: str) -> bool:
        return self._regex.match(target) != None

rp = RulesParser("""
0: 1 2
1: "a"
2: 1 3 | 3 1
3: "b"
""".strip())

assert rp.regex() == r"a(?:ab|ba)"

assert rp.match("aab")
assert rp.match("aba")
assert not rp.match("baa")
assert not rp.match("abaa")

rp = RulesParser("""
0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: "a"
5: "b"
""".strip())

assert rp.match("aaaabb")
assert rp.match("aaabab")
assert rp.match("abbabb")
assert rp.match("abbbab")
assert rp.match("aabaab")
assert rp.match("aabbbb")
assert rp.match("abaaab")
assert rp.match("ababbb")

assert not rp.match("bababa")
assert not rp.match("aaabbb")
assert not rp.match("aaaabbb")

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    rules, messages = fp.read().split("\n\n")
    rp = RulesParser(rules)
    print(sum([1 if rp.match(x) else 0 for x in messages.split()]))