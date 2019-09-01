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

set of int: DIM = 1..2;
array[ROFF,DIM] of int: rect_size = 
     array2d(ROFF,DIM, [ d[r,i] | r in ROFF, i in 3..4]);
array[ROFF,DIM] of int: rect_offset = 
     array2d(ROFF,DIM, [ d[r,i] | r in ROFF, i in 1..2]);
array[int] of set of ROFF: shapes = [ {1,3,4}, {2,5,6}, {1}, {7} ];
set of int: SHAPE = index_set(shapes);
%array[int] of set of RECT: s = sort([shapes[i,j] | i in 1..n, j in 1..4]);

array[ROOM] of var SHAPE: kind;
array[ROOM,DIM] of var int: coords =
      array2d(ROOM,DIM, [ if j = 1 then x[i] else y[i] endif
                         | i in ROOM, j in 1..2 ]);



include "geost.mzn";

constraint geost_bb(2,
                    rect_size,
                    rect_offset,
                    shapes,
                    coords,
                    kind,
                    [0,0],
                    [l,h]);

var 0..maxl: l; % length of carpet used

solve minimize l;

constraint forall(i in ROOM)
                 (exists(r in ROT where shape[i,r] != {})
                        (shapes[kind[i]] = shape[i,r]));


output ["rect_size = \(rect_size);\nrect_offset = \(rect_offset);\n"] ++
       ["l = \(l);\nx = \(x);\ny = \(y);\nkind = \(kind);\n"];

