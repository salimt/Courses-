% Taken from the paper by Patrick Prosser at CPAIOR 2014
int: n; % number of agents
set of int: AGENT = 1..n;

array[AGENT] of int: npref; % number of preferences of each agent
array[AGENT] of int: cum_npref = [ sum(j in 1..i-1)(npref[j]) | i in AGENT];
array[1..sum(npref)] of int: preferences;  % all preferences in one list

function AGENT: pref(AGENT: i, 1..n-1: j) = 
         assert(j in 1..npref[i], "Called pref(" ++ show(i) ++ "," ++ show(j) ++ ") too large preference\n",
         preferences[cum_npref[i] + j]);

constraint forall(i in AGENT)
                 (assert(forall(j in 1..npref[i])
                               (pref(i,j) != i) /\
                         forall(j1, j2 in 1..npref[i] where j1 < j2)
                               (pref(i,j1) != pref(i,j2)),
                         "Agent " ++ show(i) ++ "has erroneous preferences"));


%% ranking information: rank = 0 means not ranked but shoulnt occur!
function 0..n-1: rank(AGENT: i1, AGENT: i2) = 
                 max([bool2int(pref(i1,j) = i2) * j | j in 1..npref[i1]]); 

array[AGENT] of var 1..n: a;
%% constraint (1)
constraint forall(i in AGENT)(a[i] <= npref[i] + 1);
%% constraint (2)
constraint forall(i in AGENT, p in 1..npref[i])
                 ( let { int: j = pref(i,p) } in 
                   a[i] > rank(i,j) -> a[j] < rank(j,i)
                 );
%% constraint (3)
constraint forall(i in AGENT, p in 1..npref[i])
                 ( let { int: j = pref(i,p) } in 
                   a[i] = rank(i,j) -> a[j] = rank(j,i)
                 );

solve satisfy;

   output  [ if fix(a[i]) < npref[i] /\ i < pref(i,fix(a[i]))
             then "(" ++ show(i) ++ "," ++ show(pref(i,fix(a[i]))) ++ ")\n"
             else "" endif
           | i in AGENT ]
 %      ++ [show(rank),"\n"] 
       ++
       [show(npref),"\n"] 
       ++ [show(a),"\n"]
        ;
       

