int: n;
set of int: DOM = 1..n;
int: m;
set of int: COD = 1..m;
array[DOM,COD] of int: profit;

array[COD] of var DOM: worker;

include "alldifferent.mzn";
constraint alldifferent(worker);


solve maximize sum(t in COD)
            (profit[worker[t],t]);

output [show(worker)];
