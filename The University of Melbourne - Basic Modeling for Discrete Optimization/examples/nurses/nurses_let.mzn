int: k; % number of nurses
set of int: NURSE = 1..k;
int: m; % number of days
set of int: DAY = 1..m;
set of int: SHIFT = 1..3;
int: day = 1; int: night = 2; int: dayoff = 3;
array[SHIFT] of string: code = ["d", "n", "-"];
int: o; % number on day shift
int: l; % lower bound for nightshift
int: u; % upper bound for nightshift


array[NURSE,DAY] of var SHIFT: x;

constraint forall(n in NURSE, d in 1..m-2)
                 ( x[n,d] = night /\ x[n,d+1] = night 
                   -> x[n,d+2] = dayoff);

constraint forall(n in NURSE, d in 1..m-1)
                 (x[n,d] = night -> x[n,d+1] != day);

constraint forall(d in DAY)
                 (let {var int: on = sum(n in NURSE)
                                        (bool2int(x[n,d]=night))        
                      } in l <= on /\ on <= u);

solve satisfy;

output [ code[fix(x[n,d])] 
         ++ if d = m then "\n" else " " endif
       | n in NURSE, d in DAY];

