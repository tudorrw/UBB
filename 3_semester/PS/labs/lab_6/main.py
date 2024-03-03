import math
import random
import numpy
from matplotlib.pyplot import bar, show, legend, hist

def ubung1():
    X = [4, 5, 6, 7, 8, 9, 10]
    P = [0.05, 0.1, 0.1, 0.35, 0.2, 0.1, 0.1]
    N = 800
    D = random.choices(X, weights=P, k=N)
    print(D)
    print("Erwartungswert E(X): ", numpy.mean(D))
    s1,s2 = 0,0
    for i in range(len(X)):
        s1 += X[i] * P[i]
        s2 += X[i] * X[i] * P[i]
    s3 = sum(numpy.array(X) * numpy.array(P))
    print("theoretische E(X): ", s1, ", andere Methode: ",s3)

    print("Varianz V(X): ", numpy.var(D))
    print("theoretische V(X): ", s2 - s1 * s1)
    c7, c4 = 0, 0
    for i in D:
        if i <= 7:
            c7 += 1
        if i > 4:
            c4 += 1

    print("die Wahrscheinlichkeit P(X<=7): ", c7 / N)
    print("teoretische P(X<=7): ", sum(numpy.array(P[:4])))

    print("die Wahrscheinlichkeit P(X>4): ", c4 / N)
    print("teoretische P(X>4): ", sum(numpy.array(P[1:])))

    z,count = numpy.unique(D, return_counts=True)
    print(z,count)
    #relative Haufigkeit
    bar(z, count/N, width=0.9, color="blue", edgecolor="black", label="Rel. Hfg")
    legend()
    show()
    #teoretische Haufigkeit
    bar(X, P, width=0.9, color="red", edgecolor="black", label="Teoretische. Hfg")
    legend()
    show()
    #absolute Haufigkeit
    bar(z, count, width=0.9, color="yellow", edgecolor="black", label="Absolute. Hfg")
    legend()
    show()

from scipy.stats import uniform
def ubung2():
    N=1000
    x = uniform.rvs(loc=-2,scale=4,size=N)
    y = uniform.rvs(loc=-2,scale=4,size=N)
    z = uniform.rvs(loc=-2,scale=4,size=N)
    X = [math.dist([x[i], y[i], z[i]], [2,2,2]) for i in range(N)]
    print("Anhand Simulationen der Erwartungswert E(X): ", numpy.mean(X))
    Z = [math.dist([x[i], y[i], z[i]], [0, 0, 0]) for i in range(N)]
    R = 2
    c = 0
    for i in Z:
        if i <= R:
            c += 1
    print("Wkt. zufalliger Punkt im Inneren der Kugeln (aus Simulationen)", c/len(Z))
    w = ((4/3) * math.pi * (R**3)) / (4**3)
    print("teoretische Wkt. zufalliger Punkt im Inneren der Kugeln (aus Simulationen)", w)

from scipy.stats import expon
def ubung3_gresit():
    N = 1000
    alpha = 1 / 5
    D1 = expon.rvs(loc=0, scale=1 / alpha, size=N)
    D2 = uniform.rvs(loc=4,scale=2,size=N)

    c1 = 0
    for i in D1:
        if i > 5:
            c1 += 1
    print("Wkt. aus D1 mehr als 5 Sekunden dauert (aus Simulationen)", c1/len(D1))
    c2 = 0
    for i in D2:
        if i > 5:
            c2 += 1
    print("Wkt. aus D2 mehr als 5 Sekunden dauert (aus Simulationen)", c2/len(D2))

    print("der Erwartungswert von D1: ", numpy.mean(D1))
    print("der Erwartungswert von D2: ", numpy.mean(D2))

    print("der Standardabeweichung von D1: ", numpy.std(D1))
    print("der Standardabeweichung von D2: ", numpy.std(D2))


