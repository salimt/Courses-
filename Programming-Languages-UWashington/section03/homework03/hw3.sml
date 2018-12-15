(* @author: salimt *)

exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)

(* takes a string list and returns a string list that has only
the strings in the argument that start with an uppercase letter *)
fun only_capitals (xs: string list)=
    let fun f x = Char.isUpper(String.sub(x,0))
    in List.filter f xs end

(* takes a string list and returns the longest string in the
list. If the list is empty, return "". In the case of a tie, return the string closest to the beginning of the
list *)
fun longest_string1(xs: string list)=
    let fun f (s,x) = if String.size x >= String.size s then x else s 
    in List.foldl f "" xs end

(* in the case of ties
it returns the string closest to the end of the list *)
fun longest_string2(xs: string list)=
    let fun f (s,x) = if String.size x > String.size s then x else s 
    in List.foldl f "" xs end

(* PROBLEM 4 *)
fun longest_string_helper(s,x) = if String.size x > String.size s then x else s 
	
fun longest_string3(xs: string list)=
    List.foldr longest_string_helper "" xs
   
fun longest_string4(xs: string list)=
    List.foldl longest_string_helper "" xs

(* takes a string list and returns the longest string in
the list that begins with an uppercase letter, or "" if there are no such strings *)
fun longest_capitalized(xs: string list)=
    longest_string1(only_capitals xs)

(* takes a string and returns the string that is the same characters in
reverse order *)
fun rev_string(x: string)=
    (String.implode o List.rev o String.explode) x

(* first argument should be applied to elements of the second argument in order
until the first time it returns SOME v for some v and then v is the result of the call to first_answer.
If the first argument returns NONE for all list elements, then first_answer should raise the exception
NoAnswer. *)
fun first_answer f xs =
    case xs of
	[] => raise NoAnswer
      | x::xs' => case f x of
		       SOME v => v
		     | NONE => first_answer f xs'

(* first argument should be applied to elements of the second
argument. If it returns NONE for any element, then the result for all_answers is NONE *)
fun all_answers f xs =
    let fun helper_f (f,xs)(acc) =
	    case xs of
		[] => SOME acc
	      | x::xs' => case f x of
			      NONE => NONE
			    | SOME x => helper_f(f,xs')(x@acc) 		      
    in helper_f(f,xs)([]) end
	

(* takes a pattern and returns how many Wildcard
patterns it contains. *)
fun count_wildcards p =
    g (fn() => 1) (fn x => 0) p

(* takes a pattern and returns
the number of Wildcard patterns it contains plus the sum of the string lengths of all the variables 
in the variable patterns it contains. *)
fun count_wild_and_variable_lengths p =
    g (fn() => 1) String.size p

(* takes a string and a pattern (as a pair) and
returns the number of times the string appears as a variable in the pattern. *)
fun count_some_var (str, p) =
    g (fn() => 0) (fn s => if s = str then 1 else 0) p
	     
	
(* takes a pattern and returns true if and only if all the variables
appearing in the pattern are distinct from each other (i.e., use different strings). *)
fun check_pat p =
    let
	fun lstOfStrings p =
	    case p of
		Variable x => [x]
	      | TupleP xs => List.foldl (fn (p,i) => (lstOfStrings p) @ i) [] xs
	      | ConstructorP(_,x) => lstOfStrings x 
	      | _ => []

	fun duplicated [] = false
	  | duplicated (x::xs') = (List.exists (fn y => x = y) xs') orelse (duplicated xs')

	val f = not o duplicated o lstOfStrings				       	
    in
	f p
    end


(* takes a valu * pattern and returns a (string * valu) list option,
namely NONE if the pattern does not match and SOME lst where lst is the list of bindings if it does. *)
fun match(v, p) =
    case (v, p) of
	(_, Wildcard) => SOME []
      | (_, Variable(s)) => SOME []
      | (Unit, UnitP) => SOME [(v,p)]
      | (Const x, ConstP y) => if x = y then SOME [] else NONE
      | (Tuple(vs), TupleP(ps)) => if length vs = length ps
				  then all_answers match (ListPair.zip(vs, ps))
				  else NONE
      | (Constructor(s1, v), ConstructorP(s2, p)) => if s1 = s2
						    then match(v,p)
						    else NONE
      | _ => NONE
								    


(* takes a value and a list of patterns and returns a
(string * valu) list option, namely NONE if no pattern in the list matches or SOME lst where
lst is the list of bindings for the first pattern in the list that matches. *)
    
fun first_match v ps =
    SOME(first_answer (fn pat => match(v,pat)) ps)
    handle noAnswer => NONE
							 
