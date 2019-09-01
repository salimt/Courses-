int: n;
array[1..n] of var 0..9: x;
constraint sum(x) == product(x);
constraint forall(i in 1..n-1) (x[i]<=x[i+1]);
output[show(x)];