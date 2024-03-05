# 1
from math import sqrt
from functools import reduce
def konsonanten(wort):
    kon = list(filter(lambda x: x not in 'aeiouAEUIOU', list(map(str, wort))))
    return len(kon)

def primzahl(n):
    if n < 2:
        return False
    for i in range(2, n // 2):
        if not n % i:
            return False
    return True
# 2
def ggT(a, b):
    while(b):
        if a > b:
            a -= b
        else:
            b -= a
    return a



def ub1():
    with open("praktikum_713_2.txt", "r") as file:
        data = file.read()
    # 1
    lines = list(map(lambda x: x.split(','), data.split('\n')))
    zeichenfolge = reduce(lambda x, y: x + y, lines)

    worter = list(filter(lambda x: x.isalpha(), zeichenfolge))
    konsonanten_langen = list(map(lambda x: konsonanten(x), worter))
    k_anzahl = reduce(lambda x, y: x + y, konsonanten_langen)

    zahlen =  list(map(int, list(filter(lambda x: x.isdigit(), zeichenfolge))))
    g = reduce(lambda x, y: ggT(x, y), zahlen)
    max_lange = max(map(len, lines))

    try:
        assert primzahl(k_anzahl) == True
        assert (g > max_lange) == True
        print('OK')
    except AssertionError:
        print('FEHLER')

def main():
    ub1()
main()


class Auto:
    def __init__(self, marke, modell, km_anzahl, geld_anzahl = 0):
        self._marke = marke
        self._modell = modell
        self._km_anzahl = km_anzahl
        self._geld_anzahl = geld_anzahl
        self._bliste = []
    def __str__(self):
        return f"{self._marke}, {self._modell}, {self._km_anzahl}km, {self._geld_anzahl} EUR"

    def get_marke(self):
        return self._marke
    
    def set_marke(self, x):
        self._marke = x
    
    def get_modell(self):
        return self._modell

    def set_modell(self, x):
        self._modell = x
    
    def get_km_anzahl(self):
        return self._km_anzahl

    def set_km_anzahl(self, x):
        self._km_anzahl = x
    
    def get_geld_anzahl(self):
        return self._geld_anzahl

    def set_geld_anzahl(self, x):
        self._geld_anzahl = x

    def get_order_list(self):
        return self._bliste

    def set_order_list(self, x):
        self._bliste = x


class Kunde:
    def __init__(self, name, vorname, konto_geld):
        self._name = name
        self._vorname = vorname
        self._konto_geld = konto_geld
        self._bliste = []
    

    def __str__(self):
        return f'{self._name} {self._vorname} {self._konto_geld} EUR'

    def get_name(self):
        return self._name

    def set_name(self, x):
        self._name = x

    def get_vormane(self):
        return self._vorname

    def set_vorname(self, x):
        self._vorname = x

    def get_konto_geld(self):
        return self._konto_geld
    
    def set_konto_geld(self, x):
        self._konto_geld = x

    def get_order_list(self):
        return self._bliste
    
    def set_order_list(self, x):
        self._bliste = x


    def bestellen(self, auto):
        self._bliste.append(auto)    


# def main():
#     a1 = Auto('Audi', 'a5', 120)
#     a1.set_geld_anzahl(2 * a1.get_km_anzahl())
#     a1.get_geld_anzahl()
# main()

