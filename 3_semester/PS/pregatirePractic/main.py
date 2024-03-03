from random import randrange
# unabhangige Ereignisse(eveniment) pag 8 vorlesung
def ubung1_roulette():
    X = [175, -5]
    P = [1/38, 37/38]
    N = 1000
    daten = numpy.random.choice(X, size=N, p=P)
    print("Erwartungswert E(X: ", numpy.mean(daten))



import numpy
from scipy.stats import binom
def ubung2_ZG():
    N = 100
    x = [80, 15, 5]
    P = [0.8, 0.15, 0.05]
    rng = numpy.random.default_rng()
    # a) Man simuliere N = 100 mögliche Werte der ZG X.
    r = rng.choice(x, size=N, replace=True, p=P)
    print(r)
    # b) mittlere Anzahl M der Waren mit grossen Fehlern
    z,count = numpy.unique(r, return_counts=True)
    print(z, count)
    print("mittlere Anzahl M der Waren mit grossen Fehlern: ", count[0] / N)
    # c) theoretische Wahrscheinlichkeit dass von den nächsten hergestellten 100 Exemplaren dieser Ware
    # 1) höchstens 3 große Fehler besitzen
    wkt_hochestens_3 = binom.cdf(3, N, P[2])
    print("hochstens 3 :", wkt_hochestens_3)
    # 2) genau 10 große Fehler besitzen
    wkt_genau_10 = binom.pmf(4, N, P[2])
    print("genau 10", wkt_genau_10)
    # 3) mindestens 4 große Fehler besitzen
    wkt_mindestens_4 = 1 - binom.cdf(4, N, P[2]) + binom.pmf(4, N, P[2])
    print("mindestens 4 :", wkt_mindestens_4)

from scipy.stats import norm
def ubung3_lebensdauer():
    N = 10
    erwartungswert = 10000
    standardabweichung = 200
    daten = norm.rvs(erwartungswert, standardabweichung, N)
    print("mogliche Werte fur X: ", daten)
    print("mehr als 1500 Stunden: ", 1 - norm.cdf(1500, erwartungswert,standardabweichung))
    print("hochstens 6500 Stunden: ", norm.cdf(6500, erwartungswert,standardabweichung))
    print(" zwischen 7500 und 10500 Stunden: ", norm.cdf(10500, erwartungswert, standardabweichung) - norm.cdf(7500, erwartungswert, standardabweichung))


def ubung4_stichprobe():
    lst = [309 , 333 , 309 , 330, 325, 325 , 325 , 333 , 314 , 314, 330, 314, 314, 330]
    data = numpy.array(lst)
    print("durchschnittliche Lebensdauer: ", numpy.mean(data))
    print("empirische Standardabweichung: ", numpy.std(data, ddof=1))
    print("P(X>310): ", numpy.mean(data > 310))

import matplotlib.pyplot as plt
def ubung5_stichprobe_histogramme():
    X = [299, 299, 297, 303, 299, 301, 300, 297, 302, 303, 300, 299, 301, 302, 301, 299, 300, 297, 300, 300, 296, 303,
         295, 295, 297]
    data = numpy.array(X)
    Hfg, Klasse = numpy.histogram(data)
    print("Haufigkeit in jeder Klasse (in jedem Intervall)", Hfg)
    print("Lange Hfg: ", len(Hfg))
    print("Klassenden (Endpunkte der Intervalle)", Klasse)
    print("Lange Klasse: ", len(Klasse))
    for i in range(len(Hfg)):
        print(f"({i+1:2d}) absolute Hfg. {Hfg[i]} in der Klasse [{Klasse[i]:7.4f}, {Klasse[i+1]:7.4f}]")

    plt.hist(data, edgecolor="red", bins=numpy.arange(min(X), max(X)+1),label="abs. Hfg.")
    plt.grid()
    plt.show()
    plt.hist(data, density=True, bins=numpy.arange(min(X), max(X)+1),edgecolor="red", label="rel. Hfg.")
    plt.grid()
    plt.show()
    print("P(X<301): ", numpy.mean(data < 301))



