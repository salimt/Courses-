var 1..9: S;
var 0..9: E;
var 1..9: V;
var 0..9: N;
var 1..9: P;
var 0..9: A;
var 0..9: C;
var 0..9: R;


constraint    
           10000 * S + 1000 * E + 100 * V + 10 * E + N
         + 10000 * P + 1000 * A + 100 * C + 10 * E + S
         = 10000 * V + 1000 * E + 100 * R + 10 * S + E; 

include "alldifferent.mzn";
constraint alldifferent([S,E,V,N,P,A,C,R]);

constraint V >= E;
constraint E >= R;

solve maximize sum(letter in [V,E,R,S]) (letter);