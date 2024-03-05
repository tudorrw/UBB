# Übung 1
# In die Datei 'text.txt' finden Sie, Zeichenketten die durch ein Leerzeichen getrennt sind.
# Die Zeilen sind durch '\n'-Zeichen getrennt.
# Schreiben Sie eine die Implementierung einer Funktion namens 'ub1'. (0.25p)
# Diese Funktionen erlaubt die folgenden Parameter zu bekommen: (0.25p)
#   - 'add_string' (boolsche Wert)
#   - 'add_number' (boolsche Wert)
# Die Funktion soll die Worte aus der Datei lesen (0.75p) und, anhand der folgenden Regeln, ein Wörterbuch (dictionary) bauen:
# - falls 'add_string' den Wert 'True' hat, muss das Wörterbuch eine Schlüssel 'strings' enthalten. (0.75p)
#   Der Wert des Schlüssels wird eine Liste aller Zeichenketten die keine Zahl oder Symbol* enthalten
# - falls 'add_number' den Wert 'True' hat, muss das Wörterbuch eine Schlüssel 'numbers' enthalten. (0.75p)
#   Der Wert des Schlüssels wird eine Liste aller Zeichenketten, die eine Zahl sind.
# - Das Wörterbuch muss eine Schlüssel 'symbols' enthalten. (0.75p)
#   Der Wert des Schlüssels wird eine Liste aller Zeichenketten, die entweder ein Symbol sind oder ein Symbol beinhalten
# For/While-Schleife/List Comprehension und Rekursion sind nicht erlaubt! (0.5p)
# * Symbolen sind die folgenden Zeichen berücksichtigt: !@#$%^.,
from functools import reduce
def ub1(add_string, add_number):
    with open("text2.txt", 'r') as file:
        data = file.read()
    
    lines = list(map(lambda x: x.split(' '), data.split('\n')))
    text_list = reduce(lambda x, y: x + y, lines)

    worterbuch = {}
    if add_string:
        worterbuch['strings'] = list(filter(lambda x: x.isalpha(), text_list))
    if add_number:
        worterbuch['numbers'] = list(filter(lambda x: x.isdigit(), text_list))
    worterbuch['symbols'] = list(filter(lambda x: list(filter(lambda y: y in '!@#$%^.,', list(map(str, x)))), text_list))
    return worterbuch

print(ub1(True, True))

# Übung 2
# Definieren Sie eine Klasse namens 'Cartridge', die einen Tintenstand (Ganzzahl/integer zwischen 0 und 100) und eine Farbe (String) hat. Die zwei Werte werden bei Instanzierung gegeben.
# Definieren Sie eine Klasse namens 'Printer', die eine Liste von Objekten vom Typ 'Cartridge' enthält.
# Die Liste der Patronen(Cartridge) wird als "cartridges" bezeichnet.
# Wenn die Klasse instanziiert wird, ist das Inhaltsverzeichnis immer leer.
# Dieser Drucker kann nur 3 Patronen gleichzeitig haben.
# Jede der Patronen hat eine der folgenden Farben: Rot, Grün, Blau.
# Definieren Sie eine Methode zum Hinzufügen einer einzelnen Cartridge und nennen Sie sie 'add_cartridge'.
# Stellen Sie sicher, dass diese Methode überprüft, ob die Patronenfarbe bereits vorhanden und nicht leer ist. Wenn es leer ist, ersetzen Sie die Cartrdige durch die als Parameter angegebene.
# Definieren Sie eine Methode zum Entfernen einer einzelnen Patrone und nennen Sie sie 'remove_cartrdige'. Diese Methode benötigt einen einzelnen String-Parameter namens 'color'. Diese Methode sollte einen 'RuntimeError' mit der Meldung 'No cartridge found' werfen, wenn eine Patrone mit der angegebenen Farbe nicht gefunden werden konnte.
# Definieren Sie eine Methode namens 'print', die eine Liste von Strings in eine Datei schreibt. Jeder String sollte in eine neue Zeile geschrieben werden. Die Methode erhält zwei String-Parameter, die den Pfad zur Datei bzw. die Liste der Strings darstellen, die in die Datei geschrieben werden müssen.

# Definieren Sie eine neue Klasse namens 'SmarterPrinter', die 'Printer' erbt.
# Erweitern Sie die 'print'-Methode, indem Sie die ursprüngliche Implementierung wiederverwenden, und lassen Sie sie auch auf dem Bildschirm die Meldung 'Printed a document of length <n> to <path>' anzeigen, wobei <n> und <path> Platzhalter sind für die Länge aller in die Datei geschriebenen Zeichen bzw. den Speicherort der Datei.

# Stellen Sie sicher, dass die Klasse 'Cartridge' die notwendigen Methoden enthält, damit der folgende Code keine Fehler wirft.
class Cartridge:
    def __init__(self, tintenstand, farbe):
        self.tintenstand = tintenstand
        self.farbe = farbe

    def __add__(self, other):
        return Cartridge(self.tintenstand + other.tintenstand, self.farbe + other.farbe)
class Printer:
    def __init__(self):
        self.cartridges = []
    

    def add_cartridge(self, cartridge):
        for c in self.cartridges:
            if c.farbe == cartridge.farbe and c.tintenstand > 0:
                return 
        for i, c in enumerate(self.cartridges):
            if c.farbe == cartridge.farbe and not c.tintenstand:
                self.cartridges[i] = cartridge
                return
        if len(self.cartridges) < 3:
            self.cartridges.append(cartridge)
    
    def remove_cartridge(self, color):
        self.cartridges = list(filter(lambda x: x.farbe == color, self.cartridges))
        if not self.cartridges:
            raise RuntimeError('No cartridge found!')


    def strings(self):
        return list(map(lambda x: f"Patrone(tintenstand = {x.tintenstand}, farbe = {x.farbe})", self.cartridges))
    
    def print(self, filename, string_list):
        with open(filename, 'w') as file:
            file.write('\n'.join(string_list))



class SmarterPrinter(Printer):
    def print(self, filename, string_list):
        super().print(filename, string_list)
        total_length = sum(len(s) for s in string_list)
        print(f'Printed a document of length {total_length} to {filename}')   
def check():
    cartridge1 = Cartridge(30, 'rot')
    cartridge2 = Cartridge(70, 'blau')
    printer = SmarterPrinter()

    printer.add_cartridge(cartridge1)
    printer.add_cartridge(cartridge2)
    string_list = printer.strings()
    printer.print('printer.txt', string_list)

    cartridge3 = cartridge1 + cartridge2

#     assert isinstance(basket3, Basket)
#     assert cartridge3 is not cartridge1
    assert cartridge3.farbe == 'grun'
    assert cartridge3.tintenstand == 100
# check()