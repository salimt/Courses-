predicate far_or_equal(var int:x1, var int:y1,
                       var int:x2, var int:y2)=
          man_dist(x1,y1,x2,y2) >= 4 \/ 
         (x1 = x2 /\ y1 = y2);

function var int: man_dist(var int:u1, 
         var int:v1, var int:u2, var int:v2) =
         abs(u1 - u2) + abs(v1 - v2);

var 0..4: a;  var 0..4: b;  var 0..4: c;  var 0..4: d;  
constraint far_or_equal(a,b,c,d);

solve satisfy;
