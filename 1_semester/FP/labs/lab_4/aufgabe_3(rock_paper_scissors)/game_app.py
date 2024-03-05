# ich habe mal zwei neues python scripts und ein txt datei gestellt, wo ich einfach den code
# reingeschrieben habe und dann in den game_app file importiert habe

from random import choice
from logik import *
from drawings import *

hand_game = ['stein', 'papier', 'schere']
endgame = False
while not endgame:
    my_wins, comp_wins = 0,0
    while my_wins < 3 and comp_wins < 3:
        
        player = None
        while player not in hand_game:
            player = input('Wahlen Sie zwischen Stein, Papier oder Schere: ').lower()
        computer = choice(hand_game)
        
        print(f'my choice: {player}\n')
        drawing(player)
        print(f'computer\'s choice: {computer}\n')
        drawing(computer)

        result = rules(player, computer, my_wins, comp_wins)
        my_wins = result[0]
        comp_wins = result[1]
        print(f'Score: {my_wins} - {comp_wins}')
    
    if my_wins == 3:
        print('Du hast gewonnen!')
    else:
        print('Der Rechner hat gewonnen!')

    again = int(input('mochtest du noch einmal spielen? (0 fur nein, 1 fur ja): '))
    match again:
        case 0:
            endgame = True
        case 1:
            endgame = False 