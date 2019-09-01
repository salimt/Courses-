int: n; % maximum value
set of int: NUMBER = 1..n;
int: c; % cardinality of sets
int: m; % number of sets


array[1..m,1..c] of var NUMBER: x;
constraint forall(i in 1..m, j in 1..c-1)
      ( x[i,j] < x[i,j+1] );


constraint forall(i in 1..m-1, j in 1..c)
      ( x[i,j] < x[i+1,j] );

include "global_cardinality_low_up.mzn";
constraint global_cardinality_low_up( [ x[i,j] | i in 1..m, j in 1..c ],
                              [ i | i in 1..n ],
                              [ 0 | i in 1..n ],
                              [ 2 | i in 1..n ]);


solve satisfy;

   output [ if j = 1 then "{" else "" endif ++
   show(x[i,j])
   ++ if j = c then "}" else ", " endif
   ++ if j = c /\ i < m then ", " else "" endif
   | i in 1..m, j in 1..c ];
   

n = 10;
c = 4;
m = 4;