def ubung6_schrauben():
    erwartungswert = 10
    standardabweichung = 1
    N = 1000
    daten = norm.rvs(erwartungswert, standardabweichung, N)
    print("geschätzte Wkt kürzer als 9: ", numpy.mean(daten < 9), "::", len([x for x in daten if x < 9]) / N)
    print("teoretische Wkt kürzer als 9: ", norm.cdf(9, erwartungswert,standardabweichung))

    print("geschätzte Wkt höchstens 10.1 und mindestens 8.9: ", numpy.mean((daten <= 10.1) & (daten >= 8.9)))
    print("teoretische Wkthöchstens 10.1 und mindestens 8.9: ", norm.cdf(10.1, erwartungswert, standardabweichung) - norm.cdf(8.9, erwartungswert, standardabweichung))

# 7

from matplotlib.pyplot import axis, plot, figure, show, legend
import math

def ubung7_quadrat():
    fig = plt.figure()
    plt.axis("square")
    plt.axis((0, 2, 0, 2))
    plt.plot([0, 2], [0, 2], c="black", linestyle="-")
    plt.fill_between([0, 2, 2], [0, 0, 2], [0, 2, 2], color="blue", alpha=0.3, label="Triangle")

    N = 1000
    magenta_count = 0
    yellow_count = 0

    for _ in range(N):
        X = numpy.random.uniform(0, 2)
        Y = numpy.random.uniform(0, 2)

        if X >= Y:
            plt.scatter(X, Y, c='magenta')
            magenta_count += 1
        else:
            plt.scatter(X, Y, c='yellow')
            yellow_count += 1

    # Calculate probabilities
    prob_magenta = magenta_count / N
    prob_yellow = yellow_count / N

    print("Probability of points in the magenta region:", prob_magenta)
    print("Probability of points in the yellow region:", prob_yellow)

    fig.suptitle("Square with Triangle", fontweight="bold")
    plt.show()

def ubung8_Bino():
    n = 4
    p = 0.25
    N = 1000
    X = binom.rvs(n, p, size=N)
    Y = X**2 + 1
    print(Y)

    plt.hist(Y, edgecolor="red" ,label="abs. Hfg.")
    plt.grid()
    plt.show()

    g_wkt = numpy.mean(Y > 5)
    t_wkt = 1 - binom.cdf(X,n,p)[5]
    print("geschätzte Wahrscheinlichkeit: ", g_wkt)
    print("teoretischen Wahrscheinlichkeit: ", t_wkt)
    if (g_wkt < t_wkt):
        print("geschätzte Wahrscheinlichkeit kleiner als teoretische Wahrscheinlichkei")
    else:
        print("geschätzte Wahrscheinlichkeit grosser als teoretische Wahrscheinlichkei")

    # z,count = numpy.unique(Y, return_counts=True)
    # print("Die Werte", z, "haben die Haufigkeiten:", count)

def ubung10_Norm():
    erwartungswert = 3
    varianz = 4
    standardabweichung = numpy.sqrt(varianz)
    N = 100
    daten = norm.rvs(loc = erwartungswert, scale = standardabweichung, size = N)
    # print(daten)
    print("geschätzte Wahrscheinlichkeit: ", numpy.mean(daten > 4))
    print("teoretische Wahrscheinlichkeit: ", norm.cdf(4, erwartungswert, standardabweichung))

