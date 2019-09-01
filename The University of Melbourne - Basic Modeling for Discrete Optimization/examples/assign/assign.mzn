int: n;
set of int: DOM = 1..n;
int: m;
set of int: COD = 1..m;
array[DOM,COD] of int: profit;

array[DOM] of var COD: task;

include "alldifferent.mzn";
constraint alldifferent(task);


solve maximize sum(w in DOM)
            (profit[w,task[w]]);

output [show(task)];
