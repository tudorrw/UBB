in_list(X, [X|_]).
in_list(X, [_|T]) :-
    in_list(X, T).

%(ex1)
differenz([],_,[]).
differenz([X|T1], L2, Result) :-
    in_list(X, L2), !,
    differenz(T1, L2, Result).

differenz([X|T1], L2, [X|Result]) :-
    differenz(T1, L2, Result).

set_differenz(L1, L2, R) :- differenz(L1, L2, R).



%(ex2)
vereinigung([],L2,L2).
vereinigung([X|T1], L2, Result) :-
    in_list(X, L2), !,
    vereinigung(T1, L2, Result).

vereinigung([X|T1], L2, [X|Result]) :-
    vereinigung(T1, L2, Result).


set_vereinigung(L1, L2, X) :- vereinigung(L1,L2,X).

%(ex3)
durchschnitt([],_,[]).
durchschnitt([H|T],L2,[H|Result]) :-
    in_list(H, L2),
    durchschnitt(T,L2,Result).

durchschnitt([H|T], L2, Result) :-
    durchschnitt(T, L2, Result).

set_durchschnitt(L1, L2, Result) :- durchschnitt(L1, L2, Result).

%(ex3 ca is bou ca ce am inteles eu ii gunoi) 
differenz2([],_,[]).
differenz2([H|T],L2,Result) :-
    in_list(H, L2), !,
    differenz2(T, L2, Result).

differenz2([H|T],L2,[H|Result]) :-
    differenz2(T, L2, Result).


append([ ], List, List).
append([Head|Tail], List, [Head|Result]):- 
    append(Tail, List, Result).

set_differenz2(L1, L2, Result) :- 
    differenz2(L1, L2, Result1),
    differenz2(L2, L1, Result2),
    append(Result1, Result2, Result).




%ex10

minimal([X], X).
minimal([Min|T], Min) :- 
	minimal(T, T_Losung),
	Min =< T_Losung, !.

minimal([H|T], Losung) :-
	minimal(T, Losung), !.


delete_in_list([], _, []).
delete_in_list([H|T],Min,R) :-
    H = Min,
    delete_in_list(T,Min,R).

delete_in_list([H|T],Min,[H|R]) :-
    H \= Min,
    delete_in_list(T,Min,R).

            
delete_minimum(L, R) :-
    minimal(L, Min),
    delete_in_list(L, Min, R).








