(* Programming Languages, Dan Grossman *)
(* Section 3: Currying Wrapup *)

(* generic functions to switch how/whether currying is used *)
(* in each case, the type tells you a lot *)

fun curry f x y = f (x,y)

fun uncurry f (x,y) = f x y

fun other_curry1 f = fn x => fn y => f y x

fun other_curry2 f x y = f y x

(* example *)

(* tupled but we wish it were curried *)
fun range (i,j) = if i > j then [] else i :: range(i+1, j)

(* no problem *)
val countup = curry range 1

val xs = countup 7
