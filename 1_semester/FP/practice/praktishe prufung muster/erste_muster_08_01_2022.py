# Benotung:
# 1.a 3 Punkte
# 1.b 1 Punkt

# 2.a 2 Punkte
# 2.b 2 Punkte

# 3. 1 Punkt
# Insgesamt: 9 Punkte

# 1.
# a. Geben sei eine Textdatei mit dem Namen "text.txt" an, die in jeder Zeile den Namen des Schülers, das Fach und
# die jeweilige Note enthält, getrennt durch ein Komma ",", schreiben Sie eine Funktion namens "ub1", die:
# 	- einen Parameter namens "subject" erhält
# 	- aus der angegebenen CSV-Datei "text.txt" liest
# 	- nur die Zeilen behält, deren Fach mit dem Parameter "subject" übereinstimmt (0.5p)
# 	- den Durchschnitt aller Noten berechnet und das Ergebnis zurückgibt (0.5p)
# Es sind keine for- oder while-Schleifen erlaubt. Es wird erwartet, dass die Lösung map, filter oder reduce und
# andere mathematische Operationen, falls erforderlich, benützt sind. (2p)
from functools import reduce


def ub1(subject):
    with open("text.txt", 'r') as file:
        data = file.read()
    lines = list(map(lambda x: x.split(','), data.split('\n')))
    
    filter_subject = list(filter(lambda x: x[2] == subject, lines))
    noten = list(map(lambda x: int(x[3]), filter_subject))
    durchschnitt = reduce(lambda x, y: x + y, noten) // len(noten)
    return durchschnitt

# b. Schreiben Sie für die Funktion "ub1" zwei Testfälle. (1p)
# Einer, der das erwartete Ergebnis der Funktion bestätigt und ein anderer, der absichtlich fehlschlägt.

def test1_ub1(): assert ub1('Advanced programming') == 8
def test2_ub1(): assert ub1('Advanced programming') == 9

def run_tests():
    test1_ub1()
    test2_ub1()

run_tests()

# 2.
# a. Schreiben Sie die Definition für eine Klasse namens "BankAccount".
# Die Klasse sollte in der Lage sein, das Folgende zu tun:
# 	 - bei der Initialisierung die Instanzvariable "amount" auf 0 und die Instanzvariable "owner" auf einen
# 	   gegebenen Parameter setzt (0.5p)
# 	 - eine Methode namens "withdraw" haben, die:
# 	 	- einen einzelnen ganzzahligen Parameter bekommt
# 	 	- prüft, ob der Parameter größer oder gleich der Variablen "amount" ist (0.5pp)
# 	 	- die Differenz zwischen dem "amount" und dem gegebenen Parameter zurückgibt (0.5p)
# 	 	- eine benutzerdefinierte Ausnahme namens "NoMoney" wirft, wenn der Parameter größer als "amount" ist (0.5p)

class NoMoney(Exception):
    pass

class BankAccount:
    def __init__(self, owner):
        self.amount = 0
        self.owner = owner
        

    def withdraw(self, withdraw_value):
        if self.amount <= withdraw_value:
            raise NoMoney('Du hast kein Geld:(')
        else:
            self.amount -= withdraw_value
        return self.amount



# b. Schreiben Sie die Definition für eine Klasse namens "CreditBankAccount", die von "BankAccount" erbt.
#  Die Klasse sollte folgendes können:
# 	- bei der Initialisierung setzt sie neben den Variablen von "BankAccount" auch eine Instanzvariable namens
#  	  "credit_score" auf 1 (0.25p)
# 	- Überschreiben der Methode "withdraw", um Folgendes zu tun:
# 		- Wiederverwendung der Methode "withdraw" aus der Basisklasse (0.25)
# 		- im Falle einer erfolgreichen Abhebung* wird das Ergebnis zurückgegeben und der "credit_score" um 1 erhöht (0.25p)
# 		- im Falle einer fehlgeschlagenen Abhebung* wird "credit_score" um 1 verringert. (0.25p)
#         *Abhebung = erfolgreiche Aufruf zum "withdraw" Methode der Basisklasse
# 	- Die Summe von zwei Instanzen von "CreditBankAccount" (account_a + account_b) führt zu einer neuen Instanz
# 	  die den Namen des Eigentümers des ersten Kontos sowie "account" und "credit_score" als Summen der jeweiligen
# 	  Variablen der beiden Instanzen (1p)

class CreditBankAccount(BankAccount):
    def __init__(self, owner):
        super().__init__(owner)
        self.credit_score = 1

    def withdraw(self, withdraw_value):
        try:
            result = super().withdraw(withdraw_value)
            self.credit_score += 1
            return result
        except NoMoney as e:
            self.credit_score -= 1
            raise e

    def __add__(self, other):
        if isinstance(other, CreditBankAccount) and self.owner == other.owner:
            new_account = CreditBankAccount(self.owner)
            sum_amount = self.amount + other.amount
            sum_credit_score = self.credit_score + other.credit_score
            return new_account, sum_amount, sum_credit_score
        else:
            raise ValueError
    def __str__(self):
        return f'CreditBankAccount(owner = {self.owner}, amount = {self.amount}, credit_score = {self.credit_score})'

def main():
    account_a = CreditBankAccount('bob')
    account_a.amount += 120
    account_b = CreditBankAccount('bob')
    account_b.amount += 140
    print(account_a.__add__(account_b))
    account_a.withdraw(170)
    print(account_a.credit_score)
main()

# 3. Schreibe die folgende Funktion so um, dass sie rekursiv ist: (1p)
def my_func(n):
    if n in (0, 1):
        return n

    a, b = 0, 1
    for i in range(2, n + 1):
        a, b = b, a + b
    return b



def fibo(n):
    if n in (0, 1):
        return n
    return fibo(n-1) + fibo(n-2)

# print(fibo(3))



