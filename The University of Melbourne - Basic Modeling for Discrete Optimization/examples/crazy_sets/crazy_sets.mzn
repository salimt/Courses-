int: n; % maximum value
set of int: NUMBER = 1..n;
int: c; % cardinality of sets
int: m; % number of sets


array[1..m] of var set of NUMBER: s;

constraint forall(i,j,k in 1..m where i < j /\ j < k)
     ( s[i] intersect s[j] intersect s[k] = {} );


array[1..m,1..c] of var NUMBER: x;
constraint forall(i in 1..m, j in 1..c-1)
      ( x[i,j] < x[i,j+1] );


constraint forall(i in 1..m-1, j in 1..c)
      ( x[i,j] < x[i+1,j] );

constraint forall(i in 1..m, o in NUMBER)
      (o in s[i] 
       -> exists(j in 1..c)(x[i,j] = o));
constraint forall(i in 1..m, j in 1..c)(x[i,j] in s[i]);

solve satisfy;

output [show(s)];

n = 10;
c = 4;
m = 4;
