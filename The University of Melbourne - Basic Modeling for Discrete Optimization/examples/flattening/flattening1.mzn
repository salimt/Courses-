int: i = 3; int: j = 2;
var int: x; var 0..2: y; var 0..3: z;
constraint x*y + y*z <= i*j;

solve satisfy;