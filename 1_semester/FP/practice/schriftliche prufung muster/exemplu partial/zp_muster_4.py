def create_matrix(n):
    matrix = []
    value = n
    for i in range(n):
        list = []
        for j in range(n):
            list.append(value)
            value -= 1
        matrix.append(list)
        value += n + 1
    return matrix


def print_elements(matrix):
    m = n = len(matrix)
    i = j = num = p = 0
    while num < n * n:
        while j < m - 1:
            print(matrix[i][j], end = ' ')
            j += 1
            num += 1
        while i < m - 1:
            print(matrix[i][j], end = ' ')
            i += 1
            num += 1
        while j > p:
            print(matrix[i][j], end = ' ')
            j -= 1
            num += 1
        while i > p:
            print(matrix[i][j], end = ' ')
            i -= 1
            num += 1
        m -= 1
        i += 1
        j += 1
        p += 1

class Ball:
    def __init__(self, Farbe, Radius):
        self._Farbe = Farbe
        self._Radius = Radius

    @property
    def eigenschaften(self):
        return self._Farbe, self._Radius

    @eigenschaften.setter
    def eigenschaften(self):
        if type(_Radius) != float:
            raise AttributeError('Attribut kann nicht nur float sein')
        
             

if __name__ == '__main__':
    ball = Ball('rot', 4.5)
    print(ball.eigenschaften)


from math import sqrt
from functools import reduce
def skalar(a, b):
    return sum([i*j for i,j in zip(a,b)])

def skalar2(a, b):
    return reduce(lambda x, y: x + y, list(map(lambda x, y: x * y, a, b)))

def modul(a):
    return sqrt(skalar(a, a))

def ausdruck(a, b):
    return skalar(a, b) // (modul(a) * modul(b))

def main():

    a = [1, 6, 3, 4]
    b = [2, 3, 1, 0]
    print(skalar(a, b))
    print(modul(a))
    print(modul(b))
    print(ausdruck(a, b))
main()

# n = int(input())
# print(create_matrix(n))

# matrix = [
# [12, 6, 3, 0],
# [13, 7, 4, 1],
# [14, 8, 5, 2],
# [15, 10, 11, 9]
# ]
# print_elements(matrix)

