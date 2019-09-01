var -4..4:x;
var -4..4:y;
constraint divp(x,y,3);

predicate divp(var int:x,var int: y,var int: z) 
     = assert(lb(y) >= 0,
              "divp: y must be non-negative",
              let { var 0..ub(y)-1: r } in
	        x = z * y + r /\ r < y);

solve satisfy;
