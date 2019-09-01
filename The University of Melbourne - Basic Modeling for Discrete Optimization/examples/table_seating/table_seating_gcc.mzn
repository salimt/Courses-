int: n;
set of int: PERSON = 1..n;
int: T; % number of tables
set of int: TABLE = 1..T;
int: S; % tables size
array[int,1..2] of PERSON: couples;
set of PERSON: key;

array[TABLE] of var set of PERSON: table;
array[PERSON] of var TABLE: seat;


predicate not_same_table(PERSON:p1, PERSON: p2) =
                        seat[p1] != seat[p2];

constraint global_cardinality_low_up(seat, [t|t in TABLE], [0|t in TABLE], [S|t in TABLE]);

constraint forall(c in index_set_1of2(couples))
                 (not_same_table(couples[c,1],couples[c,2]));

constraint forall(p1,p2 in key where p1 < p2)
                 (not_same_table(p1,p2));

var int: obj = sum(c in index_set_1of2(couples))(seat[couples[c,1]] + seat[couples[c,2]]);

constraint forall(t in TABLE, p in PERSON)(p in table[t] <-> seat[p] = t);    

solve minimize obj;

output [show(table), " = ", show(obj)];


n = 20;
T = 5;
S = 5;
couples = [| 1, 2 | 4, 5 | 6, 7 | 8, 10
           | 11, 12 | 13, 14 | 17, 18 |];
key = {1, 6, 7, 8};
