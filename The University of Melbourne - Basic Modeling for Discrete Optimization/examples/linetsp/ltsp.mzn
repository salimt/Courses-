int: n; % number of cities
set of int: CITY = 1..n;
set of int: POS = 1..n;
array[CITY] of int: coord; % coordinate of city 
int: m; % number of precedences
set of int: PREC = 1..m;
array[PREC] of CITY: left;
array[PREC] of CITY: right;

array[CITY] of var POS: order;
array[POS] of var CITY: city;

include "inverse.mzn";
constraint inverse(order,city);

constraint forall(i in PREC)(order[left[i]] < order[right[i]]);

solve minimize sum(i in 1..n-1)(abs(coord[city[i]] - coord[city[i+1]]));


