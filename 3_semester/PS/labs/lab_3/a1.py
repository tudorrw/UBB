
import random
# def wahrscheinlichkeit_alle_dieselbe_farbe(red, blue, green, counts):
#     anzahl_kugeln = red + blue + green
#     def wahrscheinlichkeit_eine_farbe(colour, anzahl_kugeln):
#         wahrscheinlichkeit = 1.0
#         for i in range(counts):
#             wahrscheinlichkeit *= (colour - i) / (anzahl_kugeln - i)
#         return wahrscheinlichkeit
#
#     wahrscheinlichkeit_rot = wahrscheinlichkeit_eine_farbe(red, anzahl_kugeln)
#     wahrscheinlichkeit_blau =  wahrscheinlichkeit_eine_farbe(blue, anzahl_kugeln)
#     wahrscheinlichkeit_grun = wahrscheinlichkeit_eine_farbe(green, anzahl_kugeln)
#
#     return wahrscheinlichkeit_rot + wahrscheinlichkeit_blau + wahrscheinlichkeit_grun

def simulation_wahrscheinlichkeiten():

    kugeln = random.sample(['r', 'b', 'g'], counts=[6, 4, 6], k=3)




