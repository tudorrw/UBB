# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# a. Geben sei eine Textdatei mit dem Namen "zahlen.txt" an, auf eine mehreren Zeilen, Zahlen die durch
# ein Tab-Zeichen "\t" getrennt sind.
# schreiben Sie eine Funktion namens "ub1", die:
#   - einen Parameter namens "greatest" erhält
# 	- aus der angegebenen CSV-Datei "zahlen.txt" liest
# 	- wenn "greatest" der Wert "True" (bool) hat, für jede Zeile, behaltet man nur die größte Zahl (0.25p)
# 	- wenn "greatest" der Wert "False" (bool) hat, für jede Zeile, behaltet man nur die niedrigste Zahl (0.25p)
# 	- Das Ergebnis der Funktion ist die Multiplikation aller behaltenen Zahlen (0.5p)
# Es sind keine for- oder while-Schleifen erlaubt. Es wird erwartet, dass die Lösung map, filter oder reduce und
# andere mathematische Operationen, falls erforderlich, benützt sind. (2p)
from functools import reduce
def ub1(greatest):
    with open("zahlen.txt", 'r') as file:
        data = file.read().splitlines()

    lines = list(map(lambda x: x.split('\t'), data))
    lists_of_numbers = list(map(lambda lst: list(map(int, lst)), lines))

    max_or_min = list(map(max if greatest else min, lists_of_numbers))
    return reduce(lambda x, y: x * y, max_or_min)

print(ub1(True))


# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.

def test1(): assert ub1(True) == 175587750
def test2(): assert ub1(False) == 1
def tests():
    test1()  #ala corect
    test2()  #ala gresit
# tests()


# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "DecryptedText".
# Die Klasse sollte in der Lage sein, das Folgende zu tun:
# 	 - bei der Initialisierung wird die Instanzvariable "characters" auf einen gegebenen Parameter gesetzt.
# 	   Der Typ des Parameters ist eine Liste von Zeichen. (0.5p)
# 	 - eine Methode namens "encrypt" haben, die:
# 	 	- für alle Zeichen aus der Liste "characters" prüft, dass sich alle im Interval "a-z" sich befindet (0.5pp)
# 	 	- gibt eine Liste mit den konvertierten Zeichen in Zahlen zurück, die die ASCII-Tabelle verwenden (0.5p)
# 	 	- eine benutzerdefinierte Ausnahme namens "UnacceptedValue" wirft, wenn ein Zeichen nicht im Interval befindet (0.5p)
class UnacceptedValue(Exception):
    pass

class DecryptedText:
    def __init__(self, characters):
        self.characters = characters
    
    def encrypt(self):
        new_lst = list(filter(lambda x: x.isalpha() and x.islower(), self.characters))
        if new_lst != self.characters:
            raise UnacceptedValue('inappropriate characters')
        else:
            return list(map(ord, self.characters))


# b. Schreiben Sie die Definition für eine Klasse namens "ActualDecryptedText", die von "DecryptedText" erbt.
#  Die Klasse sollte folgendes können:
# 	- bei der Initialisierung setzt sie neben den Variablen von "CaesarDecryptedText" auch eine Instanzvariable namens
#  	  "numbers" auf None Wert (0.25p)
# 	- Überschreiben der Methode "decrypt", um Folgendes zu tun:
# 		- Wiederverwendung der Methode "decrypt" aus der Basisklasse (0.25)
# 		- Im Falle eines erfolgreichen Aufrufs wird das Ergebnis zurückgegeben und im Instanzvariable "numbers" gespeichert (0.25p)
# 		- im Falle einen fehlgeschlagenen Aufruf wird die Instanzvariable "numbers" auf leere Liste gesetzt (0.25p)
# 	- Das Ergebnis der Addition zwischen einer Instanz des Typs "ActualDecryptedText" und einer Zahl (instanz + 3) ist
#     eine neue Instanz des Typs "ActualDecryptedText", die die Elemente aus "numbers" um die Zahl (aus der Addition)
#     erhöht. (1p)
class ActualDecryptedText(DecryptedText):
    def __init__(self, characters):
        super().__init__(characters)
        self.numbers = None

    def decrypt(self):
        try:
            self.numbers = super().encrypt() #erbt die encrypt Methode  
            return self.numbers
        except UnacceptedValue:
            self.numbers = []
            return self.numbers
    
    def __add__(self, other):
        new_nums =  list(map(lambda x: x + other, self.numbers))
        new_decrypted_text = ActualDecryptedText(list(map(chr, new_nums)))
        new_decrypted_text.numbers = new_nums
        return new_decrypted_text 


text1 = ActualDecryptedText(['a', 'm', 'a', 'z', 'o', 'n'])
print(text1.decrypt())
print(text1.numbers)
new_decrypted_text = text1 + 3
new_nums = new_decrypted_text.decrypt()
print(new_decrypted_text.numbers) #ar trebui sa mearga da habar n am ce are

text2 = ActualDecryptedText(['a', 'M', '4', 'z', '0', 'n'])
# print(text2.encrypt()) 


# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)

# varianta iterativa
def my_func(lst):
    n = len(lst)
    total = 0
    for num in lst:
        total += num
    return total / n

print(my_func([1, 3, 5, 7, 8, 3]))


# varianta recursiva
def recursive(lst):  
    if not lst:
        raise ZeroDivisionError('can\'t divide by zero')  
    if len(lst) == 1:  
        return lst[0]  
    return (lst[-1] + (len(lst) - 1) * recursive(lst[:-1])) / len(lst)  

print(recursive([1, 3, 5, 7, 8, 3]))