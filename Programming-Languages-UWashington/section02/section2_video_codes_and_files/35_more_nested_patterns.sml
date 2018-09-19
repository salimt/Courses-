(* Programming Languages, Dan Grossman *)
(* Section 2: More Nested Patterns *)

(* another elegant use of "deep" patterns *)
fun nondecreasing xs =
    case xs of
	[] => true
      | x::[] => true
      | head::(neck::rest) => (head <= neck andalso nondecreasing (neck::rest))

(* nested pattern-matching often convenient even without recursion;
   also the wildcard pattern is good style 
   match on a pair and one or more parts of it quite useful on homework 2
*)
datatype sgn = P | N | Z

fun multsign (x1,x2) = 
  let fun sign x = if x=0 then Z else if x>0 then P else N 
  in
      case (sign x1,sign x2) of
	  (Z,_) => Z
	| (_,Z) => Z
	| (P,P) => P
	| (N,N) => P
	| _     => N (* many say bad style; I am okay with it *)
  end

(* simpler use of wildcard pattern for when you do not need the data *)

fun len xs =
    case xs of
       [] => 0
      | _::xs' => 1 + len xs'
