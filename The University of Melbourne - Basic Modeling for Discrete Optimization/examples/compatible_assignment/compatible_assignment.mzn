int: n;
set of int: W = 1..n;
int: m;
set of int: T = 1..2*m;
array[W,T] of int: profit;
array[W,W] of bool: compatible;

array[W] of var T: task;

include "alldifferent.mzn";
constraint alldifferent(task);
constraint forall(w1, w2 in W)
                 (task[w1] != m /\ task[w2] = task[w1] + 1 ->
                   compatible[w1,w2]);

solve maximize sum(w in W)(profit[w,task[w]]);

output [ "task = \(task)\n" ] ;