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

% only one task on any machine at one time
%constraint forall(ma in MACH)
%                 (forall(j1,j2 in JOB where j1 < j2)
%                       (forall(t1,t2 in TASK where mc[j1,t1] = ma /\ mc[j2,t2] = ma)
%                              (nonoverlap(s[j1,t1],d[j1,t1],s[j2,t2],d[j2,t2]))));

constraint forall(j1, j2 in JOB, t1, t2 in TASK 
       where j1 < j2 /\ mc[j1,t1] = mc[j2,t2])
      (nonoverlap(s[j1,t1],d[j1,t1],
                  s[j2,t2],d[j2,t2]));

predicate nonoverlap(var int:s1, var int:d1,
                     var int:s2, var int:d2)=
          s1 + d1 <= s2 \/ s2 + d2 <= s1;

var 0..maxt: makespan = max(j in JOB)(s[j,m] + d[j,m]);

solve :: int_search([s[j,t] | j in JOB, t in TASK], input_order, indomain_min, complete)
      minimize makespan;

output [ show_int(3,s[j,t]) ++ if t = m  then "\n" else " " endif
       | j in JOB, t in TASK]; 
