int: n; % number of objects
int: capacity;
array[1..n] of int: profit;
array[1..n] of int: size;

var set of 1..n: x;

constraint sum(i in 1..n)(size[i] * bool2int(i in x)) <= capacity;
solve maximize sum(i in 1..n)(profit[i] * bool2int(i in x));

output ["x = ", show(x), "\n"];
