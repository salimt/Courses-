% Langfords problem (m,n) m copies of digits 1..n
% where any two consecutive copies of digit k
% are separated by k other digits
int: n;
set of int: DIG = 1..n;
int: m;
set of int: COPY = 1..m;
int: l = m*n;
set of int: POS = 1..l;
array[DIG,COPY] of var POS: x;

include "alldifferent.mzn";
constraint alldifferent([x[d,c] | d in DIG, c in COPY]);

constraint forall(d in DIG, c in 1..m-1)
                 (x[d,c+1] = x[d,c] + d + 1);

solve satisfy;

output [show(x),"\n"]
       ++ [ if fix(x[d,c]) = p then show(d) ++ " " else "" endif
          | p in POS, d in DIG, c in COPY ]
       ;
