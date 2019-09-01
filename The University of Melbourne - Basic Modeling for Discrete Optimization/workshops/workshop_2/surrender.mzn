% workshop 2
% @author: salimt
include "globals.mzn";

% select a set of trade negotiation party
enum NEGOTIATOR;
NEGOTIATOR: dummy; 

int: l; % minimum party size
int: u; % maximum party size
int: m; % minimum joint ability

array[NEGOTIATOR] of int: honor;
array[NEGOTIATOR,NEGOTIATOR] of int: joint;
array[1..u] of var NEGOTIATOR: party;

constraint alldifferent(party);
constraint sum(i,j in party where i < j)(joint[i,j]) >= m;

var int: minHonor = min(i in party) (honor[i]);
solve maximize minHonor;

output ["party = {"] ++ [if fix(party[i]) != dummy then show(party[i])
        ++ if i < u /\ fix(party[i+1]) != dummy then ", " else "" endif
           else "" endif | i in 1..u ] ++ ["};\nobj = \(minHonor);"];

