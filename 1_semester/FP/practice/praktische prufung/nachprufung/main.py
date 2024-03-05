# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# Geben Sie eine Textdatei mit dem Namen 'text.txt' an, die in jeder Zeile den Vor- und Nachnamen des Schülers,
# das Fach und die jeweilige Note enthält. Die Felder sollen durch drei Strichpunkt (';;;') voneinander getrennt sein.
# Schreiben Sie eine Funktion namens 'ub1', die folgendes tut:
#  - liest aus der angegebenen Datei 'text.txt'
#  - behält nur die Zeilen, bei denen der Nachname eine Länge von 3 hat und die Note eine gerade Zahl ist (0,5 Punkte)
#  - gibt einen String aller Fächer aus den behaltenen Zeilen zurück. Die Fächer sollen in Kleinbuchstaben und durch
#    Kommas getrennt sein. (0,5 Punkte)
# Die Verwendung von for- oder while-Schleifen, list comprehension ist nicht erlaubt. Es wird erwartet,
# dass die Lösung map, filter, reduce und andere mathematische Operationen, falls erforderlich, verwendet. (2 Punkte)

def ub1():
    with open("text.txt", "r") as file:
        data = file.read().splitlines()  
    
    lines = list(map(lambda x: x.split(';;;'), data))  #trennen wir die strings in lists
    selected_lines = list(filter(lambda x: len(x[1]) == 3 and int(x[3]) % 2 == 0, lines))  #wahlen wir die gewunschte listen aus
    fachen = list(map(lambda x: x[2].lower(), selected_lines))   #bilden wir eine liste mit Fachen, die Fachen sind in Kleinbucstaben geschrieben
    return ",".join(fachen)   #verketten wir die Elemente aus der fachen list in einem String und geben wir das Ergebnis zuruck

print(ub1())



# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.
def test_richtig(): 
    assert ub1() == "advanced programming,programming fundamentals"
def test_falsch():
    assert ub1() == "programming fundamentals"

test_richtig()
# test_falsch()


# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "Student". Die Klasse sollte in der Lage sein,
# Folgendes zu tun:
# - Bei der Initialisierung wird die Instanzvariable "grades" auf einen gegebenen Parameter gesetzt.
# - Der Typ des Parameters ist eine Liste von Zahlen. (0,5 Punkte)
# - Eine Methode namens "take_exam" haben, die:
#   - Einen einzelnen String-Parameter namens "exam" bekommt
#   - Für alle Elemente aus der Liste "grades" prüft, ob alle größer oder gleich 5 sind (0,5 Punkte)
#   - Eine neue Liste zurückgibt, die nur ein Element enthält, und zwar ein Tuple, gebildet aus dem Parameter "exam"
#     und dem ersten Element der Liste "grades" (0,5 Punkte)
#   - Eine benutzerdefinierte Ausnahme namens "FailedExam" wirft, wenn kein Element größer oder gleich 5 ist(0,5 Punkte)


class FailedExam(Exception):   #erzeugen wird die Exeption Class(benutzerdefinierte Ausnahme)
    pass

class Student:
    def __init__(self, grades):
        self.grades = grades

    def take_exam(self, exam):
        verify_grades = list(filter(lambda grade: grade >= 5, self.grades))
        if not verify_grades:   #wenn dise Liste leer ist, dann wefen wir die Ausnahme
            raise FailedExam   

        return [exam, self.grades[0]]   
    
    

# b. Schreiben Sie die Definition für eine Klasse namens "ComputerScienceStudent", die von "Student" erbt.
# Die Klasse sollte Folgendes können:
# - Bei der Initialisierung setzt sie neben den Variablen von "Book" auch eine Instanzvariable namens "laptop" auf ein
#   leeres Wörterbuch (dict). (0,25 Punkte)
# - Überschreiben der Methode "take_exam", um Folgendes zu tun:
#   - Wiederverwendung der Methode "take_exam" aus der Basisklasse (0,25 Punkte)
#   - Im Falle eines erfolgreichen Aufrufs wird das Ergebnis in der Instanzvariable "laptop" gespeichert, wobei der
#     Schlüssel und der Wert das erste beziehungsweise zweite Element des Tupels sind (0,25 Punkte)
#   - Im Falle eines fehlgeschlagenen Aufrufs wird in der Instanzvariable "laptop" ein Eintrag hinzugefügt, wobei der
#     Schlüssel der Parameter "exam" ist und der Wert 0 ist (0,25 Punkte)
# - Das Ergebnis der Addition zwischen zwei Instanzen des Typs "ComputerScienceStudent" (stud1 + stud2) ist ein
#   Wörterbuch, das alle Einträge der beiden "laptop"-Instanzvariablen enthält.
#   Die Priorität der Einträge ist nicht wichtig. (1 Punkt)

class ComputerScienceStudent(Student):
    def __init__(self, grades):
        super().__init__(grades)
        self.laptop = {}

    def take_exam(self, exam):
        try:
            result = super().take_exam(exam)
            self.laptop[result[0]] = result[1]
        except FailedExam:
            self.laptop[exam] = 0 

    def __add__(self, other):
        return 

ana = ComputerScienceStudent([6, 7, 9, 7, 8])
ana.take_exam("fp")
print(ana.laptop)

ema = ComputerScienceStudent([4, 3, 4, 2])
ema.take_exam("asc")
print(ema.laptop)
  
        


# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)
def my_func(n):
    lst = []
    total = 0
    for i in range(-n, n):
        if i % 10 == 0:
            total += i
        lst.append(total)
    return lst

def recursive(n):
    if not n:
        return []
    if not n % 10:
        pass
    return [-n] + recursive(n - 1) + [n]



print(my_func(20))
print(recursive(20))