palindrome(List) :- reverse(List, List).

revers([], []).
revers([Head|Tail], Reversed) :-
    revers(Tail, ReversedTail),
    append(ReversedTail, [Head], Reversed).


%(ex1).
last_element([X], X).
last_element([_|T], X) :-
    last_element(T, X).

%(ex2)
second_last_element([X,_], X).
second_last_element([_|T], X) :-
    second_last_element(T,X).

%(ex3)
k_element([],0,_).
k_element([X|_], 1, X).
k_element([_|T], K, X) :-
        K1 is K - 1,
    k_element(T, K1, X).

    




%(ex4)
len_of_list([], 0).
len_of_list([_|T], X) :-
    len_of_list(T, X1),
    X is X1+1.

%(ex5)
umkehren(List,Result) :-
    reverse(List,Result).

%(ex7)
duplicate_all([],[]).
duplicate_all([H|T], [H,H|R]) :- 
    duplicate_all(T, R).

%(ex8)
is_bigger_or_equal(G1, G2) :-
    G1 =< G2.

range(G1,G2,[]).
range(G1,G2,[G1|R]) :-
    is_bigger_or_equal(G1,G2),
    G3 is G1 + 1,
    range(G3,G2,R).
























numeral(0).
numeral(succ(X)) :- numeral(X).

