import math
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __sub__(self, other):
        return math.sqrt((self.x - other.x) ** 2 + (self.x - other.x) ** 2)

    def __str__(self):
        return f'Point(x = {self.x}, y = {self.y})'

class Circle:
    def __init__(self, x, y, r):
        self.c = Point(x, y)
        self.r = r
    
    def move(self, dx, dy):
        self.c.x += dx
        self.c.y += dy
    
    def area(self):
        return math.pi * self.r ** 2

    def __str__(self):
        return 'Circle(c={}, r={}'.format(self.c, self.r)
    
    def __lt__(self, other):
        return self.area() < other.area()
    


from operator import attrgetter
def sort_c(lc):
    lc.sort(key = lambda c : c.area())
    # lc.sort(reverse = True)
    
def sort_cz(lc):
    return sorted(lc, key = attrgetter('area'))

def main():
    p1 = Point(1,2)
    p2 = Point(2, 3)
    d = p1 - p2
    print(d)
    print(p1)

    lc = [Circle(1, 2, 5), Circle(5, 7, 3), Circle(1,1, 2)]
    
    # sort_c(lc)
    # for c in lc: print(c)

    sort_cz(lc)
    for c in lc: print(c)

    
main()