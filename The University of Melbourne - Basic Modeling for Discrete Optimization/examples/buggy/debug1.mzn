int: n = 5;
array[1..n] of var 1..n: a;
array[1..n] of 1..n: b = [3,5,2,3,1];

constraint forall(j in 1..n, i in b[n-j]..n)
                 (a[j] < i);

solve satisfy;