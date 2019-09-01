% team select problem

set of int: PLAYER = 1..11;
set of int: GOALIE = 1..2;
set of int: DEFENCE = 3..6;
set of int: OFFENCE = 7..11;
array[PLAYER] of string: name =
  ["Ant","Bee","Cip","Deb","Eve","Fin","Ged","Hel","Ila","Jan","Kim"];
set of int: CAPTAIN = 1..3;
int: xavier = 1; int: yuri = 2; int: zena = 3;
array[CAPTAIN,PLAYER] of int: value;
value = [| 2, 5, 6, 8, 9, 5, 8, 7, 7, 4, 6
         | 9, 8, 4, 7, 6, 4, 5, 3, 5, 5, 7
         | 8, 4, 3, 3, 6, 2, 5, 5, 3, 2, 5 |];

var set of PLAYER: Xavier;
var set of PLAYER: Yuri;
var set of PLAYER: Zena;

predicate correct_team(var set of PLAYER: team) = 
          card(team) = 6 /\ 
          card(team intersect GOALIE) >= 1 /\
          card(team intersect DEFENCE) >= 2 /\
          card(team intersect OFFENCE) >= 2;

constraint correct_team(Xavier) /\ correct_team(Yuri) /\ correct_team(Zena);

constraint card(Xavier intersect Yuri) <= 2;
constraint card(Xavier intersect Zena) <= 2;

var int: obj = sum(p in Xavier)(value[xavier,p]) +
               sum(p in Yuri)(value[yuri,p]) +
               sum(p in Zena)(value[zena,p]);

solve maximize obj;

output ["Xavier: "] ++
       [ show(name[p]) ++ " " | p in fix(Xavier) ] ++
       ["\nYuri:   "] ++
       [ show(name[p]) ++ " " | p in fix(Yuri) ] ++
       ["\nZena:   "] ++
       [ show(name[p]) ++ " " | p in fix(Zena) ] ++
       ["\nobj = ", show(obj), ";\n"];

