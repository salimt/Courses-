int: k;
set of int: PRISONER = 1..k;
int: n;
set of int: ROW = 1..n;
int: m;
set of int: COL = 1..m;
set of PRISONER: danger;
set of PRISONER: female;
set of PRISONER: male = PRISONER diff female;
array[ROW,COL] of int: cost;


array[PRISONER] of var ROW: r;
array[PRISONER] of var COL: c;

%constraint forall(p1, p2 in PRISONER where p1 < p2)
%                 (abs(r[p1] - r[p2]) + abs(c[p1] - c[p2]) > 0); 
include "alldifferent.mzn";
constraint alldifferent([r[p] * m + c[p] | p in PRISONER]);

constraint forall(p in PRISONER, d in danger where p != d)
                 ( abs(r[p] - r[d]) + abs(c[p] - c[d]) > 1 );

constraint forall(p in female)(r[p] <= (n + 1) div 2);
constraint forall(p in male)(r[p] >= n div 2 + 1);

var int: totalcost = sum(p in PRISONER)(cost[r[p],c[p]]);  
solve minimize totalcost;
 
output ["cost = \(totalcost)\n"] ++
       [ "Prisoner \(p) " ++ if p in female then "(F)" else "(M)" endif ++ 
         " in [\(r[p]),\(c[p])].\n"
       | p in PRISONER ];
