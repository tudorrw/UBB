

# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# a. Geben sei eine Textdatei mit dem Namen "text.txt" an, die in jeder Zeile den Namen des Schülers, das Fach und
# die jeweilige Note enthält, getrennt durch zwei Leerzeichen ("  "), schreiben Sie eine Funktion namens "ub1", die:
# 	- aus der angegebenen CSV-Datei "text.txt" liest
# 	- nur die Zeilen behält, die die Note größer als 8 haben (0.5p)
# 	- die Summe der Noten, der behaltenen Zeilen, berechnet und das Ergebnis zurückgibt (0.5p)
# Es sind keine for- oder while-Schleifen erlaubt. Es wird erwartet, dass die Lösung map, filter oder reduce und
# andere mathematische Operationen, falls erforderlich, benützt sind. (2p)
from functools import reduce
def ub1():
    # aus der angegebenen CSV-Datei "text.txt" liest
    with open('text.txt') as file:
        data = file.read()
    # Die Inhalt der Datei wird  in zahlreiche Liste auf Zeilen getrennt
    lines = list(map(lambda x: x.split('  '), data.split('\n')))
    # die zeilen list wird erscheint, 
    zeilen = list(filter(lambda x: int(x[3])>8, lines))
    
    # die liste den Noten
    noten = list(map(lambda x: int(x[3]), zeilen))
    summe = reduce(lambda x, y: x + y, noten)
    return summe 

# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.
def richtig_test(): assert ub1() == 58
def falsch_test(): assert ub1() == 57

def tests():
    richtig_test()
    # falsch_test()
tests()


# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "DebitCard".
# Die Klasse sollte in der Lage sein, das Folgende zu tun:
# 	 - bei der Initialisierung die Instanzvariable "money" auf 0 und die Instanzvariable "name" auf einen
# 	   gegebenen Parameter setzen (0.5p)
# 	 - eine Methode namens "pay" haben, die:
# 	 	- einen einzelnen ganzzahligen Parameter bekommt
# 	 	- prüft, ob der Parameter größer oder gleich der Variablen "money" ist (0.5pp)
# 	 	- die Differenz zwischen dem "money" und dem gegebenen Parameter zurückgibt und als neue Wert für "money" speichert (0.5p)
# 	 	- eine benutzerdefinierte Ausnahme namens "NoBalance" wirft, wenn der Parameter größer als "money" ist (0.5p)

# die benutzerdefinierte Ausnahme "NoBalance"
class NoBalance(Exception):
    pass

class DebitCard:
    # die Konstruktor __init__ die die Instanzevariablen "money" und "name"
    def __init__(self, name):
        self.money = 0
        self.name = name
    # die methode "pay"
    def pay(self, value):
        if self.money < value: 
            raise NoBalance("Du hast kein Geld!")  # wenn der Parameter größer als "money" ist, wird die Ausnahme wirft
        else: 
            self.money -= value    #anderenfalls berechnen wir die Differnz zwischen dem "money" und dem gegebenen Parameter
        return self.money # das Ergebnis zuruckgibt



# b. Schreiben Sie die Definition für eine Klasse namens "CreditCard", die von "DebitCard" erbt.
#  Die Klasse sollte folgendes können:
# 	- bei der Initialisierung setzt sie neben den Variablen von "DebitCard" auch eine Instanzvariable namens
#  	  "debt" auf 0 (0.25p)
# 	- Überschreiben der Methode "pay", um Folgendes zu tun:
# 		- Wiederverwendung der Methode "pay" aus der Basisklasse (0.25)
# 		- im Falle einer erfolgreichen Abhebung wird das Ergebnis zurückgegeben (0.25p)
# 		- im Falle einer fehlgeschlagenen Abhebung wird ganzzahligen Parameter von der "pay" Methode zum "debt" addiert (0.25p)
# 	- Die Multiplikation zwischen ein "CreditCard" Instanz und eine Zahl (credit_card * 3) hat als Ergebnis
#     eine Liste von "CreditCard" instanzen die der gleiche Zustand der originalen Instanz haben. Die länge der Liste
#     ist gleich mit der Zahl aus der Multiplikation (1p)

# erbt die DebitCard Klasse
class CreditCard(DebitCard):

    def __init__(self, name):
        super().__init__(name)
        self.debt = 0
    
    def __str__(self):
        return f"CreditCard(name:{self.name}, money:{self.money}, debt:{self.debt})"

    def pay(self, value):
        try:
            result = super().pay(value)
            return result
        except NoBalance:
                self.debt += value

    def multiplikation(self):
        self.money *= 3


def main():
    cc1 = CreditCard('bob')
    cc1.money += 140
    print(cc1)

    cc1.pay(150)
    print(cc1)


main()


# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)
def my_func(lst, x):
    low = 0
    high = len(lst) - 1
    while low <= high:
        mid = (low + high) // 2
        if lst[mid] == x:
            return mid
        elif lst[mid] < x:
            low = mid + 1
        else:
            high = mid - 1
    return -1
# print(my_func([1, 3, 4, 5, 6, 7], 6))

def rekursiv(lst, x, low, high):
    if x not in lst:  #wenn es gibt kein Wert x in der Liste geben wir -1 zuruck
        return -1 
    if low >= high:   #Aufhoren Bedingung 
        return low      #geben wir die Position des Werts x in der gegebenen Liste zuruck
    else:
        mid = (low + high) // 2  #der Mitte berechnen 
        if lst[mid] < x:        #vergleichen wir x mit der Element mit der Index mid 
            return rekursiv(lst, x, mid + 1, high)  #wenn x grosser als der Elementmit  der Index mid, andern wir die parameter low mit mid + 1 
        else:
            return rekursiv(lst, x, low, mid)  #wenn x kleiner als der Element mit der Index mid, andern wir die parameter low mit mid

lst = [1, 3, 4, 5, 6, 7, 8]
print(rekursiv(lst, 6, 0, len(lst) - 1))
        

def h1(row, column):
    return (row + 5 * column) % 13

def h2(row, column):
    return 1 + (row + 7 * column) % 12

row = int(input("row:"))
column = int(input("column: "))

for i in range(6421):
    


