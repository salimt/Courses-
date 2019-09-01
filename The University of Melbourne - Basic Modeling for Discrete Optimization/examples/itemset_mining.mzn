int : NrI ; 
int : NrT ; 
int : Freq ;
array [ 1..NrT ] of set of 1..NrI : TDB;
var set of 1..NrI : Items ;
constraint card ( cover ( Items ,TDB ) ) >= Freq ;
solve satisfy ; 

function var set of int : cover (
         var set of int : Items ,
         array [ int ] of var set of int : D) = 
         let {
                          var set of index_set (D): Trans ;
                          constraint forall ( t in index_set (D) )
                          ( t in Trans <-> Items subset D[ t ] );
         } in Trans;

array[1.. NrI] of int: itemprice;
int: minUtility;

constraint sum(i in Items)(itemprice[i]) >= minUtility;

NrI = 6;
int: bread = 1;
int: butter = 2;
int: cheese = 3;
int: cocoa = 4;
int: milk = 5;
int: vegemite = 6;
array[1..NrI] of string: name = ["bread","butter","cheese","cocoa","milk","vegemite"];
itemprice = [1,2,1,2,1,1];

NrT = 7;
TDB = [ { butter, cheese, milk, vegemite },
        { butter, cocoa, milk, vegemite },
	{ bread, cocoa, cheese, milk },
	{ bread, butter, cheese, vegemite }, 
	{ bread, cheese, milk, vegemite },
	{ bread, butter, cheese, cocoa, milk },
	{ bread, cheese, milk }];

Freq = 4;
minUtility = 3;

output [ name[i] ++ " " | i in fix(Items) ];
