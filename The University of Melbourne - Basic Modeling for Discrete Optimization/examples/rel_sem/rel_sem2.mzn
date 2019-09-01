array[1..2] of var 0..1: x;
var 1..3: i;

constraint i <= 2 -> x[i] >= 1;

output ["i  = \(i); x = \(x);\n"];

solve satisfy;