predicate even(var int:x) = 
          let { var int: y } in x = 2*y;

var -1..7: u;

constraint not even(u); 

solve satisfy;

output [ show(u) ];
