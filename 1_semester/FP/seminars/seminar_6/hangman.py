def new_word(wort):
    return '_' * len(wort)
    

def update_wort(newes_wort, maske, buchstabe):
    position = maske.rfind(buchstabe)
    newes_wort = newes_wort[:position] + buchstabe + newes_wort[position + 1:]
    maske = maske[:position] + '_' + maske[position + 1:]
    return newes_wort, maske