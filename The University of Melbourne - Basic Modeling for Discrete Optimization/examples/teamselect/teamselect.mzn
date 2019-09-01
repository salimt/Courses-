set of int: PLAYER = 1..11;
set of int: GOALIE = 1..2;
set of int: DEFENCE = 3..6;
set of int: OFFENCE = 7..11;

var set of PLAYER: Xavier;
var set of PLAYER: Yuri;
var set of PLAYER: Zena;

array[CAPTAIN] of var set of PLAYER: team = [Xavier,Yuri,Zena];

set of int: CAPTAIN = 1..3;
array[CAPTAIN,PLAYER] of int: value;
 
value = [| 2, 5, 6, 8, 9, 5, 8, 7, 7, 4, 6
         | 9, 8, 4, 7, 6, 4, 5, 3, 5, 5, 7
         | 8, 4, 3, 3, 6, 2, 5, 5, 3, 2, 5 |]; 

constraint card(Xavier intersect GOALIE) >= 1;
constraint card(Xavier intersect DEFENCE) >= 2;
constraint card(Xavier intersect OFFENCE) >= 2;
constraint card(Xavier) = 6;


constraint card(Yuri intersect GOALIE) >= 1;
constraint card(Yuri intersect DEFENCE) >= 2;
constraint card(Yuri intersect OFFENCE) >= 2;
constraint card(Yuri) = 6;

constraint card(Zena intersect GOALIE) >= 1;
constraint card(Zena intersect DEFENCE) >= 2;
constraint card(Zena intersect OFFENCE) >= 2;
constraint card(Zena) = 6;

constraint card(Xavier intersect Yuri) <= 2;
constraint card(Xavier intersect Zena) <= 2;

solve maximize obj;

var int: obj = sum(c in CAPTAIN)(sum(p in team[c])(value[c,p]));

array[PLAYER] of string: name =
  ["Ant","Bee","Cip","Deb","Eve","Fin","Ged","Hel","Ila","Jan","Kim"];

output ["Xavier: "] ++
       [ show(name[p]) ++ " " | p in fix(Xavier) ] ++
       ["\nYuri:   "] ++
       [ show(name[p]) ++ " " | p in fix(Yuri) ] ++
       ["\nZena:   "] ++
       [ show(name[p]) ++ " " | p in fix(Zena) ] ++
       ["\nobj = ", show(obj), ";\n"];
