
include "alldifferent.mzn";
predicate all_different_int_reif(
      array[int] of var int: a, var bool: b) = 
 b <-> forall(i, j in index_set(a) where i < j)
             (a[i] != a[j]);

var 0..3: x; var 0..3: y; var 0..3: z; var 0..3: t;
constraint alldifferent([x,y,z]);
constraint alldifferent([y,z,t]) -> x = 0;

solve satisfy;

