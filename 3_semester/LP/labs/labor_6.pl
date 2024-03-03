%(ex 11 a)
is_prime(N) :-
    N > 1,
    is_prime_helper(N,2).

is_prime_helper(N, Start) :-
    Start > sqrt(N), !.

is_prime_helper(N, Start) :-
    N mod Start =\= 0,
    NextStart is Start + 1,
    is_prime_helper(N, NextStart).

duplicate([],[]).
duplicate([H|L],[H,H|R]) :-
    is_prime(H),
    duplicate(L,R).

duplicate([H|L],[H|R]) :-
    duplicate(L,R).

%(ex 11 b)

duplicate_in_sublist([],[]).
duplicate_in_sublist([H|L],[NewList|R]) :-
    is_list(H),
    duplicate(H,NewList),
    duplicate_in_sublist(L,R).

duplicate_in_sublist([H|L],[H|R]) :-
    duplicate_in_sublist(L,R).



%(ex 13a)
divisors(N, R) :-
    N > 1,
    divisors_helper(N, 2, R).

divisors_helper(N, Start, []) :-
    Start > N/2, !.

divisors_helper(N, Start, [Start|R]) :-
    N mod Start =:= 0,
    NextStart is Start + 1,
    divisors_helper(N, NextStart, R).

divisors_helper(N, Start, R) :-
    N mod Start =\= 0,
    NextStart is Start + 1,
    divisors_helper(N, NextStart, R).

add_divisors([],[]).
add_divisors([H|T], [H | DivisorsR]) :- 
    divisors(H, Divisors),
    append(Divisors, R, DivisorsR),
    add_divisors(T, R).

add_divisors_in_sublist([],[]).
add_divisors_in_sublist([H|T],[NewList|R]) :-
    is_list(H),
    add_divisors(H, NewList),
    add_divisors_in_sublist(T, R).

add_divisors_in_sublist([H|T],[H|R]) :-
    add_divisors_in_sublist(T, R).