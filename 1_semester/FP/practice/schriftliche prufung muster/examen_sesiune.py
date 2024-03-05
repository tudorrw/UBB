def binare_suche(lst, st, dr, elem):
    try:
        if st > dr:
            raise Exception

        mij = (st + dr) // 2
        if lst[mij] == elem:
            return True
        if lst[mij] > elem:
            return binare_suche(lst, st, mij - 1, elem)
        return binare_suche(lst, mij + 1, dr, elem)    
    except Exception:
        return False

def umgekehrter_reihe(lst):
    if not lst:
        return []
    if len(lst) == 1:
        return [[lst[0]]]
    return [[lst[-1]]] + umgekehrter_reihe(lst[:-1])

# Aufgabe 4
def decorator(function):
    def wrapper(*args, **kwargs):
        pc = function(*args, **kwargs)
        if pc <= 25:
            pc += 10
        return pc
    return wrapper

class Calculator:
    def __init__(self, name, preis):
        self.name = name
        self.preis = preis
    
    @decorator
    def add_tax(self, tax):
        return self.preis + tax

if __name__ == "__main__":
    a = Calculator("HC90", 300)
    pc = a.add_tax(10)
    print(pc)

    print(umgekehrter_reihe([1, 2, 3]))
    print(binare_suche([1, 4, 6, 7, 9, 11, 16, 19], 0, 7, 0))

