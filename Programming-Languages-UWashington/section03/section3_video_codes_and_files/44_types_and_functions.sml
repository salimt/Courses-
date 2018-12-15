(* Programming Languages, Dan Grossman *)
(* Section 3: Polymorphic Types and Functions As Arguments *)

(* our n_times function is polymorphic, which lets us use it for numbers, lists,
   or anything else provided f and x "agree"
     - return and argument type of f must be the same here because and only
       because result is passed back to f
*)
fun n_times (f,n,x) = 
    if n=0
    then x
    else f (n_times(f,n-1,x))

fun increment x = x+1
fun double x = x+x
val x1 = n_times(double,4,7)       (* instantiates 'a with int *)
val x2 = n_times(increment,4,7)    (* instantiates 'a with int *)
val x3 = n_times(tl,2,[4,8,12,16]) (* instantiates 'a with int list *)

(* higher-order functions are often polymorphic based on "whatever
type of function is passed" but not always: *)
fun times_until_zero (f,x) = 
    (* note: a better implementation would be tail-recursive *)
    if x=0 then 0 else 1 + times_until_zero(f, f x)

(* conversely, we have seen polymorphic functions that are not higher-order *)
fun len xs =
    case xs of
       [] => 0
      | x::xs' => 1 + len xs'

