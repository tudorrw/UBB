# hier drin habe ich ein funktion geschrieben, die die folgenden Gleichungen uberpruft,
# also diese darstellt die Regeln des Spiels und aufruft auch die Anzahl von Punkten, die 
# jede Spieler erhaltet
# diese datei wird in den hauptsachlich datei importiert

def rules(player, computer, my_wins, comp_wins):
    #wenn beide Antworten gleich sind, die Werte von my_wins and comp_wins bleiben dieselbe
    if player == computer:  
        pass 
    # wenn diese Falle auftretten, my_wins wird inkremertiert 
    elif ((player == 'stein' and computer == 'schere') or 
        (player == 'papier' and computer == 'stein') or
        (player == 'schere' and computer == 'papier')):
            my_wins += 1
    # andererseits wenn der Rechner gewinnt, comp_wins wird inkrementiert
    else: 
        comp_wins += 1
    #die beide Variablen wird aufruft
    return(my_wins, comp_wins)