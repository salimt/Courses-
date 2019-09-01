var -2..2: x; var -1..2: y; 
var -2..2: z; var -1..2: u; var 0..3: t;
constraint x <= 0 \/ bool2int(y > 0 /\ z > 0) + t >= u;

solve satisfy; 