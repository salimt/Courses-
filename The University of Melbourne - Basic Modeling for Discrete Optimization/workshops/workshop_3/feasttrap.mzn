% Beauty Trap
% @author: salimt

% Create the most magnificient feast 
enum DISH;
enum TASTE = {spicy, sour, salty, sweet, umame, bland};
enum TEMP = {hot, cold, warm};
%int: ndish;
%set of int: DISH = 1..ndish;
% set of int: TASTE = 1..6;
% int: spicy = 1; int: sour = 2; int: salty = 3; int: sweet = 4; int: umame = 5; int: bland = 6;
% set of int: TEMP = 1..3;
% int: hot = 1; int: cold = 2; int: warm = 3;


array[DISH] of TASTE: taste;
array[DISH] of TEMP: temp;
array[DISH] of bool: heavy;
array[DISH] of int: value;

int: len; % length of banquet
set of int: COURSE = 1..len;

array[COURSE] of var DISH: dish;

include "alldifferent.mzn";
constraint alldifferent(dish);

constraint taste[dish[len]] = sweet /\ taste[dish[1]] = salty;
constraint forall(i in 1..len-1)(taste[dish[i]] != taste[dish[i+1]]);
constraint forall(i in 1..len-1)(taste[dish[i]] = sour  -> taste[dish[i+1]] in {bland, umame});
constraint forall(i in 1..len-1)(taste[dish[i]] = spicy -> taste[dish[i+1]] in {bland, sweet});
constraint forall(i in 1..len-1)(taste[dish[i]] = sweet -> taste[dish[i+1]] in {bland, salty, sour});
constraint forall(i in 1..len-1)(temp[dish[i]]=hot -> temp[dish[i+1]] != cold);                                 
constraint forall(i in 1..len-2)(heavy[dish[i]] /\ heavy[dish[i+1]] -> not(heavy[dish[i+2]]));


solve maximize val;
var int: val;
constraint val = sum(i in 1..len)(value[dish[i]]) +
                 sum(i in 1..len-1)(temp[dish[i]] != temp[dish[i+1]]) +
                 len - 1 +
                 sum(i in 1..len-1)(heavy[dish[i]] != heavy[dish[i+1]]);
                 
output ["dish = {"] ++ [show(dish[i]) ++ if i == len then "};\nobj = \(val)" else ", " endif | i in 1..len ]
