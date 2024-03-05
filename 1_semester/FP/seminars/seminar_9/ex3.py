import math 
from dataclasses import dataclass
@dataclass
class Point:
    x : float
    y : float

@dataclass(order = True) #genereaza o metoda __lt__
class Circle:

    c : Point
    r : float

    def move(self, dx, dy):
        self.c.x += dx
        self.c.y += dy
    
    def area(self):
        return math.pi * self.r ** 2

    def __str__(self):
        return 'Circle(c={}, r={}'.format(self.c, self.r)
        
def main():
    c = Circle(Point(1, 2), 10)
    print(c)
main()

 