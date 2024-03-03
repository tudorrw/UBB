% inorder-Traversierung
inorder(nil, []).
inorder(node(L, V, R), X) :- 
    inorder(L, L1), %1. der linke Teilbaum traversieren
    inorder(R, R1), %3. der rechte Teilbaul traversieren
    append(L1, [V|R1], X). % der Wert des aktuellen Knotens hinzugefugt

% inorder(node(node(nil,2,nil),5,node(nil,8,nil)), X).
%    5
%   / \
%  2   8


% inorder(node(node(node(nil,1,node(node(nil,2,nil),3,nil)),4,nil), 5, node(nil,6,nil)),X).

%           5
%          / \  
%         4   6
%        / \   
%       /
%      1
%       \ 
%        \
%         3
%        /
%       2