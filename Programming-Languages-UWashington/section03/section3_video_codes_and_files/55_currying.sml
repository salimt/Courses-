(* Programming Languages, Dan Grossman *)
(* Section 3: Another Closure Idiom: Currying *)

(* old way to get the effect of multiple arguments *)
fun sorted3_tupled (x,y,z) = z >= y andalso y >= x

val t1 = sorted3_tupled (7,9,11)

(* new way: currying *)
val sorted3 = fn x => fn y => fn z => z >= y andalso y >= x

(* alternately: fun sorted3 x = fn y => fn z => z >= y andalso y >= x *)

val t2 = ((sorted3 7) 9) 11

(* syntactic sugar for calling curried functions: optional parentheses *)
val t3 = sorted3 7 9 11 

(* syntactic sugar for defining curried functions: space between arguments *)
fun sorted3_nicer x y z = z >= y andalso y >= x

(* more calls that work: *)
val t4 = sorted3_nicer 7 9 11
val t5 = ((sorted3_nicer 7) 9) 11

(* calls that do not work: cannot mix tupling and currying *)
(*val wrong1 = ((sorted3_tupled 7) 9) 11*)
(*val wrong2 = sorted3_tupled 7 9 11*)
(*val wrong3 = sorted3 (7,9,11)*)
(*val wrong4 = sorted3_nicer (7,9,11)*)

(* a more useful example *)
fun fold f acc xs = (* means fun fold f = fn acc => fn xs => *)
  case xs of
    []     => acc
  | x::xs' => fold f (f(acc,x)) xs'
(* Note: foldl in the ML standard library is very similar, but 
   the two arguments for the function f are in the opposite order. 
   The order is, naturally, a matter of taste.
*)

(* a call to curried fold: will improve this call next *)
fun sum xs = fold (fn (x,y) => x+y) 0 xs

