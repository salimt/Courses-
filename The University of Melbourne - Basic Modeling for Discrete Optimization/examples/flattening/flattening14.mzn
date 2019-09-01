var -3..3: y;
constraint (let { var int: x = 9 div y } 
            in x * y != 9) -> y != 2;

solve satisfy;