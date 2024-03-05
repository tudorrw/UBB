def validate(s):
    try:
        county, number, strings = s.split('-')
        if county and number and strings:
            try:
                c = list(county)
                n = list(number)
                s = list(strings)
                print(c, n, s)
                # if c[0].isalpha() and c[1].isalpha() and n[0].isdigit() and n[1].isdigit() and s[0].isalpha() and s[1].isalpha() and s[2].isalpha() and len(c) == 2 and len(n) == 2 and len(s) == 3:
                if county[0:2].isalpha() and number[0:2].isdigit() and strings[0:3].isalpha() and len(county) == 2 and len(number) == 2 and len(strings) == 3:
                    return True
                return False
            except IndexError:
                return False
    except ValueError:
        return False



print(validate('AB-15ASV'))

from functools import reduce
def PigLatin(filename):
    with open(filename , 'r') as file:
        data = file.read()

    str_lst = list(map(lambda x: x.split(','), data.split('\n')))

    sentences = reduce(lambda x, y: x + y, str_lst)
    string = list(map(lambda x: x.split(' '), sentences))
    change_sentences = list(map(lambda sentence: list(map(lambda word: word[1:] + word[0] + 'ay', sentence)) ,string))
    print_text = map(lambda sentence:  ' '.join(sentence) ,change_sentences)
    return '\n'. join(print_text)

    # for line in sentences:
    #     sentence = line.split(' ')
    #     sentence = list(map(lambda x: x[1:] + x[0] + 'ay', sentence))
    #     # print(' '.join(sentence))
print(PigLatin('datei.txt'))

def PigLatin2(filename):
    with open(filename , 'r') as file:
        data = file.read()
    return '\n'. join(map(lambda sentence: ' '.join(sentence),list(map(lambda sentence: list(map(lambda word: word[1:] + word[0] + 'ay', sentence)) ,list(map(lambda x: x.split(' '), reduce(lambda x, y: x + y, list(map(lambda x: x.split(','), data.split('\n'))))))))))
print(PigLatin2('datei.txt'))