% Taken from the paper by Patrick Prosser at CPAIOR 2014
int: n; % number of agents
set of int: AGENT = 1..n;
set of int: AGENT0 = 0..n;    % zero indicates no agent
array[AGENT,1..n-1] of AGENT0: pref; % preference order for each agent
     % the preference order is padded by zeros
array[AGENT] of int: npref = [ sum(j in 1..n-1)(bool2int(pref[i,j] > 0))
                             | i in AGENT ];
constraint forall(i in AGENT)
                 (assert(forall(j in 1..n-1)
                               (pref[i,j] != i /\ 
                                (pref[i,j] = 0 <-> j > npref[i])) /\
                         forall(j1, j2 in 1..npref[i] where j1 < j2)
                               (pref[i,j1] != pref[i,j2]),
                         "Agent " ++ show(i) ++ "has erroneous preferences"));
%% ranking information: rank = 0 means not ranked
array[AGENT,AGENT] of 0..n-1: rank = 
     array2d(AGENT,AGENT, [ max([bool2int(pref[i1,j] = i2) * j | j in 1..n-1])
                          | i1, i2 in AGENT ]);

array[AGENT] of var 1..n: a;
%% constraint (1)
constraint forall(i in AGENT)(a[i] <= npref[i] + 1);
%% constraint (2)
constraint forall(i in AGENT, p in 1..npref[i])
                 ( let { int: j = pref[i,p] } in 
                   a[i] > rank[i,j] -> a[j] < rank[j,i]
                 );
%% constraint (3)
constraint forall(i in AGENT, p in 1..npref[i])
                 ( let { int: j = pref[i,p] } in 
                   a[i] = rank[i,j] -> a[j] = rank[j,i]
                 );

solve satisfy;

output  [ if i < fix(pref[i,a[i]]) then "(" ++ show(i) ++ "," ++ show(pref[i,a[i]]) ++ ")\n"
          else "" endif
        | i in AGENT ]
       ++ [show(rank),"\n"] 
 %      ++ [show(npref),"\n"] 
 %      ++ [show(a),"\n"]
        ;
       

