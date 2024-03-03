maennlich('Arthur Weasley').
maennlich('Ron Weasley').
maennlich('Hugo Weasley').
maennlich('Harry Potter').
maennlich('James Sirius Potter').
maennlich('Albus Severus Potter').

weiblich('Molly Prewett').
weiblich('Ginny Weasley').
weiblich('Hermione Granger').
weiblich('Rose Weasley').
weiblich('Lily Luna Potter').

vater('Arthur Weasley', 'Ron Weasley').
vater('Arthur Weasley', 'Ginny Weasley').
vater('Ron Weasley', 'Rose Weasley').
vater('Ron Weasley', 'Hugo Weasley').
vater('Harry Potter', 'James Sirius Potter').
vater('Harry Potter', 'Albus Severus Potter').
vater('Harry Potter', 'Lily Luna Potter').

mutter('Molly Prewett', 'Ron Weasley').
mutter('Molly Prewett', 'Ginny Weasley').
mutter('Hermione Granger', 'Rose Weasley').
mutter('Hermione Granger', 'Hugo Weasley').
mutter('Ginny Weasley', 'James Sirius Potter').
mutter('Ginny Weasley', 'Albus Severus Potter').
mutter('Ginny Weasley', 'Lily Luna Potter').

tochter(X, Y):- (mutter(Y, X); vater(Y, X)), weiblich(X).
sohn(X, Y):- mutter(Y, X); vater(Y, X), maennlich(X).
schwester(X, Y):- mutter(Z, X), mutter(Z, Y), weiblich(X), X =\= Y.
bruder(X, Y):- mutter(Z, X), mutter(Z, Y), maennlich(X), X\=Y.
grossvater(X, Y):- vater(X, Z), (vater(Z, Y); mutter(Z, Y)).



