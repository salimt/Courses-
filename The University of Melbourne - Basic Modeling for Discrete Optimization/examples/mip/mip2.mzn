var float: B;
var float: C;
var float: D;


solve maximize 1.0*B - 1.0*C + 1.0*D;
constraint 2.0*B - 1.0*C + 2.0*D <=  4.0;
constraint 2.0*B - 3.0*C + 1.0*D <= -5.0;
constraint -1.0*B + 1.0*C - 2.0*D <= -1.0;

constraint B >= 0 /\ C >= 0 /\ D >= 0