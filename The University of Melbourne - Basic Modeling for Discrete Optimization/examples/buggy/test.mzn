

set of int: A = {1,3,5};

set of int: B = trace("setB",{2,3,6});

array[A union B] of var 0..1: x;


solve satisfy;

output [show(x)];
