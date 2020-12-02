import os.path

orbits = {}

with open(os.path.join(os.path.dirname(__file__), "day6.txt"), "r") as fp:
    for line in fp:
        # Terms are a lie, but they help me keep it straight
        planet, moon = line.strip().split(")")
        orbits[moon] = {"orbiting": planet, "distance": None, "path": None}


def count_orbits(orbital):
    if orbital == "COM":
        return 0

    # Don't calculate it multiple times
    # Saves ~1/2 the time :)
    if orbits[orbital]['distance'] == None:
        orbits[orbital]['distance'] = 1 + count_orbits(orbits[orbital]['orbiting'])

    return orbits[orbital]['distance']

total = 0
for orbital in orbits:
    total += count_orbits(orbital)

print(f"Orbit Count Checksums: {total}")

def path_to_com(start):
    if start == "COM":
        return ["COM",]

    obj = orbits[start]
    path = obj['path']
    if path == None:
        path = [start] + path_to_com(obj['orbiting'])

    return path

# -2 to remove YOU and SAN from the result sets
print(len(set(path_to_com("YOU")) ^ set(path_to_com("SAN"))) - 2)
