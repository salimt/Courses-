array[1..10] of var 1..10:x;
include "alldifferent.mzn";
constraint alldifferent(x);
constraint forall(i in 2..10)(x[i] <= 9);
constraint x[1] >= 9;

solve :: int_search(x, input_order, indomain_min, complete)
      satisfy;

