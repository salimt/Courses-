array[1..10] of var bool: x; 
var 0..5: y;
predicate atmostone(array[int] of var bool: b);
constraint y != 0 -> atmostone(x);

solve satisfy;
