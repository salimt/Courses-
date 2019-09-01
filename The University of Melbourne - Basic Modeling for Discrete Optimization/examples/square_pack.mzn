int: n; % number of squares
set of int: SQUARE = 1..n;
int: maxl = sum(i in SQUARE)(i);
int: mina = sum(i in SQUARE)(i*i);

var n..maxl: height;
var n..maxl: width;
var mina .. n*maxl: area = height * width;

array[SQUARE] of var 0..maxl: x;
array[SQUARE] of var 0..maxl: y;

% squares fit in the rectangle
constraint forall(s in SQUARE)(x[s] + s <= width);
constraint forall(s in SQUARE)(y[s] + s <= height);

% non overlap
constraint forall(s1, s2 in SQUARE where s1 < s2)
                 (x[s1] + s1 <= x[s2] \/
                  x[s2] + s2 <= x[s1] \/  
                  y[s1] + s1 <= y[s2] \/ 
                  y[s2] + s2 <= y[s1]);
array[SQUARE] of int: size = [ i | i in SQUARE ];

% non overlap with global diffn
include "diffn.mzn";
%constraint diffn(x,y,size,size);

% redundant cumulative constraints
include "cumulative.mzn";
%constraint cumulative(x,size,size,height);
%constraint cumulative(y,size,size,width);

% variables ordered in reverse size x[n], y[n], x[n-1], y[n-1], ..., x[1], y[1]
%array[1..2*n] of var 0..maxl: vs = [ if i mod 2 = 0 then x[n+1 - i div 2] 
%                                     else y[n+1 - i div 2] endif | i in 2..2*n+1 ];

solve :: seq_search([
         int_search([area,height,width], input_order, indomain_min, complete) 
         %,int_search(vs, input_order, indomain_min, complete)
         ])
      minimize area;

output ["area = ",show(area), "\n"] ++
["height = ",show(height), "\n"] ++
["width = ",show(width), "\n"] ++
["x = ", show(x), "\n"] ++
["y = ", show(y), "\n"];
