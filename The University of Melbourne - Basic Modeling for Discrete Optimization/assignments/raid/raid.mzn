% assignment 2 (18/18)
% @author: salimt 

% select a set of raiding warrior
enum WARRIOR;
WARRIOR: dummy; 
enum CLAN; % the clan they belong to

int: l; % minimum raid party size
int: u; % maximum raid party size
int: m; % limit of hatreds

array[WARRIOR] of int: strength;
array[WARRIOR] of CLAN: clan;

array[CLAN,CLAN] of 0..1: hates;
array[1..u] of var WARRIOR: raid;

constraint forall(i,j in 1..u where i != j)(raid[i] != raid[j]);
constraint forall(i in 1..u-1)(raid[i] < raid[i+1]);
constraint sum(i,j in 1..u where i < j)(hates[clan[raid[i]], clan[raid[j]]]) <= m;

solve maximize obj;
var int: obj;
constraint obj = sum(i in 1..u)(strength[raid[i]]);

output["raid = {"] ++ [if fix(raid[i]) != dummy then show(raid[i]) ++ 
                       if i == u then "};\nobj = \(obj);" 
                       else ", " endif  else "" endif  | i in 1..u];



% output ["raid = {"] ++ [if fix(raid[i]) != dummy then show(raid[i])
%         ++ if i < u /\ fix(raid[i+1]) != dummy then ", " else "" endif
%            else "" endif | i in 1..u ] ++ ["};\n"] ++ ["obj = \(obj);"];
