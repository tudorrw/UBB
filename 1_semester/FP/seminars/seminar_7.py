# 1
import random
class wurfel:
    def __init__(self, sides = 6):
        self.sides = sides

    def roll(self):
        return random.randint(1, self.sides)  

def roll(die):
    r = die.roll()
    while r != 6:
        print(r)
        r = die.roll()
    print(r)
# 2
class Bruch:
    def __init__(self, n, m):
        self.n = n
        self.m = m

    def exit(self, k):
        self.n *= k
        self.m *= k

    def sim(self, k):
        self.n //= k
        self.m //= k
    def exit_new(self, k):
        return Bruch(self.n * k, self.m * k)

    def print(self):
        print(str(self.n) + '/' + str(self.m))
        
    def print_fancy(self):
        print(f'{str(self.n)}/{str(self.m)}')

    def print_old(self):
        print('%d/%d' %(self.n, self.m))
# 3


class Statistics:
    def __init__(self):
        self.autos = []
    def add_auto(self, auto):
        self.autos.append(auto)
        
        def aaf(self, f): #anzahl auto farbe
            c = 0
            for auto in self.autos:
                if auto.f == f:
                    c += 1
            return c
        def amm(self, m):
            b, c = 0, 0
            for auto in self.autos:
                if auto.m == m:
                    c += 1
                    b += auto.b
            return b, c
class Auto:
    def __init__(self, b, f, m):
        self.b = b
        self.f = f
        self.m = m 
   
# 5
from math import pi
class Circle():
    def __init__(self, radius, x, y):
        self.radius = radius
        self.center = Point(x, y)

    def move(self, dx, dy):
        self.circle = (self.circle[0] + dx, self.circle[1] + dy)

    def  resize(self, k):
        self.radius *= k 
    
    def area(self):
        return pi * self.radius ** 2

class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


class Box:
    def __init__(self):
        self.figs = []
    def add(self, f):
        self.figs.append(f)
    def compute_area(self):
        for f in self.figs:
            print(f.area())

def main():
    # 1
    dice = wurfel()
    roll(dice)

    # 2
    # b = Bruch(1, 2)
    # b.exit(10)
    # print(b.n, b.m)
    # g = b.exit_new(10)
    # print(g.n, g.m)
    # print(b.n, b.m)
    # g.print()

    # 3
    # s = Statistics()
    # a = Auto(1907, 'n', 'x')
    # s.add_auto(a)
    # print(s.amm('x'))

    # 5
    c = Circle(4, 1, 2)
    # print(c.circle.x, c.circle.y)
    # c.resize(10)
    # print(c.circle.x, c.circle.y, c.radius)
    # print(c.area())
    c1 = Circle(7,5,1)
    b = Box()
    b.add(c)
    b.add(c1)
    b.compute_area()


main()