int: n; % number of objects
int: capacity;
array[1..n] of int: profit;
array[1..n] of int: size;

array[1..n] of var 0..1: x; 

constraint sum(i in 1..n)(size[i] * x[i]) <= capacity;
solve maximize sum(i in 1..n)(profit[i] * x[i]);

output ["x = ", show(x), "\n"];