import random
def ubung11():
    r,w,b = 6,4,10
    alle = r + w + b
    urne = ['r'] * r + ['w'] * w + ['b'] * b
    N = 1000
    z1 = 0
    z2 = 0
    for _ in range(N):
        Z = random.sample(urne, k = 4)
        kugeln_zurucklegen = numpy.random.choice(urne, size=4)
        if kugeln_zurucklegen[0] == 'r' and kugeln_zurucklegen[1] == 'w' and kugeln_zurucklegen[2] == 'b' and kugeln_zurucklegen[3] == 'b':
            z1 += 1
        kugeln_ohne_zurucklegen = numpy.random.choice(urne, size=4, replace=False) #replace=False (ohne Zurucklegen)
        if kugeln_ohne_zurucklegen[0] == 'r' and kugeln_ohne_zurucklegen[1] == 'w' and kugeln_ohne_zurucklegen[2] == 'b' and kugeln_ohne_zurucklegen[3] == 'b':
            z2 += 1

    print("Mit Zurucklegen: ", z1/N)
    print("Ohne Zurucklegen: ", z2/N)
    th_mit_Zurucklegen = (r/alle) * (w/alle) * (b/alle) * (b/alle)
    print("Th Wkt. mit zurucklegen", th_mit_Zurucklegen)

    th_ohne_Zurucklegen = (r/alle) * (w/(alle-1)) * (b/(alle-2)) * (b-1)/(alle-3)
    print("Th Wkt. ohne zurucklegen", th_ohne_Zurucklegen)

def ubung12():
    N = 10
    X = []
    for _ in range(N):
        seiten = random.choices([1,1,1,1,2,2], k=2)
        X.append(sum(seiten))
    p2 = (4/6) * (4/6)
    p3 = (2/6) * (4/6) * 2
    p4 = (2/6) * (2/6)
    print("Theoretische Wahrscheinlichkeiten:")
    print("P(X=2):", p2)
    print("P(X=3):", p3)
    print("P(X=4):", p4)
    print(X)

    erwartungswert = numpy.mean(X)
    wkt_grosser_als_2 = numpy.mean(numpy.array(X) > 2)
    print("erwartende Summe E(X): ", erwartungswert)
    print(" Wahrscheinlichkeit, dass die Summe größer als 2 ist: ", wkt_grosser_als_2)

from matplotlib.patches import Circle
def ubung13():
    fig = plt.figure()
    plt.axis("square")
    plt.axis((0, 2, 0, 2))
    kreis = Circle((0, 0), 1, color='orange', fill=True, alpha=0.6)
    plt.gca().add_patch(kreis)
    plt.show()

from scipy.stats import hypergeom, geom
def ubung14():
    N = 1000
    lottospiel = hypergeom.rvs(M=49,n=6,N=6, size=N)
    z,count = numpy.unique(lottospiel, return_counts=True)
    print(z, count)
    wkt_genau_2_korrekt = len([x for x in lottospiel if x == 2]) / N
    print("Wkt, das man in einem Lottospiel geanu 2 Zahlen richtig erratet: ", wkt_genau_2_korrekt)

def ubung15():
    N = 1000
    #erste methode
    daten = []
    for _ in range(N):
        bis_6_auftaucht = 0
        while numpy.random.choice([1,2,3,4,5,6]) != 6:
            bis_6_auftaucht += 1
        daten.append(bis_6_auftaucht+1)
    print("Durchschnittliche Anzahl der Würfe bis zur ersten 6:", numpy.mean(daten))
    #zweite methode
    daten = geom.rvs(1/6, size=N)
    print("Durchschnittliche Anzahl der Würfe bis zur ersten 6:", numpy.mean(daten))


def ubung16():
    n = 10
    p = 0.3
    N = 100
    daten = binom.rvs(n, p, size=N)
    print("Anhand Simulationen: ")
    print("Wahrscheinlichkeit P(3 < X < 7): ", numpy.mean((daten > 3) & (daten < 7)))
    print("Erwartungswert E(X): ", numpy.mean(daten))
    print("Varianz V(X): ", numpy.var(daten))

    print("Theoretischen Werten: ")
    print("Wahrscheinlichkeit P(3 < X < 7): ", binom.cdf(7,n,p) - binom.cdf(3,n,p))
    print("Erwartungswert E(X): ", binom.mean(n, p))
    print("Varianz V(X): ", binom.var(n, p))

def ubung17():
    X = ['A', 'B']
    P = [0.46, 0.54]
    N=500
    simulationen = 1000
    # rng = numpy.random.default_rng()
    # r = rng.choice(X, size=N, replace=True, p=P)
    # print(r)
    c = 0
    for _ in range(simulationen):
        daten = numpy.random.choice(X, size=N, replace=True, p=P)

        # print(len([i for i in daten if i == 'A']))
        if len([i for i in daten if i == 'A']) > 235:
            c += 1
    print("Wahrscheinlichkeit, dass anhand der Daten mehr als 235 Bürger Kandidat A wählen möchten: ", c / simulationen)

