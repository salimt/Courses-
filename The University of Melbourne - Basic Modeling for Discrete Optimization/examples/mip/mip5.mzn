solve maximize 8*x + 5*y;

var int: x;
var int: y;
constraint x + y <= 6;
constraint 9*x + 5*y <= 45;
constraint x >= 0 /\ y >= 0; 
