import os.path

with open(
    os.path.join(
        os.path.dirname(__file__),
        os.path.splitext(os.path.basename(__file__))[0] + ".txt",
    ),
    "r",
) as fp:
    data = [line.strip() for line in fp]

test = [
    "F10",
    "N3",
    "F7",
    "R90",
    "F11",
]

NORTH = 0
EAST = 90
SOUTH = 180
WEST = 270


class Ship(object):
    heading = EAST
    x = 0
    y = 0

    def rotate(self, degree):
        self.heading += degree
        self.heading %= 360

    def cardinal(self, direction, distance):
        if direction == "N":
            self.y += distance
        elif direction == "S":
            self.y -= distance
        elif direction == "E":
            self.x += distance
        elif direction == "W":
            self.x -= distance
        else:
            print(f"What have you done?! {direction} {distance}")

    def forward(self, distance):
        if self.heading == NORTH:
            self.y += distance
        elif self.heading == SOUTH:
            self.y -= distance
        elif self.heading == EAST:
            self.x += distance
        elif self.heading == WEST:
            self.x -= distance
        else:
            print(f"Ya done fucked up! {self.heading} {distance}")

    def sail(self, commands):
        for line in commands:
            command, argument = line[0], int(line[1:])
            if command == "F":
                self.forward(argument)
            elif command == "R":
                self.rotate(argument)
            elif command == "L":
                self.rotate(-argument)
            else:
                self.cardinal(command, argument)

    def manhattan(self):
        return abs(ship.x) + abs(ship.y)


ship = Ship()
ship.sail(test)
print(ship.x, ship.y, ship.manhattan())
assert(ship.manhattan() == 25)

ship = Ship()
ship.sail(data)
print(ship.x, ship.y, ship.manhattan())
