% setselect revised
int: n; % number of elements
int: u; % cardinality of chosen set
int: k; % number of subsets
array[1..k] of set of 1..n: s;

var set of 1..n: x;

constraint forall(i in 1..k)(card(x intersect s[i]) <= 1);
constraint card(x) = u;

solve maximize sum(i in x)(i);

output ["x = ", show(x), ";\n"];
