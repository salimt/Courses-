array[1..15] of var 0..1: b;
array[1..4] of var 1..4: x;

include "alldifferent.mzn";
constraint alldifferent(x);
constraint sum(x) <= 9;

solve  
       :: int_search(b ++ x, first_fail, indomain_min, complete)
   %   :: int_search(b ++ x, dom_w_deg, indomain_min, complete)
      satisfy;


