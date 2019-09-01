int: n; % number of objects
set of int: OBJ = 1..n;
int: capacity;
array[OBJ] of int: profit;
array[OBJ] of int: size;

var set of OBJ: x; 


constraint sum(i in x)(size[i]) <= capacity;

solve maximize sum(i in x)(profit[i]);


output ["x = ", show(x), "\n"];