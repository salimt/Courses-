int: n; % points to be clustered
set of int: POINT = 1..n;
array[POINT,POINT] of int: dist; % distance between two points
int: maxdist = max([ dist[i,j] | i,j in POINT]);


int: k; % number of clusters
set of int: CLUSTER = 1..k;

int: maxdiam;

array[POINT] of var CLUSTER: x;

constraint forall(i,j in POINT where i < j /\ x[i] = x[j])
                 (dist[i,j] <= maxdiam);

constraint forall(i in 1..k-1)
                 (  min([ j | j in POINT where x[j] = i])
                  < min([ j | j in POINT where x[j] = i+1]));

var 0..maxdist: obj = min( i,j in POINT where i < j )
                         ( dist[i,j] + maxdist * (x[i] != x[j]) );

include "value_precede_chain.mzn";
constraint value_precede_chain([ i | i in 1..k ],x);

solve maximize obj;

output ["obj = \(obj); x = \(x)\n"];
