function var int: myabs(var int: x) 
         :: promise_total =
         let { var int: y; 
               constraint int_abs(x,y) } in y;

predicate int_abs(var int: x, var int: y) = 
          y >= x /\ y >= -x /\ (y <= x \/ y <= -x); 

var -5..6: a;
var 2..5: b;

constraint myabs(a) > 4 -> b < 4;

solve satisfy;