def ubung3():
    alpha = 1 / 5
    N = 1000
    daten = [expon.rvs(scale=1 / alpha) if random.random() < 0.4 else uniform.rvs(loc=4, scale=2) for _ in range(N)]

    print("Wkt. mehr als 5 Sekunden dauert (aus Simulationen)", numpy.mean(numpy.array(daten) > 5))
    print("der Erwartungswert: ", numpy.mean(daten))
    print("die Standardabeweichung : ", numpy.std(daten))

def ubung4():
    N = 1000
    B = uniform.rvs(loc=-1,scale=2,size=N)
    C = uniform.rvs(loc=-1,scale=2,size=N)
    daten = []
    reell, positiv = 0,0
    for i,j in zip(B,C):
        try:
            r = math.sqrt(i**2 - 4*j)
            x1 = (-i - r) / 2
            x2 = (-i + r) / 2
            # print(r, x1, x2)
            if x1 > 0 and x2 > 0:
                positiv += 1
            reell += 1
            daten.append(x1+x2)
        except ValueError:
            pass
    print("a) Wahrscheinlichkeit, dass beide Wurzeln reell sind:", reell/N)
    print("b) Wahrscheinlichkeit, dass beide Wurzeln positiv sind:", positiv/N)
    print("Erwartungswert E(X): ", numpy.mean(daten))
    print("Varianz V(X): ", numpy.var(daten))



def ubung5():
    X = ['r'] * 20 + ['b'] * 15 + ['g'] * 5 + ['s'] * 10
    N = 1000
    D = random.choices(X, k=N)
    print(D)
    z,count = numpy.unique(D, return_counts=True)
    print(z,count)
    P = [0.3, 0.1, 0.4, 0.2]

    print("Relative Haufigkeit: ", count/N)
    print("Teoretishe Haufigkeit: ", numpy.array(P))
    print("die Ergebnisse der ersten 10 Ziehungen:", D[:10])
    bar(z, count / N, width=0.9, color="red", edgecolor="black", label=" relative Haufigkeiten ")
    legend()
    show()
    bar(z, P, width=0.6, color="blue", edgecolor="black", label=" theoretisch Haufigkeiten ")
    legend()
    show()



def ubung6():
    urne = numpy.array([0]*10 + [1]*20 + [2]*20)
    N = 1000
    X = []
    for _ in range(N):
        kugeln = numpy.random.choice(urne, size=3, replace=False) #replace=False (ohne Zurucklegen)
        X.append(numpy.prod(kugeln))
    print("Erwartungswert E(X): ", numpy.mean(X))
    print("Varianz V(X): ", numpy.var(X))

    z,count = numpy.unique(X, return_counts=True)
    p1 = (20 * 19 * 18) / (50 * 49 * 48)
    p2 = (20/50) * (20/49) * (19/48) * 3
    p4 = (20/50) * (19/49) * (20/48) * 3
    p8 = (20 * 19 * 18) / (50 * 49 * 48)
    P = [1.0 - (p1 + p2 + p4 + p8), p1, p2, p4, p8]
    print(P)
    bar(z, count, width=0.9, color="yellow", edgecolor="black", label=" absolute Haufigkeiten ")
    legend()
    show()
    # hist(X, edgecolor='black', alpha=0.7, density=True, label="absol. Hfg.")
    # show()
    bar(z, P, width=0.9, color="red", edgecolor="black", label="Teoretische. Hfg")
    legend()
    show()

from math import factorial
def ubung7():
    b,r,w = 3,3,4
    alle = b + r + w
    lst = ['b'] * b + ['r'] + ['w'] * w
    N = 1000
    t = 0
    for _ in range(N):
        Z = random.sample(lst, k = 3)
        if len(set(Z)) == 1:
            t += 5
        elif len(set(Z)) == 3:
            t += 2
        else:
            t -= 1
    print("Gewinn oder Verlust im Mittel", t / N)  # simulierter Erwartungswert fur Gewinn oder Verlust
    p1 = (b/alle) * (b-1)/(alle-1) * (b-2)/(alle - 2) + (r/alle) * (r-1)/(alle-1) * (r-2)/(alle - 2) + (w/alle) * (w-1)/(alle-1) * (w-2)/(alle - 2)
    p2 = 6 * (b/alle) * (r/(alle-1)) * (w/(alle-2))
    p3 = 1 - p1 - p2
    print(f"theoretische Wkt: p1={p1:6.5f}, p2={p2:6.5f}, p3={p3:6.5f}; Summe der theor. Wkt.:", p1 + p2 + p3)

    s = 0
    X = [5, 2, -1]
    P = [p1, p2, p3]  # die Wahrscheinlichkeiten fur von X
    for i in range(len(X)):
        s = s + X[i] * P[i]
    print(f"theoretischer Erwartungswert fur Gewinn oder Verlust {s:6.5f}")



