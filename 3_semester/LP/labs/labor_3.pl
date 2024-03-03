hatgegessen(moskito,blut(john)).
hatgegessen(frosch,moskito).
hatgegessen(storch,frosch).

verdaut(X,Y) :-
    hatgegessen(X,Y).

verdaut(X,Y):- 
    hatgegessen(X,Z),verdaut(Z,Y). 



%(Aufgabe 1)

connected(1,2).
connected(3,4).
connected(5,6).
connected(7,8).
connected(9,10).
connected(12,13).
connected(13,14).
connected(15,16).
connected(17,18).
connected(19,20).
connected(4,1).
connected(6,3).
connected(4,7).
connected(6,11).
connected(14,9).
connected(11,15).
connected(16,12).
connected(14,17).
connected(16,19).

path(X, Y) :- connected(X, Y).

path(X, Y) :-
    connected(X, Z), path(Z, Y).



%(Aufgabe 2)

byCar(auckland,hamilton).
byCar(hamilton,raglan).
byCar(valmont,saarbruecken).
byCar(valmont,metz).

byTrain(metz,frankfurt).
byTrain(saarbruecken,frankfurt).
byTrain(metz,paris).
byTrain(saarbruecken,paris).

byPlane(frankfurt,bangkok).
byPlane(frankfurt,singapore).
byPlane(paris,losAngeles).
byPlane(bangkok,auckland).
byPlane(singapore,auckland).
byPlane(losAngeles,auckland).

travel(X, Y) :-
    byCar(X, Y) ; byTrain(X, Y) ; byPlane(X, Y).

travel(X, Y) :- 
    (byCar(X, Z) ; byTrain(X, Z) ; byPlane(X, Z)), travel(Z,Y).


%(Aufgabe 3)

et(albert,kevin).
et(lena,albert).
et(marie,lena). 

vorfahr(X,Y) :- 
    et(X,Y).

vorfahr(X,Y) :- 
    et(X,Z), vorfahr(Z,Y).



%(Aufgabe 4)

directlyin(irina, natasha).
directlyin(natasha, olga).
directlyin(olga, katarina).

in(X, Y) :- 
    directlyin(X, Y).
in(X, Y) :- 
    directlyin(X, Z), in(Z, Y).


%(Aufgabe 5)
descending_count(0) :- write(0), nl.

descending_count(X) :- 
    write(X), nl,
    Y is X - 1,
    descending_count(Y).

descending_count_to_10 :- descending_count(10).


%(Aufgabe 6)

groesser_oder_gleich(X, Y) :- 
    X >= Y.

vergleichen(X, Y) :-
    groesser_oder_gleich(X, Y), 
    write(X), write(' ist grosser oder egal als '), write(Y) ;
    write(X), write(' ist kleiner als '), write(Y).