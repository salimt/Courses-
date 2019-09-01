var -2..2: x; var -1..2: y; var bool: b;
constraint if b then x else y endif >= 0;

solve satisfy;
