array[0..2,0..2] of var 0..2: x;
constraint sum(i in 0..2)(x[i,i]) <= 1;
constraint x[x[1,1],1] = 2;

solve satisfy;