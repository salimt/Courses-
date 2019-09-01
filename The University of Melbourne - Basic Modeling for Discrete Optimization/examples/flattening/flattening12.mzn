array[1..2] of var 0..25: a;

constraint not (8 >= sum(i in 1..2)(sqrt(a[i])));
function var int:sqrt(var int: x) :: promise_total =
    let { var int: y; 
    constraint y * y = x /\ y >= 0 } in y;

solve satisfy;