def ubung18():
    erwartungswert = 60
    standardabweichung = 5
    N=1000
    daten = norm.rvs(erwartungswert, standardabweichung, size=N)
    print("Wkt., dass die Niederschlagsmenge mehr als 55 ist: ", numpy.mean(daten > 55))
    print("teoretische Wkt., dass die Niederschlagsmenge mehr als 55 ist: ", 1 - norm.cdf(55, erwartungswert, standardabweichung))


def ubung19():
    X = ['A', 'B']
    P = [0.6, 0.4]
    N = 2000
    spielerA = 0
    for _ in range(N):
        a, b = 0, 0
        while a < 3 and b < 3:
            spiel = numpy.random.choice(X, p=P)
            if spiel == 'A':
                a += 1
            else:
                b += 1
        if a == 3:
            spielerA += 1
    print("die Gewinnwahrscheinlichkeiten für Spieler A bzw. Spieler B: ", spielerA / N)

def ubung21():
    P = [0.85, 0.15]
    N = 1000
    verfehlt = 0
    for _ in range(N):
        daten = numpy.random.choice(['JA', 'NEIN'], size=3, p=P)
        if 'NEIN' in daten:
            verfehlt += 1
    print("Wahrscheinlichkeit, dass er bei drei Versuchen mindestens einmal daneben schießt?", verfehlt / N)
    # die teoretische Wkt.
    X = [0, 1, 2, 3]
    p_gol = 0.85
    th_wkt = [binom.pmf(x, 3, p_gol) for x in X]
    print("Th. Wkt.: ", th_wkt)

def ubung23():
    lostrommel = ['G'] * 10 + ['N'] * 30
    N = 1000
    mindestens_ein_gewinnlos, nur_nieten= 0, 0
    for _ in range(N):
        daten = numpy.random.choice(lostrommel, replace=False, size=3)
        if 'G' in daten:
            mindestens_ein_gewinnlos += 1
        else:
            nur_nieten += 1
    print("Wahrscheinlichkeit bei dreimalingem Ziehen: ")
    print("mindestens ein Gewinnlos zieht: ", mindestens_ein_gewinnlos / N)
    print("nur Nieten zieht: ", nur_nieten / N)

    X = [0, 1, 2, 3]
    p_gewinn = 10 / 40
    th_wkt = [binom.pmf(x, 3, p_gewinn) for x in X]
    print((th_wkt))

from scipy.stats import geom
def ubung24():
    N = 1000
    P_erfolg = 0.3

    X = geom.rvs(P_erfolg, size=N)
    print(X)
    plt.hist(X, bins=range(1, max(X) + 2), edgecolor='black', alpha=0.7, density=True)
    plt.title("Histogramm der Zufallsgröße X")
    plt.xlabel("Anzahl der Schüsse bis zum ersten Treffer")
    plt.ylabel("Relative Häufigkeit")
    plt.show()

    print("Durchschnittliche Anzahl der Schusse: ", numpy.mean(X))
    print("P(X<5): ", numpy.mean((X<5)))
    print("teoretische Wahrscheinlichkeit: ", geom.cdf(5, P_erfolg, loc=0) - geom.pmf(5, P_erfolg,loc=0))


if __name__ == '__main__':
    ubung1_roulette()
    # ubung2_ZG()
    # ubung3_lebensdauer()
    # ubung4_stichprobe()
    # ubung5_stichprobe_histogramme()
    # ubung6_schrauben()
    # ubung7_quadrat()
    # ubung8_Bino()
    # ubung10_Norm()
    # ubung11()
    # ubung12()
    ubung13()
    # ubung14()
    # ubung15()
    # ubung16()
    # ubung17()
    # ubung18()
    # ubung19()
    # ubung21()
    # ubung23()
    # ubung24()