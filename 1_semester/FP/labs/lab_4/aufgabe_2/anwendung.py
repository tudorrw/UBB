from read_and_write import *

def main():
    word_to_replace = str(input('Wort zu ersetzen: ')).lower()
    replaced_to = str(input('Ersatzwort: ')).lower()

    f = read_file()
    write_file(word_to_replace, replaced_to,f)
main()