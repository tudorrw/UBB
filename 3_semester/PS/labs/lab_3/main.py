import random
import numpy
from random import randrange
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks


# Aufgabe 1
def bedingte_Wahrscheinlichkeit(simulationen):
    countA = 0
    countB = 0
    countAB = 0
    for _ in range(simulationen):
        kugeln = random.sample(['r', 'b', 'g'], counts=[6, 4, 6], k=3)
        # print(kugeln)
        if 'r' in kugeln:
            countA += 1
        # print(countA)
        if len(set(kugeln)) == 1:
            countB += 1
        # print(countB)
        countAB = countAB + ('r' in kugeln and len(set(kugeln)) == 1)
        # print(countAB)
    print("P(A): ", countA / simulationen)
    print("P(B): ", countB / simulationen)
    print("P(A und B): ", countAB / simulationen)
    print("P(B|A): ", countAB / countA)

    print("teoretischen P(A): ",
          countA / simulationen)  # P("mindestens eine rote kugeln") = 1 - P("keine rote Kugeln") = 1 - 10/16 * 9/15 * 8/14
    print("teoretischen P(B): ", countB / simulationen)
    print("teoretischen P(A und B): ", countAB / simulationen)  # P((6*5*4)/(16*15*14))
    print("teoretischen P(B|A): ", countAB / countA)


# Aufgabe 3
def histogramm():
    N = 1000
    daten = [randrange(1, 7) + randrange(1, 7) + randrange(1, 7) for _ in range(N)]

    # print ( daten )
    z, count = numpy.unique(daten, return_counts=True)
    d = dict([(z[i], count[i] / N) for i in range(0, len(z))])
    print(d)

    bar(z, count / N, width=0.9, color="red", edgecolor="black", label="relative Haufigkeiten")
    # D = dict([(k, 3 / 216) for k in range(1, 7)])
    D = {k:0 for k in range(3, 19)}
    for i in range(1, 7):
        for j in range(1, 7):
            for k in range(1, 7):
                D[i+j+k] += 1 / 216

    bar(D.keys(), D.values(), width=0.7, color="blue", edgecolor="black", label="teoretische wahrscheinlichkeit")
    legend(loc="lower left")
    xticks(range(0, 7))
    grid()
    show()
    relative_max = max(d.values())
    theoretic_max = max(D.values())
    print("Teoretisch wette man auch die Summe " + ' '.join([str(key) for key, val in d.items() if val == relative_max]))
    print("Teoretisch wette man auch die Summe " + ' '.join([str(key) for key, val in D.items() if val == theoretic_max]))


#Aufgabe 4
def aufgabe4():
    c1, c2, a1, a2 = 0, 0, 0, 0
    N = 10000
    A = list(range(1, 21))
    for _ in range(N):
        i = numpy.random.randint(len(A))
        v = A[i]
        c1 = c1 + (v % 2) #impar
        c2 = c2 + ((v % 2) == 0) #par
        a1 = a1 + (v % 2) * ((v % 3) == 0) #divizibil cu 2 sau 3
        a2 = a2 + ((v % 2) == 0) * (6 <= v and v <= 10) #par sau intre 6 si 10
    p1 = a1 / c1
    p2 = a2 / c2 #frecventa 7 sau 9
    p3 = c1 / N #par
    print("Aus den Simulationen :")
    print(f"p1=", p1)
    print(f"p2=", p2)
    print(f"p3=", p3)

    print("Teoretischen Werte:")
    print(f"p1=", p1)
    print(f"p2=", p2)
    print(f"p3=", p3)

def main():
    # bedingte_Wahrscheinlichkeit(1000)
    histogramm()
    aufgabe4()


if __name__ == '__main__':
    main()
