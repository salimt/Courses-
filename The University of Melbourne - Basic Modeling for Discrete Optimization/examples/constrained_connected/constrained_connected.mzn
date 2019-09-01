% constrained clustering 
% Pick a maximal subset of edges so that no two nodes
% given by the pairs (first[i],second[i]) 
% are connected.

int: n;
set of int: NODE = 1..n;
int: m;
set of int: EDGE = 1..m;
array[EDGE] of NODE: pre;
array[EDGE] of NODE: suc;
int: d;
set of int: DPAIR = 1..d;
array[DPAIR] of NODE: first;
array[DPAIR] of NODE: second;

var set of EDGE: selected;
% a representative for each cluster
array[NODE] of var NODE: rep;

%constraint forall(e in selected)
%                 (rep[pre[e]] = rep[suc[e]]);
constraint forall(e in EDGE)
                 (e in selected -> rep[pre[e]] = rep[suc[e]]);

constraint forall(d in DPAIR)
                 (rep[first[d]] != rep[second[d]]);

solve maximize card(selected);

output [ "(" ++ show(pre[e]) ++ "," ++ show(suc[e]) ++ ")\n"
       | e in fix(selected) ]
       ++ [show(rep),"\n"]
       ;



                 
