set of int: INDEX = 1..3;
set of int: MODEL = 1..5;
int: mazda6 = 1;
int: vwbug = 2;
int: mini = 3;
int: porsche = 4;
int: landrover = 5;
set of int: YEAR = 1900..2100;
set of int: COLOR = 1..6;
int: red = 1;
int: yellow = 2;
int: black = 3;
int: grey = 4;
int: white = 5;
int: blue = 6;
set of int: DOOR = 1..7;
set of int: PRICE = 0..100000;

% cars database: MAKE * YEAR * COLOR * DOOR * PRICE
array[INDEX,1..5] of int: 
cars = [| mazda6, 2003, red,  5, 12000
        | vwbug,  2009, blue, 3,  8000
        | mini,   2014, grey, 4, 10000
        |];

var MODEL: model;
var YEAR:  year;
var COLOR: color;
var DOOR:  doors;
var int:   cost;

include "table.mzn";
constraint table([model,year,color,doors,cost],  cars);

solve satisfy;
