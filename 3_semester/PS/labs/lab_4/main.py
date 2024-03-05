
import numpy
#fuer histogramm
import matplotlib.pyplot as plt
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks
#fuer a3 - binomialer Verteilung
from scipy.stats import binom
def a1():
    N=1000
    x=[0 ,1 ,3 ,5]
    P=[0.4 ,0.1 ,0.3 ,0.2]
    rng = numpy.random.default_rng()
    r=rng.choice(x, size=N , replace=True, p=P)
    z,count = numpy.unique(r, return_counts=True)
    d = dict([(z[i], count[i]/N ) for i in range(0, len(x))])
    print(d)
    bar (z, count /N , width =0.9 , color ="red", edgecolor ="black", label ="relative Haufigkeiten")

    D = dict([(x[k], P[k]) for k in range(len(x))])
    print(D)
    bar(D.keys(), D.values(), width=0.7, color="blue", edgecolor="black", label="teoretische Wahrscheinlichkeit")
    legend(loc="lower left")
    xticks(range(0,6))
    grid()
    show()

def a2():
    N=100
    tippfehler = [0, 1, 2, 3, 4]
    P = [0.25, 0.35, 0.25, 0.1, 0.05]
    rng = numpy.random.default_rng()
    r=rng.choice(tippfehler, size=N , replace=True, p=P)

    z,count = numpy.unique(r, return_counts=True)
    d = dict([(z[i], count[i]/N ) for i in range(0, len(tippfehler))])
    print(r,'\n', sum(d.values()))
    erwantungswert = numpy.mean(tippfehler)
    print(erwantungswert)

    bar(z, count/N, width=0.9, color="red", edgecolor="black", alpha=0.5, label="relative Haufigkeiten")

    D = dict([(tippfehler[k], P[k]) for k in range(len(tippfehler))])
    print(D)
    bar(D.keys(), D.values(), width=0.7, color="blue", edgecolor="black", alpha=0.5, label="teoretische Wahrscheinlichkeit")
    legend(loc="lower left")
    xticks(range(0,5))
    grid()
    show()

def a3():
    N = 500
    n = 8
    p = 0.5
    X = binom.rvs(n, p, size=N)
    print("zufaellige Werte fur X:")
    print(X)
    w = binom.pmf(5, n, p)
    print("P(X =", 5,f")={w:.6f}")

    z,count = numpy.unique(X, return_counts=True)
    print("Die Werte", z, "haben die Haufigkeiten:", count)

    bar(z, count/N, width=0.9, color="red", edgecolor="black", alpha=0.5, label="relative Haufigkeiten")


    D = dict([(k, binom.pmf(k, n, p)) for k in range(0, n+1)])
    print(D)
    bar(D.keys(), D.values(), width=0.7, color="blue", edgecolor="black", alpha=0.5, label="teoretische Wahrscheinlichkeit")
    legend(loc="lower left")
    xticks(range(0,n))
    grid()
    show()


def a4():
    N = 100
    n = 7
    p = 0.4
    
    a = binom.cdf(3, n, p)
    b = 1 - binom.cdf(4, n, p) + binom.pmf(4, n, p)
    c = binom.pmf(4, n, p)
    print("hochstens 3 Rechner: ", a)
    print("mindestens 4 Rechner: ", b)
    print("genau 4 Rechner: ", c)


    X = binom.rvs(n, p, size=N)
    z,count = numpy.unique(X, return_counts=True)
    bar(z, count/N, width=0.9, color="red", edgecolor="black", alpha=0.5, label="relative Haufigkeiten")
    D = dict([(k, binom.pmf(k, n, p)) for k in range(0, n+1)])
    bar(D.keys(), D.values(), width=0.7, color="blue", edgecolor="black", alpha=0.5, label="teoretische Wahrscheinlichkeit")
    legend(loc="lower left")
    xticks(range(0,n))
    grid()
    show()


def a5():
    pass

def main():
    # a1()
    # a2()
    # a3()
    a4()


if __name__ == '__main__':
    main()
