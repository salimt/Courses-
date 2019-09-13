% 26/26
% @author: salimt 

int: n; % number of court members
set of int: COURT = 1..n;
int: emperor = 1;
array[COURT] of int: rank;
array[COURT] of int: ability;

int: m; % number of horses
set of int: HORSE = 1..m;
array[HORSE] of int: beauty;
array[HORSE] of int: speed;

array[COURT,HORSE] of int: enjoy;
array[1..n] of var 0..m: horse;


include "alldifferent_except_0.mzn";
constraint  alldifferent_except_0(horse);

% emperor must enjoy it the most and nobody can suffer from it(<0)
constraint forall(i in 2..n)(if horse[i] != 0 
                             then enjoy[emperor, horse[emperor]] > 
                                  enjoy[i, horse[i]] /\ enjoy[i, horse[i]] >= 0 endif );
                                  
% all members of court must ride unless there is no horse left
var int: notRiding = sum([if horse[i] != 0 
                          then 1 
                          else 0 endif | i in 1..n]);

% no horses left behind
constraint notRiding = min(m,n);

% higher ranks ride more beautiful horses
constraint forall(i,j in 1..n)( rank[i] > rank[j] -> 
                if horse[i] != 0 /\ horse[j] != 0
                then beauty[horse[i]] >= beauty[horse[j]]
                else horse[j] = 0 \/ (horse[i] = 0 /\ horse[j] = 0)
                endif );


var int: lessAbi = sum([if speed[horse[i]] > speed[horse[j]] /\ 
                           ability[i] < ability[j] /\ 
                           horse[i] != 0  then 1 else 0 endif | i,j in 1..n]);

var int: noRiders = sum([speed[i] > speed[j] | 
                         i in horse, j in 1..m where not(exists([k=j | k in horse]))]);

var int: obj;
constraint obj = sum([enjoy[i, horse[i]] | 
                      i in 1..n where horse[i] != 0]) - (noRiders+lessAbi)*100;
solve maximize obj;

output["horse = " ++ show(horse) ++ ";\nobj = \(obj);"];