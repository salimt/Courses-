int: n; % number of objects
int: capacity;
array[1..n] of int: profit;
array[1..n] of int: size;

array[1..n] of var bool: x; 

constraint sum(i in 1..n)(size[i] * bool2int(x[i])) <= capacity;
solve maximize sum(i in 1..n)(profit[i] * bool2int(x[i]));

output ["x = ", show(x), "\n"];
