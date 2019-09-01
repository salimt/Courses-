int: W; % number of Warehousse
set of int: WARE = 1..W;
int: F; % number of Factories
set of int: FACT = 1..F;

array[WARE] of int: demand;
array[FACT] of int: production;

array[FACT,WARE] of int: cost;

array[FACT,WARE] of var int: ship;

%int: m = max(demand);
%array[FACT,WARE] of var 0..m: ship;
constraint forall(f in FACT, w in WARE)(ship[f,w] >= 0);

constraint forall(w in WARE)
                 (sum(f in FACT)(ship[f,w]) >= demand[w]);

constraint forall(f in FACT)
                 (sum(w in WARE)(ship[f,w]) <= production[f]);

solve minimize sum(f in FACT, w in WARE)
                  (cost[f,w]*ship[f,w]);


output [ show_int(2,ship[f,w]) ++
         if w == W then "\n" else " " endif
       | f in FACT, w in WARE ];