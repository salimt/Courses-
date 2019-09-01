int: n;
array[1..n] of var 0..3: x;
constraint x[1] == 0 /\ x[n] == 3;
constraint forall ( i in 1..n) ( if (i mod 3) == 0 then x[i] >= 2 endif );
constraint forall (i in 1..n-1) ( x[i]+x[i+1] <= 3 );

output["\(sum(x)) = \(x)"];




