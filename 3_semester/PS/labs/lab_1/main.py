
import math
import random
from itertools import permutations, combinations
# erste Ubung

def liste_mit_permutationen():

    # gesamte Anzahl der Permutationen von ABC?
    print("die gesamte Anzahl von Permutation von 3 Elementen:", math.perm(3))
    for u in permutations("ABC"):
        print("".join(u))

    # eine zufallige Permutation von MATHE.
    lst = 'MATHE'
    print("eine zufallige Permutation von MATHE:", random.sample(population = lst, k = len(lst)))

    # eine zufallige Variation mit 3 Buchstaben aus dem String MATHE
    print("zufallige Variation mit 3 Buchstaben:", random.sample(lst , k = 3))

    # alle Variationen (Anordungen ohne Wiederholung mit Berucksichtigung der Reihenfolge)
    print("alle Variationen  mit 3 Buchstaben:")
    M = list(permutations(lst,3))
    print(M)
    print("daca merge pushu asta in git is top rau")

if __name__ == '__main__':
    liste_mit_permutationen()