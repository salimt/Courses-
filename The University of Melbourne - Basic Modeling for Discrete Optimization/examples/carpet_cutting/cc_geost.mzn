int: n; % number of rooms
set of int: ROOM = 1..n;
int: m; % number of rectangle/offsets
set of int: ROFF = 1..m;
array[ROFF,1..4] of int: d; % defns
set of int: ROT = 1..4;
array[ROOM,ROT] of set of ROFF: shape;
int: h; % height of roll
int: maxl; % maximum length of roll

n = 3; m = 7;
d = [| 0,0,3,4  % (xoffset,yoffset,xsize,ysize)
    | 0,1,4,3
    | 1,4,1,1
    | 3,1,1,2
    | 4,2,1,1
    | 1,0,2,1 
    | 0,0,4,3 |];
shape = [| {1,3,4}, {2,5,6}, {}, {}
         | {1,3,4}, {2,5,6}, {}, {}
         | {1}, {7}, {}, {}  |];
h = 7; maxl = 12;


array[ROOM] of var 0..maxl: x;
array[ROOM] of var 0..h: y;

set of int: SHAPE = 1..4;
array[ROOM] of var SHAPE: kind;

constraint geost_bb(2,
       [| 3,4 | 4,3 | 1,1 | 1,2 | 1,1 | 2,1 | 4,3 |],
       [| 0,0 | 0,1 | 1,4 | 3,1 | 4,2 | 1,0 | 0,0 |],
       [ {1,3,4}, {2,5,6}, {1}, {7} ],
       [| x[1], y[1] | x[2],y[2] | x[3],y[3] |],
       kind,
       [ 0,0 ],
       [ l, h]);

constraint kind[1] in {1,2};
constraint kind[2] in {1,2};
constraint kind[3] in {3,4}; 
include "geost.mzn";

var 0..maxl: l; % length of carpet used

solve minimize l;

output ["l = \(l);\nx = \(x);\ny = \(y);\nkind = \(kind);\n"];

