array[1..10] of var 1..10:x;

constraint forall(i,j in 1..10 where i < j)
                 (x[i] != x[j]);
constraint forall(i in 2..10)(x[i] <= 9);
constraint x[1] >= 9;

solve :: int_search(x, input_order, indomain_min, complete)
      satisfy;