from matplotlib.pyplot import plot
def ubung8():
    N = 1000
    X = uniform.rvs(loc = -2, scale = 4, size = N)
    xunif = numpy.linspace(min(X), max(X), 100)
    yunif = uniform.pdf(xunif, loc=-2, scale=4)
    # print(xunif, yunif)

    #[-3, 3]
    xunif = numpy.linspace(-3, 3, 100)
    y_dichte = uniform.pdf(xunif, loc=-2, scale=4)
    y_verteilung = uniform.cdf(xunif, loc=-2, scale=4)
    plot(xunif, y_dichte, "r-", label="Unif Dichtefunktion")
    plot(xunif, y_verteilung, "r-", label="Unif Verteilungsfunktion")
    legend()
    show()


    alpha = 2
    Y = expon.rvs(loc=0, scale=1 / alpha, size=N)
    # [0, 4]
    xexpon = numpy.linspace(0, 4, 100)
    yexpon_dichte= expon.pdf(xexpon, loc=0, scale=1 / alpha)
    yexpon_verteilung = expon.cdf(xexpon, loc=0, scale=1 / alpha)
    plot(xunif, yexpon_dichte, "r-", label="Expon Dichtefunktion")
    plot(xunif, yexpon_verteilung, "r-", label="Expon Verteilungsfunktion")
    legend()
    show()

    # 2
    punif = 0
    for i in X:
        if 1 < i and i < 1.5:
            punif += 1
    pexpon = 0
    for i in Y:
        if 1 < i and i < 1.5:
            pexpon += 1
    print("Simulationen Unif: ", punif / N, " und expon: ", pexpon / N)

    th_unif = uniform.cdf(1.5, loc=-2, scale=4) - uniform.cdf(1, loc=-2, scale=4)
    th_expon = expon.cdf(1.5, loc=0, scale=1/alpha) - expon.cdf(1,loc=0, scale=1/alpha)
    print("Theoretische Wkt. Unif: ", th_unif / N, " und expon: ", th_expon / N)

    # 3
    eunif= numpy.mean(X)
    eexpon=numpy.mean(Y)
    print("Erwartungs")
    print(eunif)
    print(eexpon)

    vunif=numpy.var(X)
    vexpon=numpy.var(Y)
    print("Varianz")
    print(vunif)
    print(vexpon)


from itertools import permutations, combinations
def ubung9():
    # a
    wort = 'mutig'
    perms = [''.join(p) for p in permutations(wort)]
    print("Die Permutionen: ", perms)
    print("die Anzahl der Permutationen: ", len(perms))
    # b
    print(random.sample(wort, len(wort)))
    print(random.sample(wort, len(wort)))
    # c
    variationen_4_buchstaben = [''.join(p) for p in permutations(wort, 4)]
    print("alle Variationen mit vier Buchstaben : ", variationen_4_buchstaben)
    print("die Anzahl von alle Variationen mit vier Buchstaben : ", len(variationen_4_buchstaben))

    combination = [''.join(p) for p in permutations(wort, 2)]
    print("alle Kombinationen mit zwei Buchstaben: ", combination)
    print("die Anzahl alle Kombinationen mit zwei Buchstaben: ", len(combination))

if __name__ == '__main__':
    # ubung1()
    # ubung2()
    # ubung3()
    # ubung4()
    # ubung5()
    # ubung6()
    # ubung7()
    # ubung8()
    ubung9()

