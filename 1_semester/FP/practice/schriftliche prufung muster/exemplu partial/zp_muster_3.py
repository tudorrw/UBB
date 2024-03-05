from functools import reduce 
class Schokolade:
    def __init__(self, name, preis):
        self.name = name
        self.preis = preis
    
def sum_preise(lst_shoko):
    # milka_schoko = list(filter(lambda x: x.name.lower() == 'milka' and x.preis >= 10, lst_shoko))
    # return reduce(lambda x, y: x + y.preis, milka_schoko, 0)
    return reduce(lambda x, y: x + y.preis, list(filter(lambda x: x.name.lower() == 'milka' and x.preis >= 10, lst_shoko)),0)
        
def main():
    Liste = [Schokolade("milka", 10), Schokolade("milka", 20), Schokolade("laura", 5), Schokolade("Milka", 7),Schokolade("kandia",10)]    
    print(sum_preise(Liste))


main()








def encode(code):
    zeichenfolge = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
    reihe = i = 0
    while i < len(code):
        if code[i] == '>':
            reihe += 1
        elif code[i] == '<':
            reihe -= 1
        elif code[i] == '+':
            try:
                try:
                    for j in range(int(code[i+1])):
                        print(zeichenfolge[reihe], end = '')
                except ValueError:
                    print(zeichenfolge[reihe], end = '')
            except IndexError:
                print(zeichenfolge[reihe], end = '')
        elif code[i] == '|':
            zeichenfolge = zeichenfolge[:reihe] + zeichenfolge[reihe +1:]     
        elif code[i] == '$':
            pass
        i += 1

def main2():
    code = str(input('Geben Sie ein Geheimcode: '))
    encode(code)

# main2()