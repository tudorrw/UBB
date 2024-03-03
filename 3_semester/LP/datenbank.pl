
% kann man den Wissenbasis (Datenbank) mit hilfe den standard Predikate assert/retract umwandeln 
:- dynamic(speise/3).

% Fakten
% speise(Name, Kategorie, Preis). 

speise(spaghetti, breakfast, 12.99).
speise(cheeseburger, lunch, 8.99).
speise(salad, dinner, 6.99).
speise('Chicken Alfredo', dinner, 15.99).
speise(burrito, lunch, 6.49).
speise(cheesecake, dessert, 5.99).


% Hinfugen 
addSpeise :- 
        write('Gib einen Namen: '),nl,
        read(Name),
        \+ speise(Name, _, _),
        write('Gib eine Kategorie (breakfast, lunch, dinner, dessert): '),nl,
        read(Kategorie),
        write('Gib ein Preis: '),nl,
        read(Preis),
        assertz(speise(Name, Kategorie, Preis)),
        format('Speise ~w in Kategorie ~w mit Preis ~w wird hinzugefügt.', [Name, Kategorie, Preis]).

addSpeise :- write('Speise mit dieses Name findet bereits in der Datenbank.').
%asserta(speise(burrito, lunch, 6.49)). 

% Lesen
getSpeisenByName :- 
        write('Gib einen Namen: '), nl,
        read(Name),            
        findall(X, speise(X, _, _), Speisen),
        member(Name, Speisen), !.
            

getAll :- 
        findall((Name, Kategorie, Preis), speise(Name, Kategorie, Preis), Speisen),
        printSpeisen(Speisen).

printSpeisen([]).
printSpeisen([(Name, Kategorie, Preis)|Tail]) :-
        format('Speise: ~w, Kategorie: ~w, Preis: ~w~n', [Name, Kategorie, Preis]),
        printSpeisen(Tail).

% Loschen
removeSpeise :- 
        write('Gib den Namen der Speise ein, der Sie loschen mochten: '),nl,
        read(Name),
        speise(Name, _, _),
        retract(speise(Name, _, _)),
        format('Speise mit der Name ~w wird geloscht.~n', [Name]).

removeSpeise :- write('Die angegebene Speise existiert nicht in der Datenbank').

clearAll :- 
        write('Sind Sie sicher, dass Sie alle Daten aus der Datenbank löschen möchten? yes/no'), nl,
        read(Answer),
        =(Answer, yes),
        retractall(speise(_, _, _)),
        write('Speisen erfolgreich gelöscht.'), !.

clearAll :- write('Der Prozess wurde abgewiesen.').

%Aktualisieren

updateKategorie :-  
        write('Gib den Namen der Speise ein, deren Kategorie aktualisiert werden soll: '),nl,
        read(Name),
        speise(Name, _, _), 
        write('Gib die neue Kategorie ein: '), nl,
        read(Kategorie),
        retract(speise(Name, _, Preis)),
        assertz(speise(Name, Kategorie, Preis)),
        format('Kategorie der Speise ~w erfolgreich auf ~w aktualisiert.~n', [Name, Kategorie]), !.

updateKategorie :-
        write('Die angegebene Speise existiert nicht.~n').

updatePreis :-
        write('Gib den Namen der Speise ein, deren Preis aktualisiert werden soll: '), nl,
        read(Name),
        speise(Name, _, _), % Überprüfen, ob die Speise existiert
        write('Gib den neuen Preis ein: '), nl,
        read(Preis),
        retract(speise(Name, Kategorie, _)), % Speise entfernen
        assertz(speise(Name, Kategorie, Preis)), % Speise mit neuem Preis hinzufügen
        format('Preis der Speise ~w erfolgreich auf ~w aktualisiert.~n', [Name, Preis]), !.

updatePreis :-
        write('Die angegebene Speise existiert nicht.~n').


updateSpeise :-
        write('Welche Eigenschaften möchten Sie aktualisieren (Kategorie/Preis)? '), nl,
        read(Eigenschaft),
        (
            Eigenschaft = kategorie,
            updateKategorie ; 
        
            Eigenschaft = preis,
            updatePreis ;

            write('Ungültige Auswahl.')
        ), !.
