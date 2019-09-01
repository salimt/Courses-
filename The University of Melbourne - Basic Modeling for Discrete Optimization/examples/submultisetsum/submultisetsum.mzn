% subsetsum problem
array[int] of int: number;
int: target;

set of int: NUMBER = index_set(number);
var set of NUMBER: selected;

constraint sum(i in selected)
              (number[i]) = target;
solve satisfy;

output [show(selected)];