
int: n;                                 % The number of queens.

array [1..n] of var 1..n: q;

include "alldifferent.mzn";

constraint alldifferent(q);
constraint alldifferent(i in 1..n)(q[i] + i);
constraint alldifferent(i in 1..n)(q[i] - i);


solve 
%      :: int_search(q, input_order, indomain_min, complete) 
%      :: int_search(q, first_fail, indomain_min, complete) 
%      :: int_search(q, smallest, indomain_min, complete)
%      :: int_search(q, largest, indomain_min, complete)
%      :: int_search(q, input_order, indomain_max, complete) 
%      :: int_search(q, first_fail, indomain_max, complete) 
%      :: int_search(q, smallest, indomain_max, complete)
%      :: int_search(q, largest, indomain_max, complete)
%      :: int_search(q, input_order, indomain_median, complete) 
%      :: int_search(q, first_fail, indomain_median, complete) 
%      :: int_search(q, smallest, indomain_median, complete)
%      :: int_search(q, largest, indomain_median, complete)
%      :: int_search(q, input_order, indomain_random, complete) 
%      :: int_search(q, first_fail, indomain_random, complete) 
%      :: int_search(q, smallest, indomain_random, complete)
%      :: int_search(q, largest, indomain_random, complete)
      satisfy;

%output	
%%	[	if fix(q[i]) = j then "Q " else ". " endif ++
%	 	if j = n then "\n" else "" endif
%	|	i, j in 1..n
%	];
