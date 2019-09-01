% Missing Solutions
int: n; % no of people
set of int: PERSON = 1..n;
int: male = 1; int: female = 2;
set of int: GENDER = male..female;
array[PERSON] of GENDER: gender;
array[PERSON] of int: age;

array[1..n] of var PERSON: order;

include "alldifferent.mzn";
constraint alldifferent(order);

% males and females alternate
constraint forall(i in 1..n)
                 (i mod 2 = 0 <-> gender[order[i]] = female);

% age does not grow bu more than 10
constraint forall(i in 1..n-1)
                 (age[order[i+1]] - age[order[i]] <= 10);

solve satisfy;

output [ show(order), "\n"] ++
       ["["] ++ [ show(age[order[i]]) ++ if i < n then ", " else "]\n" endif
                | i in 1..n ] ++
       ["["] ++ [ show(gender[order[i]]) ++ if i < n then ", " else "]\n" endif
                 | i in 1..n ];


%n = 6;
%gender = [male,male,male,female,female,female];
%age = [13, 25, 34, 40, 16, 13];

n = 5;
gender = [male,male,female,female,female];
age = [13,25,34,40,16];

%order = [4,2,3,1,5];
