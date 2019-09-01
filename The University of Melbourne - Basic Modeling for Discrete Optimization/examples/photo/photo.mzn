int:n ;
set of int: PERSON = 1..n;
set of int: POS = 1..n;
array[PERSON,PERSON] of int: friend;

%array[PERSON] of var POS: x;
array[POS] of var PERSON: y;

%include "inverse.mzn";
%constraint inverse(x,y);

include "alldifferent.mzn";
constraint alldifferent(y);

%constraint alldifferent(x);

%y[i] = sum(j in 1..n)(bool2int(x[j] = i) * j);

solve maximize sum(i in 1..n-1)(friend[y[i],y[i+1]]);

