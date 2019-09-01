var -2..2: x;
var 0..4: y;
constraint x * x + y * y <= 6;

solve satisfy;