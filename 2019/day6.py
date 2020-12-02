import os.path

orbits = {}

with open(os.path.join(os.path.dirname(__file__), "day6.txt"), "r") as fp:
    for line in fp:
        # Terms are a lie, but they help me keep it straight
        planet, moon = line.strip().split(")")
        orbits[moon] = {"orbiting": planet, "distance": None}


def count_orbits(orbital):
    if orbital == "COM":
        return 0
    if orbits[orbital]['distance'] == None:
        return 1 + count_orbits(orbits[orbital]['orbiting'])

total = 0
for orbital in orbits:
    total += count_orbits(orbital)

print(total)
