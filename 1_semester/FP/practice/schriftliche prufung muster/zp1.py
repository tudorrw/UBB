from functools import reduce
def transfer():
    with open('input.txt', 'r') as file:
        data = file.read()
    
    numbers = reduce(lambda x, y: x + y, list(map(lambda lines: lines.split(','), data.split('\n'))))
    palindroms = list(filter(lambda x: x[2:len(x):3] == x[2:len(x):3][::-1] and x[2:len(x):3] != '', numbers))

    with open('output.txt', 'w') as file:
        file.write('\n'.join(palindroms))

# transfer()

def do_stuff(s1):
    arr = [0]*len(s1)
    print(arr)
    for i in range (len(s1)):
        w = s1[i]
        while w >= 2 and s1[i]%w:
            w -= 1
        arr[i] = w
    for i in range (len(s1)):
        arr[i] = arr[i] == 1
    return arr

print(do_stuff([1, 5, 7, 1, 4]))


def rekursive(n):
    if n == 0:
        return 0
    return (-1) ** (n + 1) * n * (n + 1) + rekursive(n - 1)

print(rekursive(3)) 
# 2 - 6 + 12 = 8

def do_stuff(s1 : str, s2 : str):
    arr = [0]*256
    if len(s1) != len(s2):
        return False
    for i in range (len(s1)):
        arr[s1[i]] += 1
        arr[s2[i]] -= 1
    for i in arr:
        if i: return False
    return True

# print(do_stuff('abc', 'acd'))
# Die Funktion do_stuff nimmt 2 String Variablen als Parameter und kehrt einen 
# boolean Wert zuruck. Erstens erzeugt eine leere Liste arr. Dann uberpruft wenn die
# Lange der String Parameters sind gleich und kehrt False zuruck falls die Bedingung wahr ist.
# Die Zeil des Algorithmus ist zu uberprufen wenn die Parameters dieselbe anzahl von gleiche Charakteren enthalten

# In der ersten for Schleife struzt das Programm ab, weil der Index der liste arr Strings sind
# Das Program wirft ein TypeError Ausnahme.

from functools import reduce
def skalarprodukt(a, b):
    return reduce(lambda x, y: x + y, [i * j for i, j in zip(a, b)])

from math import sqrt
def f(a, b):
    return skalarprodukt(a, b) / (sqrt(skalarprodukt(a, a)) * sqrt(skalarprodukt(b, b)))

print(skalarprodukt([1, 3, 5], [2, 4, 6]))
print(f([1, 3, 5], [2, 4, 6]))

# Die binare Suche halbiert des Suchraums in jedem Schritt, indem der gesuchte Wert mit
# dem Wert auf der Mittelposition des geordneten Feldes vergleichen wird:
# gesuchter Wert ist kleiner: wiederarbeiten mit linkem Teilfeld
# gesuchter Wert is grosser: wiederarbeiten mit rechtem Teilfeld
# Auf Teile und Herrsche basiest 


def mydecorator(function):
    def wrapper(*args):
        print('I am decorating this function')
        ret =  function(*args)

    return wrapper

@mydecorator
def hello_world(person):
    return f'hello {person}'

print(hello_world('tudor'))


def insertion_sort(lst):
    for i in range(len(lst)):
        j = i
        while j > 0 and lst[j - 1] > lst[j]:
            lst[j], lst[j - 1] = lst[j - 1], lst[j]
            j -= 1
        
    return lst

def bubble_sort(lst):
    sorted = False
    while not sorted:
        sorted = True
        for i in range(len(lst) - 1):
            if lst[i] > lst[i + 1]:
                lst[i], lst[i + 1] = lst[i+ 1], lst[i]
                sorted = False
    return lst



print(insertion_sort([4, 6, 1, 2, 4, 9, 0]))
print(bubble_sort([4, 6, 1, 2, 4, 9, 0]))

