var 0..5: x; var 0..2: y; var 0..3: z;
int: k = 4;
constraint x + 2*(y - x) + z <= k*z;

solve satisfy;