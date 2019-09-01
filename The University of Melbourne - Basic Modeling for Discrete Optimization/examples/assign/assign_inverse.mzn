int: n;
set of int: DOM = 1..n;
int: m;
set of int: COD = 1..m;
array[DOM,COD] of int: profit;

array[DOM] of var COD: task;
array[COD] of var DOM: worker;

include "inverse.mzn";
constraint inverse(task,worker);


solve maximize sum(w in COD)
            (profit[w,task[w]]);

output [show(task)];
