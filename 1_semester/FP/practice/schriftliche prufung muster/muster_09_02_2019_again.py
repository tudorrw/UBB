def nested(lst):
    flat = []
    for item in lst:
        if type(item) is list:
            flat.extend(nested(item))
        else:
            flat.append(item)
    return flat

def fahrenheit(celsius_lst):
    return list(map(lambda x: (9 / 5) * x + 32, celsius_lst))


class Point:
    def __init__(self, x, y, z):
        self.x = x
        self.y = y
        self.z = z
        self.farbe = "blau"

    def color(self, gefarbt):
        self.farbe = gefarbt
    
    def __eq__(self, other):
        return self.farbe == other.farbe
    
    def __repr__(self):
        return f'Point({self.x}, {self.y}, {self.z}, farbe:{self.farbe})'


from operator import attrgetter
def sort_points(lst):
    lst.sort(key = attrgetter("farbe"))





if __name__ == '__main__':
    print(nested([2, [4, [5]], 8, 9, [[[[[[[[69]]]]]]]],[[[3]]]]))
    print(fahrenheit([6, 0, 4, 12, 30, 1]))
    
    p1 = Point(1, 3, 1)
    p1.color('rot')
    p2 = Point(5, 0, 1)
    p2.color('weiss')
    p3 = Point(1, 1, 1)
    p3.color('schwartz')
    p4 = Point(2, 4, 6)
    p4.color('gelb')
    p5 = Point(0, 0, 1)
    print(p1 == p2)
    lst = [p1, p2, p3, p4, p5]
    sort_points(lst)
    print(lst)


