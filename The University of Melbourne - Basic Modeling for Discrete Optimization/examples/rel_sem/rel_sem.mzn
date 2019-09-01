var -4..4: x;
var -4..4: y;

constraint x div y >= 2;

solve satisfy;

output ["x = \(x); y = \(y);\n"];