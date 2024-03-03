import random

from scipy.stats import uniform
from matplotlib.pyplot import show, legend, hist
import numpy


def test():
    N = 2000
    X = uniform.rvs(loc=10,scale=10,size=N)
    print("Anhand Simulationen: ")
    # a1
    print("die Durchlaufzeit zwischen 14 und 18 Minuten P(14 <= X <= 18): ", numpy.mean((X >= 14) & (X <= 18)))
    # a2
    print("die Durchlaufzeit hochstens 15 Minuten P(X <= 15): ", numpy.mean(X <= 15))
    # a3
    print("die Vereiningung zwischen P(X <= 15) und P(X >= 17): ", numpy.mean(X <= 15) + numpy.mean(X >= 17))
    print("Teoretische Wahrscheinlichkeiten: ")
    print("die Durchlaufzeit zwischen 14 und 18 Minuten: ", uniform.cdf(18, loc=10, scale=10) - uniform.cdf(14, loc=10, scale=10))
    print("die Durchlaufzeit hochstens 15 Minuten: ", uniform.cdf(15, loc=10, scale=10))
    print("die Vereiningung zwischen A und B: ", uniform.cdf(15, loc=10, scale=10) + 1 - uniform.cdf(17, loc=10, scale=10))

    hist(X, bins=10, density=False, edgecolor="black", label="abs. Hfg.")
    legend()
    show()

def wurfelspiel():
    a = b = c = 0
    N =100000
    for _ in range(N):
        x = random.choices([1,2,3,4,5,6], k = 4)
        a = a + (x.count(6) > 0)
    print("aus Simulationen sind P(A):", a / N, ", P(B):", b / N, ", P(C):", c / N)



if __name__ == '__main__':
    # test()
    wurfelspiel()
