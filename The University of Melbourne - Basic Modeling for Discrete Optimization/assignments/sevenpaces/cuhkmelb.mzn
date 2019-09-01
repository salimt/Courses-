var 1..9: C;
var 0..9: U;
var 0..9: H;
var 0..9: K;
var 1..9: M;
var 0..9: E;
var 1..9: L;
var 0..9: B;
var 0..9: O;
var 0..9: R;

constraint    
           1000 * C + 100 * U + 10 * H + K
         + 1000 * M + 100 * E + 10 * L + B
         = 1000 * L + 100 * O + 10 * R + E; 

include "alldifferent.mzn";
constraint alldifferent([C,U,H,K,M,E,L,B,O,R]);

solve satisfy;

