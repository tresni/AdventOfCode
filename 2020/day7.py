import os.path

baglaw = {}
reverse = {}
with open(os.path.join(os.path.dirname(__file__), "day7.txt")) as fp:
    for line in fp:
        line = line.strip(".\n")
        container, rules = line.split(" contain ")
        reverse[container] = {}
        for rule in rules.split(", "):
            count, description = rule.split(" ", maxsplit=1)
            if count == "1":
                description += "s"
            if count != "no":
                reverse[container][description] = int(count)
            try:
                baglaw[description].append(container)
            except:
                baglaw[description] = [container, ]

MY_BAG = "shiny gold bags"

def find_containers(bag, containers = []):
    if bag not in baglaw:
        return []
    else:
        for outer in baglaw[bag]:
            if outer not in containers:
                containers.append(outer)
                find_containers(outer, containers)
        return containers

# assuming no recursion because that would lead to infinite bags in bags

print(len(find_containers(MY_BAG)))

def count_containers(bag):
    if not reverse[bag]:
        return 0
    count = 0
    for inner in reverse[bag]:
        temp = reverse[bag][inner]*count_containers(inner)
        count += temp + reverse[bag][inner]
    return count

print(count_containers(MY_BAG))