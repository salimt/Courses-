int: n;
set of int: PERSON = 1..n;
int: T; % number of tables
set of int: TABLE = 1..T;
int: S; % tables size
array[int,1..2] of PERSON: couples;
set of PERSON: key;

array[TABLE] of var set of PERSON: table;

predicate not_same_table(PERSON:p1, PERSON: p2) =
          forall(t in TABLE)(not ({p1,p2} subset table[t]));

constraint forall(t in TABLE)(card(table[t]) <= S);

constraint forall(p in PERSON)(exists(t in TABLE)(p in table[t]));

constraint forall(c in index_set_1of2(couples))
                 (not_same_table(couples[c,1],couples[c,2]));

constraint forall(p1,p2 in key where p1 < p2)
                 (not_same_table(p1,p2));

var int: obj = sum(c in index_set_1of2(couples))
                  ( let { var TABLE: tn1 = sum(t in TABLE)(t * bool2int(couples[c,1] in table[t]));
                          var TABLE: tn2 = sum(t in TABLE)(t * bool2int(couples[c,2] in table[t]));
                    } in  tn1 + tn2);



solve minimize obj;

output [show(table), " = ", show(obj)];


n = 20;
T = 5;
S = 5;
couples = [| 1, 2 | 4, 5 | 6, 7 | 8, 10
           | 11, 12 | 13, 14 | 17, 18 |];
key = {1, 6, 7, 8};
