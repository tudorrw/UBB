from console import *
from hangman import *

def spiel(wort):
    gefundet = False
    newes_wort = new_word(wort)
    maske = wort
    leben = 6
    while not gefundet and leben > 0:

        buchstabe = eingeben_buchstabe()

        if buchstabe not in maske:
            leben -= 1
            verbleibende_Versuchen(leben)
        else:
            while buchstabe in maske:
                newes_wort, maske = update_wort(newes_wort, maske, buchstabe)
            print(newes_wort)

        if str(newes_wort) == str(wort):
            gewonnen()
            gefundet = True

def main():
    wort = eingeben_wort()
    spiel(wort)
    
main()