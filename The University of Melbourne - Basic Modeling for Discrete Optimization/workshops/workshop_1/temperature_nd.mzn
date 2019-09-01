array[int] of int: readings;
int: start;
int: l = length(readings);
set of int: TIME = 1..l+1;

array[TIME] of var 25..30: temp;
array[TIME] of var SET: choice;

enum SET = {heat, strongly_heat, cool, strongly_cool, do_nothing};
array[SET] of int: adj = [+1, +4, -2, -5, 0];
array[SET] of int: cost = [1, 5, 3, 9, 0];

constraint temp[1] = start;
constraint forall(t in 2..l) (temp[t] = (readings[t-1] + temp[t-1]) div 2 + adj[choice[t-1]]);

var int: minCost = sum(i in TIME) (cost[choice[i]]);
solve minimize minCost;

output["temp = \(show(temp)); \nchoice = \(show(choice)) \ncost = \(show(minCost))"]