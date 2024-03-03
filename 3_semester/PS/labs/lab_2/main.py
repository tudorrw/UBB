import numpy
import random
from matplotlib.pyplot import axis, plot, figure, show, legend
import matplotlib.pyplot as plt
from matplotlib.patches import Circle
import math


def ersteProblem():
    gunstigenFalle = 0
    N = 1000
    for i in range(N):
        ereignis = set(numpy.random.randint(low=1, high=365, size=23))
        if len(ereignis) != 23:
            gunstigenFalle += 1
    print(gunstigenFalle / N)


def zweiteProblem():
    fig = plt.figure()
    axis("square")
    axis((0, 1, 0, 1))

    kreis = Circle((0.5, 0.5), 0.5, color='blue', fill=False)
    plt.gca().add_patch(kreis)

    N = 1000
    k = 0
    for _ in range(N):
        X = numpy.random.random()
        Y = numpy.random.random()
        color = 'yellow'
        if math.dist((X, Y), (0.5, 0.5)) < 0.5:
            k += 1
            color = 'magenta'
        plt.scatter(X, Y, c=color)
    fig.suptitle("Beispiel 1", fontweight="bold")
    plt.show()
    print(k / N)
    print(4 * k / N)


def random_color_generator():
    return "#" + ''.join([random.choice('ABCDEF0123456789') for _ in range(6)])


def lengthBetween2Points(x_values, y_values):
    return math.sqrt((x_values[0] - x_values[1]) * (x_values[0] - x_values[1]) + (y_values[0] - y_values[1]) * (
            y_values[0] - y_values[1]))


def dritteProblem():
    fig = plt.figure()
    axis("square")
    axis((0, 1, 0, 1))
    N = 10
    stumpf1 = 0
    stumpf2 = 0
    for _ in range(N):
        color = random_color_generator()
        X = numpy.random.random()
        Y = numpy.random.random()
        x_values = [X, 0]
        y_values = [Y, 0]
        erste = lengthBetween2Points(x_values, y_values)
        plt.plot(x_values, y_values, c=color, linestyle="--")
        x_values[1] = 1
        zweite = lengthBetween2Points(x_values, y_values)
        plt.plot(x_values, y_values, c=color, linestyle="--")
        y_values[1] = 1
        dritte = lengthBetween2Points(x_values, y_values)
        plt.plot(x_values, y_values, c=color, linestyle="--")
        x_values[1] = 0
        vierte = lengthBetween2Points(x_values, y_values)
        plt.plot(x_values, y_values, c=color, linestyle="--")
        count = 0
        if erste * erste + zweite * zweite < 1:
            count += 1
        if zweite * zweite + dritte * dritte < 1:
            count += 1
        if dritte * dritte + vierte * vierte < 1:
            count += 1
        if vierte * vierte + erste * erste < 1:
            count += 1

        if count == 1:
            stumpf1 += 1

        if count == 2:
            stumpf2 += 1

    fig.suptitle("Dritte Problem", fontweight="bold")
    plt.show()
    print('die Wahrscheinlichkeit, dass genau ein Winkel in A stumpf ist', stumpf1 / N)
    print('die Wahrscheinlichkeit, dass genau zwei Winkel in A stumpf ist', stumpf2 / N)



def zweiteFigur():
    fig = figure()
    axis("square")
    axis((0, 1, 0, 1))
    X = numpy.random.random(25)
    # Y = numpy.random.random(25)
    plot(X, numpy.square(X), "g*")  # zufallige Punkte auf dem Bild der Funktion F(x)=xˆ2
    plot(X, numpy.power(X, 4), "mo")  # zufallige Punkte auf dem Bild der Funktion F(x)=xˆ4
    plot(X[-1], numpy.square(X[-1]), "g*", label="xˆ2")
    plot(X[-1], numpy.power(X[-1], 4), "mo", label="xˆ4")
    legend(loc='upper left')
    fig.suptitle("Beispiel 2 ", fontweight="bold")
    show()


def main():
    ersteProblem()
    zweiteProblem()
    dritteProblem()
    zweiteFigur()


if __name__ == '__main__':
    main()
