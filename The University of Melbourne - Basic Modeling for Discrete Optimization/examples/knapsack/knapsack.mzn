int: n; % number of objects
set of int: OBJ = 1..n;
int: capacity;
array[OBJ] of int: profit;
array[OBJ] of int: size;

array[OBJ] of var int: x; % how many of each object

constraint forall(i in OBJ)(x[i] >= 0);
constraint sum(i in OBJ)(size[i] * x[i]) <= capacity;
solve maximize sum(i in OBJ)(profit[i] * x[i]);

output ["x = ", show(x), "\n"];
