# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# a. Geben sei eine Textdatei mit dem Namen "zahlen.txt" an, auf eine mehreren Zeilen, Zahlen die durch
# die Zeichenkette "UBB" getrennt sind.
# schreiben Sie eine Funktion namens "ub1", die:
#   - einen Parameter namens "even_row" erhält
# 	- aus der angegebenen CSV-Datei "zahlen.txt" liest
# 	- wenn "even_row" der Wert "True" (bool) hat, behalten nur die Zeilen, wo alle Zahlen gerade Zahlen sind (0.25p)
# 	- wenn "even_row" der Wert "False" (bool) hat, behalten nur die Zeilen, wo alle Zahlen ungerade Zahlen sind (0.25p)
# 	- Das Ergebnis der Funktion ist die Summe der Zahlen der behaltenen Zeilen (0.5p)
# Es sind keine for- oder while-Schleifen erlaubt. Es wird erwartet, dass die Lösung map, filter oder reduce und
# andere mathematische Operationen, falls erforderlich, benützt sind. (2p)

from functools import reduce
def ub1(even_row):
    with open("zahlen.txt", "r") as file:
        data = file.read()   # citeste din fisier

    lines = data.split('\n') # da split la fiecare rand nou si creeaza o lista din randuri
    separate_numbers = list(map(lambda x: x.split("UBB"), lines)) #creeaza o lista de liste in care se afla doar numere
    int_numbers = list(map(lambda lst: list(map(int, lst)), separate_numbers)) #converteste toate numerele din string-uri in integeri
    
    # even_or_odd e o lista de liste doar cu numere pare/impare depinzand de valoarea lui even-row. verific daca o lista contine doar numere impare/pare
    # creand o alta lista in care verific fiecare numar si verifica daca aceasta lista creeata e egala cu cea originala 
    even_or_odd = list(filter(lambda lst: list(filter(lambda number: not(number % 2) if even_row else number % 2, lst)) == lst, int_numbers))
    
    try:
        numbers = reduce(lambda x, y: x + y, even_or_odd) # concateneaza toate listele din lista even_or_odd intr-o singura lista
        return reduce(lambda x, y: x + y, numbers) # suma numerelor pare/impare
    except TypeError: # in cazul in care nu exista nici o lista de numere pare/impare, in reduce-ul de la 'numbers' va aparea o eroare 
                      # pt ca nu are ce liste sa concateneze, ceea ce inseamna ca nu exista niciun numar si rezultatul va fi 0
        return 0 

print(ub1(False))
# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.
def test1(): assert ub1(False) == 98
def test2() :assert ub1(True) == 1

def tests():
    test1()
    test2()
# tests()
    

# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "Shop".
# Die Klasse sollte in der Lage sein, das Folgende zu tun:
# 	 - bei der Initialisierung wird die Instanzvariable "products" auf einen gegebenen Parameter gesetzt.
# 	   Der Typ des Parameters ist eine Liste von strings. (0.5p)
# 	 - eine Methode namens "buy" haben, die:
# 	 	- einen einzelnen ganzzahligen Parameter namens "price" bekommt
# 	 	- für alle Element aus der Liste "products" prüft, dass die doppelte Länge des Elements nicht größer als "price" ist (0.5pp)
# 	 	- gibt eine neue Liste mit strings zurück, die dem folgenden Format behalten: "<element> - <price>" (0.5p)
# 	 	- eine benutzerdefinierte Ausnahme namens "BadName" wirft, wenn die Prüfung der Lange nicht fördert(0.5p)
class BadName(Exception):
    pass

class Shop:
    def __init__(self, products):
        self.products = products
    

    def buy(self, price):
        new_lst = []
        for product in self.products:
            if 2* len(product) <= price:
                new_lst.append(f"{product} - {price}")
            else:
                raise BadName
        return new_lst
        

# b. Schreiben Sie die Definition für eine Klasse namens "BetterShop", die von "Shop" erbt.
#  Die Klasse sollte folgendes können:
# 	- bei der Initialisierung setzt sie neben den Variablen von "Shop" auch eine Instanzvariable namens
#  	  "total" auf 0 (0.25p)
# 	- Überschreiben der Methode "buy", um Folgendes zu tun:
# 		- Wiederverwendung der Methode "buy" aus der Basisklasse (0.25)
# 		- Im Falle eines erfolgreichen Aufrufs wird das Ergebnis zurückgegeben und die Instanzvariable "total" mit dem
# 	      Wert von "price" erhöht (0.25p)
# 		- im Falle einen fehlgeschlagenen Aufruf wird die Instanzvariable "total" auf -1 gesetzt (0.25p)
# 	- Das Ergebnis der Subtraktion zwischen zwei Instanzen des Typs "BetterShop" (shop1 - shop2) ist eine Liste
#     von strings die sich in der "products" der beiden Instanzen befinden.
#     (1p)
class BetterShop(Shop):
    def __init__(self, products):
        super().__init__(products)
        self.total = 0
    


    def buy(self, price):
        try:
            previous_result = super().buy(price)
            self.total += price
            return previous_result
        except BadName:
            self.total = -1
        
    def __sub__(self, other):
        return list(set(self.products) - set(other.products))

shop1 = BetterShop(["tee", "coffee", "bread", "sushi"])
shop2 = BetterShop(["juice", "yogurt", "tee", "pasta", "bread"])
print(shop1.buy(12))
print(shop2.buy(10))
print(shop2.total)
print(shop1 - shop2)





# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)
# functia test
other_func = lambda num: num > 45 and num < 60

# varianta iterativa
def my_func(some_list, other_function):
    my_list = []
    for num in some_list:
        if other_function(num):
            my_list.append(num)
    return my_list

print(my_func([3, 46, 54, 6, 78, 23, 55], other_func))

# varianta recursiva
def recursive(lst, other_function):
    if not lst:
        return []
    if other_function(lst[-1]):
        return recursive(lst[:-1], other_function) + [lst[-1]]  
    else: 
        return recursive(lst[:-1], other_function)

print(recursive([3, 46, 54, 6, 78, 23, 55], other_func))

