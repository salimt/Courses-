int: n;
set of int: JOB = 1..n;
int: m;
set of int: MACH = 1..m;
set of int: TASK = 1..m;
array[JOB,TASK] of int:  d;  % length of task
array[JOB,TASK] of MACH: mc; % which machine

int: maxt = sum(j in JOB, t in TASK)(d[j,t]);

array[JOB,TASK] of var 0..maxt: s; % start time

% performs tasks in a job in order
constraint forall(j in JOB, t in 1..m-1)
                 (s[j,t] + d[j,t] <= s[j,t+1]);

include "disjunctive.mzn";

constraint forall(ma in MACH)
                 ( let { array[int] of var int: ss = [ s[j,t] | j in JOB, t in TASK where mc[j,t] = ma ];
                         array[int] of int:     dd = [ d[j,t] | j in JOB, t in TASK where mc[j,t] = ma ]; } in
                   disjunctive(ss,dd));


var 0..maxt: makespan = max(j in JOB)(s[j,m] + d[j,m]);

solve  
   %:: int_search([s[j,t]|j in JOB,t in TASK],input_order,indomain_min,complete)
   %:: int_search([s[j,t]|j in JOB,t in TASK],smallest, indomain_min, complete)
   %:: int_search([s[j,t]|j in JOB,t in TASK],dom_w_deg, indomain_min, complete)
         minimize makespan;

output [ "makespan = ", show(makespan), "\n"] ++
       [ show_int(3,s[j,t]) ++ if t = m  then "\n" else " " endif
       | j in JOB, t in TASK]; 
