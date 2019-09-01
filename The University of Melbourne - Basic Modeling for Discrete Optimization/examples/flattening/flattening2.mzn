int: i = 3; int: j = 3;
var 0..5: x; var 0..2: y; var 0..3: z;
constraint (x - i) * (x - j) + y + z + i + j >= 0;

solve satisfy;