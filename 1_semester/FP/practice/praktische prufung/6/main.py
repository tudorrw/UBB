# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# a. Geben sei eine Textdatei mit dem Namen "text.txt" an, die in jeder Zeile den Vorname und Nachname des Schülers, das Fach und
# die jeweilige Note enthält, getrennt durch zwei Fragezeichen ("??"), schreiben Sie eine Funktion namens "ub1", die:
# 	- aus der angegebenen CSV-Datei "zahlen.txt" liest
# 	- nur die Zeilen behalten, die die Länge der Nachname gleich dem Wert der Note haben (0.5p)
# 	- Das Ergebnis der Funktion ist die Summe der Indizes der behaltenen Zeilen (0.5p)
# Es sind keine for- oder while-Schleifen erlaubt. Es wird erwartet, dass die Lösung map, filter oder reduce und
# andere mathematische Operationen, falls erforderlich, benützt sind. (2p)
from functools import reduce

def ub1():
    with open("text.txt", "r") as file:
        data = file.read()

    string_lines = data.split("\n") 
    lines = list(map(lambda x: x.split("??"), string_lines))
    zeilen = list(filter(lambda x: len(x[1]) == int(x[3]), lines)) #alege doar listele in care se respecta conditia: nur die Zeilen behalten, die die Länge der Nachname gleich dem Wert der Note haben
    
    return reduce(lambda x, y: x + y, list(map(lambda x: string_lines.index("??".join(x)), zeilen))) #aflam index ul fiecarui element din lista zeilen si adunam valorile indecsilor

print(ub1())

# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.
def test1():
    assert ub1() == 7

def test2():
    assert ub1() == 6

def tests():
    test1()
    test2()
    
# tests()


# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "Book".
# Die Klasse sollte in der Lage sein, das Folgende zu tun:
# 	 - bei der Initialisierung wird die Instanzvariable "words" auf einen gegebenen Parameter gesetzt.
# 	   Der Typ des Parameters ist eine Liste von strings. (0.5p)
# 	 - eine Methode namens "find" haben, die:
# 	 	- einen einzelnen string Parameter namens "word" bekommt
# 	 	- für alle Element aus der Liste "words" prüft, ob "word" sich im Element befindet (0.5pp)
# 	 	- gibt eine neue Liste zurück, die die Indizes der Elemente enthält, die "word" enthalten (0.5p)
# 	 	- eine benutzerdefinierte Ausnahme namens "TryAgain" wirft, wenn kein Element "word" enthält (0.5p)
class TryAgain(Exception):
    pass

class Book:
    def __init__(self, words):
        self.words = words

    def find(self, word):
        lst = list(filter(lambda elem: word in elem, self.words)) #alege elementele din lista self.words in care apare acel parametru word
        indizes =  list(map(lambda elem: self.words.index(elem), lst)) #creeaza o lista cu indicii respectivi ai elementelor din self.words in care apare word 
        if indizes: 
            return indizes
        raise TryAgain


# b. Schreiben Sie die Definition für eine Klasse namens "EBook", die von "Book" erbt.
#  Die Klasse sollte folgendes können:
# 	- bei der Initialisierung setzt sie neben den Variablen von "Book" auch eine Instanzvariable namens
#  	  "battery" auf 100 (0.25p)
# 	- Überschreiben der Methode "find", um Folgendes zu tun:
# 		- Wiederverwendung der Methode "find" aus der Basisklasse (0.25)
# 		- Im Falle eines erfolgreichen Aufrufs wird der Wert von "battery" um die Länge des Ergebnisses subtrahiert. (0.25p)
# 		- im Falle einen fehlgeschlagenen Aufruf wird die Instanzvariable "battery" auf 101 gesetzt (0.25p)
# 	- Das Ergebnis der Multiplikation zwischen zwei Instanzen des Typs "BetterShop" (shop1 * shop2) ist die Konkatenation
#     der strings die sich nicht in "words" Instanzvariablen der beiden Listen befinden. Die strings werden durch ">"
#     Zeichen konkateniert
#     (1p)

class EBook(Book):
    def __init__(self, words):
        super().__init__(words)
        self.battery = 100
    
    def find(self, word):
        try:
            previous_result = super().find(word)
            self.battery -= len(previous_result)
        except TryAgain:
            self.battery = 101

    def __mul__(self, other):
        lst = []
        for word in self.words + other.words:
            if word not in lst:
                lst.append(word)

        return '>'.join(lst)


ebook1 = EBook(['ananas', 'ciment', 'stabilopod', 'ana', 'asteriod', 'banana'])
ebook2 = EBook(["ana", "cocos", "pahar", "ciment", "ananas"])
ebook1.find('ana')
print(ebook1.battery)
ebook1.find('drac')
print(ebook1.battery)

print(ebook1 * ebook2)
# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)

# 2 functii de proba
def cmmdc(a, b):
    if not b:
        return a
    return cmmdc(b, a % b)

suma = lambda x, y: x + y


#varianta iterativa
def my_func(lst, other_function, start_value=None):
    if start_value is None:
        start_value = lst[0]
        lst = lst[1:]
    for num in lst:
        start_value = other_function(start_value, num)

    return start_value


print(my_func([18, 36, 72, 12], cmmdc, 60))
print(my_func([18, 36, 72, 12], suma))

def recursive(lst, other_function, start_value = None):
    if not lst and start_value:   
        return start_value 

    if not lst:
        return 0

    return other_function(recursive(lst[:-1], other_function, start_value), lst[-1])


print(recursive([18, 36, 72, 12], cmmdc, 60))
print(recursive([18, 36, 72, 12], suma))

