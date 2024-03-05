# 2 Funktionen
# update_list erstellt eine neue List, wo es die angeforderten Worter ersetzt und auch sie zuruckgibt
# count_word berechnet wie oft die angefordeten Wort in word_list erscheint  

def update_list(word_list, word_to_replace, replaced_to):
    new_word_list = [replaced_to if word == word_to_replace else word for word in word_list]
    print(new_word_list)
    return new_word_list

def count_word(word_list, word_to_replace):
        return word_list.count(word_to_replace)
        

