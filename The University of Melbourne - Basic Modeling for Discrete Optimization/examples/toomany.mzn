%% TooMany.mzn (too many solutions)
int: n; % number of machines
set of int: MACHINE = 1..n;
int: k; % production per day
int: red = 1; int: white = 2; int: black = 3; int: blue = 4;
set of int: COLOR = 1..4;
array[COLOR] of int: l; % lower bound on production
array[COLOR] of int: u; % upper bound on production

array[MACHINE,COLOR] of var 0..k div 2: produce;

constraint forall(m in MACHINE)
                 (sum(c in COLOR)(produce[m,c]) <= k);
constraint forall(c in COLOR)
                 (sum(m in MACHINE)(produce[m,c]) >= l[c]);
constraint forall(c in COLOR)
                 (sum(m in MACHINE)(produce[m,c]) <= u[c]);
constraint forall(m in MACHINE)
                 (produce[m,white] > 0 /\ produce[m,black] > 0 -> produce[m,blue] = 0);
constraint forall(m in MACHINE)
                 (produce[m,blue] <= produce[m,red]);
constraint forall(m in MACHINE)
                 (produce[m,red] mod 4 = 0);
constraint forall(m in MACHINE)
                 (produce[m,white] mod 3 = 0);


solve maximize sum(m in MACHINE, c in COLOR)(produce[m,c]);

output [ show_int(3,produce[m,c]) ++
         if c = 4 then "\n" else " " endif
       | m in MACHINE, c in COLOR ];

n = 4;
k = 11;
l = [8,7,10,6];
u = [14,16,12,20];

