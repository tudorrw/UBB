import random

import matplotlib.pyplot as plt
from scipy.stats import norm, expon
import numpy

def a1():
    mu= 199
    sigma =3
    N= 1000
    Daten = norm.rvs(mu,sigma,N) #Erwartungswert anhand der Daten
    print(Daten)
    m = numpy.mean(Daten)
    print("p1 Daten <= 195 : ", sum(Daten <= 195) / N) #zahlt wie viele True werte sind
    print("p1 Daten <= 195 : ", numpy.mean(Daten <= 195))
    print("p1 Daten >= 195 and Daten <= 198: ", numpy.mean((Daten >= 195) & (Daten <= 198)))
    print("w1 theor. Wkt. P(X <= 195): ", norm.cdf(195,mu,sigma))
    print("w2 theor. Wkt. P(195 <= X <= 198): ", norm.cdf(198,mu,sigma)-norm.cdf(195,mu,sigma))
    print("p3 Daten <= 195 : ", numpy.mean(195 <= Daten))
    print("w23theor. Wkt. P(195 <= Daten): ", 1-norm.cdf(195,mu,sigma))

    Hfg, Klasse = numpy.histogram(Daten, bins=16)
    print("Haufigkeit in jeder Klasse (in jedem Intervall)", Hfg)
    print("Lange Hfg: ", len(Hfg))
    print("Klassenden (Endpunkte der Intervalle)", Klasse)
    print("Lange Klasse: ", len(Klasse))

    for i in range(len(Hfg)):
        print(f"({i+1:2d}) absolute Hfg. {Hfg[i]:3d} in der Klasse [{Klasse[i]:7.4f}, {Klasse[i+1]:7.4f}]")

    plt.hist(Daten, bins=16, density=True, edgecolor="black", label="rel. Hfg.")
    x = numpy.linspace(min(Daten), max(Daten), 100)
    y = norm.pdf(x,mu,sigma)
    plt.plot(x,y,'r-')
    plt.legend()
    plt.grid()
    plt.show()


def a2():
    alpha = 1 / 12
    N = 1000
    data = expon.rvs(loc = 0, scale = 1/alpha, size = N)
    print(data)

    # a)
    print("durchschnittliche Druckzeit: ", numpy.mean(data)) #Erwartungswert anhand simulierten Daten

    # b)
    plt.hist(data, bins=12, density=True, color="green", edgecolor="black", label="rel. Hfg.")
    x = numpy.linspace(min(data), max(data), 80)
    y = expon.pdf(x, loc = 0, scale = 1 / alpha)
    plt.plot(x, y, "r-", label="Dichtfkt. Exp(1/12)")
    plt.legend()
    plt.grid()
    plt.show()

    # c)
    #wahrscheinlichkeiten
    print("p1: ", numpy.mean(data < 20))
    print("theor. w1: ", expon.cdf(20, loc = 0, scale = 1 / alpha))

    print("p2: ", numpy.mean(data > 20))
    print("theor. w2: ", expon.cdf(10, loc = 0, scale = 1 / alpha))

    print("p3: ", numpy.mean((data > 10) & (data < 30)))
    print("theor. w3: ", expon.cdf(30, loc = 0, scale = 1 / alpha) - expon.cdf(10, loc = 0, scale = 1 / alpha))

    # d)
    plt.hist(data, bins=12, density=True, color="green", edgecolor="black", label="abs. Hfg.")
    plt.legend()
    plt.show()
    Hfg, Klasse = numpy.histogram(data, bins=12)
    for i in range(len(Hfg)):
        print(f"({i+1:2d}) absolute Hfg. {Hfg[i]:3d} in der Klasse [{Klasse[i]:7.4f}, {Klasse[i+1]:7.4f}]")

    # e)
    t = numpy.linspace(min(data), max(data), 80)
    alpha = 1
    y = expon.pdf(t, loc=0, scale=1 / alpha)
    plt.plot(t, y, "m-", label="Dichtfkt. Exp(1)")
    plt.legend()
    plt.grid()
    plt.show()

def ubung4():
    N = 1000
    X = []
    for _ in range(N):
        kugel = ''
        versuchen = 0
        while kugel != 'w' and versuchen < 3:
            kugel = random.sample(population=['w', 's'], counts=[4,6], k=1)
            versuchen += 1
        X.append(versuchen)
    print(X)
    w,s = 4,6
    alle = w + s
    p1 = (w/alle)
    p2 = (s/alle) * (w/(alle-1))
    p3 = (s/alle) * (s-1)/(alle-1) * (w/(alle-2))
    p4 = (s/alle) * (s-1)/(alle-1) * (s-2)/(alle-2)
    P = [p1, p2, p3, p4]
    print(P)



def main():
    # a1()
    # a2()
    ubung4()


if __name__ == '__main__':
    main()