int: n;
set of int: WORK = 1..n;
int: m;
set of int: TASK = 1..m;
array[WORK,TASK] of int: profit;
array[WORK] of var TASK: x;
% new
array[WORK] of var int: px =
     [ profit[w,x[w]] | w in WORK ];
var int: obj = sum(w in WORK)(px[w]);

include "alldifferent.mzn";
constraint alldifferent(x);

ann: varselect;
ann: valselect;

solve :: int_search(px, varselect, valselect, complete)
      maximize obj;

output ["obj = \(obj); x = \(x);\n"];


varselect = largest;
valselect = indomain_min;
