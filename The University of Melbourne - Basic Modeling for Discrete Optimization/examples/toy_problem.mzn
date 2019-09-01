var 0..6000: B;
var 0..4000: T;

constraint 140*B+200*T <= 40*200*140;

solve maximize 25*B + 30*T;

output  ["B=\(B) T=\(T)\n"];
