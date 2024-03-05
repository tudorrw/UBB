def eingeben_wort():
    wort = str(input('geben Sie einen Wort: '))
    return wort

def eingeben_buchstabe():
    buchstabe = input('Geben sie ein Buchstabe: ')
    return buchstabe 

def verbleibende_Versuchen(leben):
    if leben > 0: print(f'versuchen Sie weiter. Sie haben noch {leben} Leben')   
    else: print('Du hast verloren! ')
    
def gewonnen():
    print('Du hast das Wort erraten!')
