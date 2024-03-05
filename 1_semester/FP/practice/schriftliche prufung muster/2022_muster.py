
# Aufgabe 3

# A
def extract_parentheses(txt):
    if txt[0] == "(" and txt[-1] == ")":
        return txt
    elif txt[0] == "(":
        return extract_parentheses(txt[:-1])
    elif txt[-1]  == ")":
        return extract_parentheses(txt[1:])
    else:
        return extract_parentheses(txt[1:-1])


print(extract_parentheses("22a3(abcd)Xy2Z"))
# B.
from functools import  reduce
def matrix_sum(matrix):
    return reduce(lambda x, y: x + y, reduce(lambda x, y: x + y, matrix))
    return reduce(lambda x, y: x + y, list(map(lambda lines: reduce(lambda x, y: x + y, lines), matrix)))

# Aufgabe 4
# A
class Square:
    def __init__(self, x, y, side):
        self.x = x
        self.y = y
        self.side = side
    
    def __repr__(self):
        return f"Square: x:{self.x}, y:{self.y}"
# B
class Rectangle:
    def __init__(self, x, y, length, width):
        self.x = x
        self.y = y
        self.length = length
        self.width = width


# C
def contains(quadraten_lst, rechteck):
   boolean_lst = list(map(lambda quadrat: True if quadrat.x >= rechteck.x and quadrat.y <= rechteck.y and quadrat.x + quadrat.side <= rechteck.length and quadrat.y + quadrat.side <= rechteck.width else False, quadraten_lst))
   return False if False in boolean_lst else True
 

def main():
    # print(rekursive("22a3(abcd)Xy2Z"))

    matrix = [[4, 5, 19, 4], [5, 6, 1, 0], [6, 8, 3, 5]]
    print(matrix_sum(matrix))

    q1 = Square(3, 1, 3)
    q2 = Square(2, 0, 2)
    q3 = Square(4, 5, 3)
    q4 = Square(5, 2, 1)
    r1 = Rectangle(1, 2, 6, 4)
    print(contains([q1, q2, q4], r1))

main()
