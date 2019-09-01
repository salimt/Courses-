var int: B;
var int: C;
var int: D;

constraint 2.0*B + 2.0*C + 1.0*D <= 30.0;
constraint 1.5*B + 2.0*C + 3.0*D <= 25.0;
constraint 2.0*B + 1.0*C + 1.0*D <= 20.0;
solve maximize 3.0*B + 1.0*C + 3.0*D;

constraint B >= 0 /\ C >= 0 /\ D >= 0