# 2 Funktionen erzeungen: 
# eine die die Datei einliest und ein List zuruckgibt 
# andere die in der Variable content die angepasst List eingibt und in der Datei schreibt
def read_file():
    with open('meine_datei.txt', 'r') as file:
        f = file.read()
    return f



def write_file(word_to_replace, replaced_to,f):
    
    with open('meine_datei.txt', 'w') as file:
        c = 0
        while word_to_replace in f:
            c += 1
            f = f.replace(word_to_replace, replaced_to, 1)
        file.write(f)
    return c