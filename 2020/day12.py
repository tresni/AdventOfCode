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


class Ship(object):
    x = 0
    y = 0

    w_x = 10
    w_y = 1

    def rotate(self, degree, clockwise):
        degree %= 360
        while degree > 0:
            self.w_x, self.w_y = self.w_y, self.w_x
            if clockwise:
                self.w_y *= -1
            else:
                self.w_x *= -1
            degree -= 90

    def cardinal(self, direction, distance):
        if direction == "N":
            self.w_y += distance
        elif direction == "S":
            self.w_y -= distance
        elif direction == "E":
            self.w_x += distance
        elif direction == "W":
            self.w_x -= distance
        else:
            print(f"What have you done?! {direction} {distance}")

    def forward(self, distance):
        for _ in range(distance):
            self.y += self.w_y
            self.x += self.w_x

    def sail(self, commands):
        for line in commands:
            command, argument = line[0], int(line[1:])
            if command == "F":
                self.forward(argument)
            elif command == "R":
                self.rotate(argument, True)
            elif command == "L":
                self.rotate(argument, False)
            else:
                self.cardinal(command, argument)

    def manhattan(self):
        return abs(ship.x) + abs(ship.y)


ship = Ship()
ship.sail(test)
print(ship.x, ship.y, ship.manhattan())
assert(ship.manhattan() == 286)

ship = Ship()
ship.sail(data)
print(ship.x, ship.y, ship.manhattan())
