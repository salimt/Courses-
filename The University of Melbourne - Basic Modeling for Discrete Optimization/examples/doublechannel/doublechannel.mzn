% scheduling ships entering and leaving a port through k channels
set of int: TYPE = 1..3;
int: entering = 1;
int: leaving  = 2;
int: dummy = 3;

int: nC; % number of channels
set of int: CHANNEL = 1..nC;
array[CHANNEL] of int: len;    % length in 100m units

int: nS; % number of ships
set of int: SHIP = 1..nS; % ships ENTER then LEAVE
set of int: SHIPE = 1..nS+nC; % ships ENTER then LEAVE then nC dummy ships

array[SHIP] of int: speed;   % time in minutes to traverse 100m
array[SHIP] of int: desired; % desired time to start to enter/leave 
array[SHIP] of entering..leaving: dirn;   % entering or leaving

int: leeway;  % leeway between 2 ships in 100m units
int: maxt;    % maximum time
set of int: TIME = 0..maxt;

array[SHIPE] of var TYPE: kind = dirn ++ [ dummy | i in 1..nC];
array[SHIPE] of var TIME:    start; % when ships enter the channel 
array[SHIPE] of var TIME:     end; % when ships leave the channel 
array[SHIPE] of var CHANNEL: channel; % which channel a ship is assigned to

array[SHIP] of var SHIPE:   next;  % the next ship in the same channel (or a dummy)

% dummy ships
constraint forall(s in nS + 1 .. nS + nC)(start[s] = maxt /\ end[s] = maxt);
constraint forall(s in nS + 1 .. nS + nC)(channel[s] = s - nS);

% relationships between start and ends
constraint forall(s in SHIP)(end[s] = start[s] + len[channel[s]]*speed[s]);


% the next ship are all different
include "alldifferent.mzn";
constraint alldifferent(next);

% no clashes in channel
constraint forall(s in SHIP)(if kind[s] + kind[next[s]] = entering+leaving then
                                end[s] <= start[next[s]]
                             else
                                start[s] + speed[s]*leeway <= start[next[s]] /\
                                end[s] + speed[s]*leeway <= end[next[s]]
                             endif);

% next ship is in same channel
constraint forall(s in SHIP)(channel[next[s]] = channel[s]);

solve minimize sum(s in SHIP)(abs(start[s] - desired[s]));

output [ "start = ",show(start),
         ";\nend = ", show(end),
         ";\nchannel = ", show(channel),
         ";\nnext = ", show(next),
         ";\n" ];

