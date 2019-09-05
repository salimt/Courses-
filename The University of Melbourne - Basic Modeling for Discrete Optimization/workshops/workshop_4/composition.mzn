% n = 6000 sec = 30

int: len;
set of int: SET = 1..len;
set of int: SET1 = 1..len-1;
array[SET] of var SET: order;
array[SET1] of var SET1: diffs;

include "alldifferent.mzn";
constraint alldifferent(order);
constraint alldifferent(diffs);

constraint forall(i in 1..len-1)(diffs[i] = abs(order[i]-order[i+1]));

solve satisfy;

output[if i == len then "\(order[i]) \n" else "\(order[i]) " endif | i in SET];
output[" \(i)" | i in diffs];