array[1..10] of var 1..10: a;
solve satisfy;
output [show(a),"\n"];

include "var_sym.mzn";
constraint var_sym(a);

include "val_sym.mzn";
constraint val_sym(a,[i | i in 1..10]);