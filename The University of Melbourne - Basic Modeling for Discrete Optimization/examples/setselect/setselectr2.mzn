% setselect revised
int: n; % number of elements
int: u; % cardinality of chosen set
int: k; % number of subsets
array[1..k] of set of 1..n: s;

array[1..u] of var 1..n: x;
%constraint forall(i in 1..u-1)(x[i] < x[i+1]);
constraint forall(i,j in 1..u where i < j)(x[i] != x[j]);

constraint forall(i in 1..k)(sum(j in 1..u)(x[j] in s[i]) <= 1);

solve maximize sum(x);

output ["x = ", show(x), ";\n"];
