function var int:mydiv(var int: x, var int: y)=
         assert(lb(x) >= 0 /\ lb(y) >= 0,
         "mydiv called with negative arguments",
         let { constraint y != 0 } in
         safediv(x,y));
function var int:safediv(var int: x, var int: y)
         :: promise_total =
         let { var 0..ub(y)-1: r;
               var 0..ub(x): z;
               constraint x = y * z + r;
               constraint r < y } in
         z;

var 0..5: a;
var 0..5: b;
int: c = 1;

constraint not (c = mydiv(a,b));

solve satisfy;

output ["\(c) != mydiv(\(a),\(b))\n"];
