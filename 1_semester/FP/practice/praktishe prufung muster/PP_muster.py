from functools import reduce

def third(lines):
    for i, line in enumerate(lines):
        if not (i+1) % 3 and not line.startswith('.'):
            return False
    return True   

def primzahl(n):
    if n < 2:
        return False
    for i in range(2, n // 2):
        if not n % i:
            return False
    return True


def ub1():
    with open('PP_muster.txt', 'r') as file:
        lines = file.readlines()
    
    # 2
    lines1 = list(map(lambda x: x.split(' '), lines))
    strings = reduce(lambda x, y: x + y, lines1)
    numbers = list(map(int, list(filter(lambda x: x.isdigit(), strings))))
    summe = reduce(lambda x, y: x + y, numbers)
    
    # worter = list(filter(lambda x: x.isalpha(), ))
    try:
        assert third(lines) == True
        assert primzahl(summe) == True
        print('OK')
    except AssertionError:
        print("FEHLER") 


class Gerat:
    def __init__(self, preis, hersteller, effizienzklasse):
        self.preis = preis
        self.hersteller = hersteller
        self.effizienzklasse = effizienzklasse
    
    def __eq__(self, other):
        return self.preis == other.preis

    def __str__(self):
        return f"Gerat: preis={self.preis}, hersteller={self.hersteller}, effizienzklasse={self.effizienzklasse}"

class Kuhlschrank(Gerat):
    def __init__(self, preis, hersteller, effizienzklasse, kapazitat):
        super().__init__(preis, hersteller, effizienzklasse)
        self.kapazitat = kapazitat

    def __str__(self):
        return super().__str__() + f", kapazitat={self.kapazitat}"

class Kuche:
    def __init__(self):
        self.lst = []

 
def main():
    ub1()
    k1 = Kuhlschrank(200, 'Samsung', 'i3', 80)
    print(k1)
    
    lst = ['ana', 'are', '4', 'catei', 'si', '6', 'pisici']

main()    