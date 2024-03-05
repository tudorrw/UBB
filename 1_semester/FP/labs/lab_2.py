#1
def wiederholen(l):
    #ein temp list aufbauen
    lfin = []
    for i in l:
        if i not in lfin:
            lfin.append(i)
    print(lfin)

#2
def symmetrische_Paaren(l):
    temp = l
    symmetrisch = 0
    for i in range(len(temp) - 1):
        for j in range(i + 1, len(temp)):
            #wenn man symmetrische Paaren darin findet wird, indenziert man mit 0 und die Variable wachst mit 1
            if l[i] % 10 * 10 + l[i] // 10 == l[j] and l[i] and l[j]:
               l[i], l[j] = 0, 0
               symmetrisch += 1
    print(symmetrisch)

#3
#bubblesort
def bubble(l):
    sorted = False
    while not sorted:
        sorted = True
        for i in range(len(l) - 1):
            if(l[i] <= l[i + 1]):
                sorted = False
                l[i], l[i + 1] = l[i + 1], l[i]

def konkatenation(l):
    #l.sort(reverse = True)
    bubble(l) 
    num = ''
    print(l)
    for i in l:
        num += str(i)
    print(num) 

#4
#wandelt die Zahl von den basis 2 um, dann berechnet die operation xor zwischen die Zahl und den ersten Element und speichert die
# Ergebnis in dieser Zahl
def base_2(decimal_number):
    #eine Liste aufbauen, die aufeinanderfolgende Reste 
    #der Division durch 2 der jeweiligen Zahl enthalt 
    remainder_stack = []
    while decimal_number: 
        remainder = decimal_number % 2
        remainder_stack.append(remainder) 
        decimal_number //= 2
    #man invertiere die Liste, um die Zahl in der richtigen Reihenfolge darzustellen 
    remainder_stack.reverse() 
    #man verwandelt die Liste in einen Zahl(integer)
    binary_digits = ''
    for b in remainder_stack:
         binary_digits += str(b)
    return int(binary_digits)

def bitwise_xor(a, b):
    m, n = base_2(a), base_2(b)
    final_number, aux = 0, 1 
    while n or m:
        if n % 10 and not m % 10 or not n % 10 and m % 10:
            final_number += aux
        aux *= 10 
        n //= 10
        m //= 10
    return final_number

def base_10(binary_number):
    base_10_number, pw = 0, 1
    while binary_number:
        if binary_number % 10:
            base_10_number += pw
        pw *= 2
        binary_number //= 10
    return base_10_number 

def encrypt(l):
    key = str(input("Eingeben Sie ein Key(+, *, ^): "))
    match key:
        case '*':
            for i in range(1, len(l)):
                l[i] *= l[0]
        case '+':
            for i in range(1, len(l)):
                l[i] += l[0]
        case '^':
            for i in range(1, len(l)):
                # l[i] ^= l[0]
                l[i] = bitwise_xor(l[i], l[0])
                l[i] = base_10(l[i])
    print(l)
    
#5 den Teil vor dem Gleichen trennt und die Werte in 2 Variablen beibehalten
def filter_numbers(L):
    equations = ["x=y+7", "2*y+1=x"]
    for idx in equations:
        condition, left, right = True,'', ''
        i = 0
        while i < len(idx):
            if idx[i] == '=':
                condition = False
            if condition:
                left += idx[i]
            else :
                right += idx[i]
            i += 1
        right = right.replace('=', '')
        for number in L:
            x, y = int(number // 10), int(number % 10)
            if eval(left) == eval(right):
                 print(number)

#6
def domino(l):
    length, maxlength, idx = 1, 1, -1
    for i in range (len(l) - 1):
        if l[i] % 10 == l[i + 1] // 10:
            length += 1
            if length == 2:
                idx = i
            if maxlength < length:
                maxlength, idxfin = length, idx
        else : 
            length = 1
    print(l[idxfin:idxfin + maxlength])

#7
# kleinsten gemeinsamen Vielfachen
def kgV(a, b): 
    m, n = a, b
    while a != b:
        if a > b:
            a -= b
        else :
            b -= a
    return (m * n) // a
# s speichert die kgV zwischen jedes aufeinanderfolge Elemente zwichen 
def vielfach(l):
    idx1 = int(input("left = "))
    idx2 = int(input("right = "))
    a = 1
    for i in range(idx1, idx2 + 1):
        s = kgV(a, l[i])
        a = s
    print(s)     


def main():
    print('Willkommen zu meiner Anwendung.')
    print('Wählen ein Binarzahl zwischen 0 oder 1.')
    print('0 wenn man die Elemente der Liste von dem Tastatur eingeben \n1 fur ein schon gebaut Liste')
    anzahl = int(input())
    match anzahl:
        case 0:
            l = []
            n = int(input('Die Anzahl von Elementen der Liste: '))
            for ele in range(0, n):
                ele = int(input())
                l.append(ele)
        case 1:
            l = [45, 20, 14, 56, 73, 31]
    
    ubung = int(input('''Eingeben ein Zahl, die ein Ubung zwischen 1 und 7 darstellt. 
    Etwas anderes wenn Sie die Anwendung schliessen möchten'''))
    match ubung:
        case 1:
            wiederholen(l)
        case 2:
            symmetrische_Paaren(l)
        case 3:
            konkatenation(l)
        case 4:
            encrypt(l)
        case 6:
            domino(l)
        case 7:
            vielfach(l)
        case _:
            print("Danke, dass Sie meine Anwendung benutzt haben")


if __name__ == "__main__":
    main()



