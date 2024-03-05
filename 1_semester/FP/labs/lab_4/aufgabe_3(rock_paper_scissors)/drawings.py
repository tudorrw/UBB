# Bei jedem Aufruf der Funktion wird die Datei mit dem Symbolzeichnungen geöffnet
def drawing(choice):
    with open('rps_game.txt') as file:
        if choice == 'stein':
            list_stein = file.readlines()[0:6] # liest zwischen die Reihen 0 - 6 
            for line in list_stein:
                print(line.rstrip())
        
        elif choice == 'papier':
        
            list_papier = file.readlines()[7:13]
            for line in list_papier:
                print(line.rstrip())

        elif choice == 'schere':
            list_schere = file.readlines()[14:20]
            for line in list_schere:
                print(line.rstrip())


# das ist die Datei, die die Zeichen enthalt. Jede Zeichnung wird in Terminal zur Aufforderung der Benutzer angezeigt werden 