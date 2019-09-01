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

include "alldifferent.mzn";
constraint alldifferent([r[p] * m + c[p] | p in PRISONER]);

function var int: 
     manhattan(var int:x1, var int:y1,
               var int:x2, var int:y2) =
     abs(x2 - x1) + abs(y2 - y1);

constraint forall(p in PRISONER, d in danger where p != d) 
                 (manhattan(r[p],c[p],r[d],c[d]) > 1);

constraint forall(p in female)(r[p] <= (n + 1) div 2);
constraint forall(p in male)(r[p] >= n div 2 + 1);

var int: totalcost = sum(p in PRISONER)(cost[r[p],c[p]]);  
solve minimize totalcost;
 
output ["cost = \(totalcost)\n"] ++
       [ "Prisoner \(p) " ++ if p in female then "(F)" else "(M)" endif ++ 
         " in [\(r[p]),\(c[p])].\n"
       | p in PRISONER ];
