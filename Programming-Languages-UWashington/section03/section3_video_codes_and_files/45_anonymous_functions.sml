(* Programming Languages, Dan Grossman *)
(* Section 3: Anonymous Functions *)

fun n_times (f,n,x) = 
    if n=0
    then x
    else f (n_times(f,n-1,x))

fun triple x = 3*x

fun triple_n_times1 (n,x) = n_times(triple,n,x)

fun triple_n_times2 (n,x) =
  let fun triple x = 3*x in n_times(triple,n,x) end

(* actually since used only once, we could define it 
   right where we need it *)
fun triple_n_times3 (n,x) = 
    n_times((let fun triple y = 3*y in triple end), n, x)

(* This does not work: a function /binding/ is not an /expression/ *)
(* fun triple_n_times3 (n,x) = n_times((fun triple y = 3*y), n, x) *)

(* This /anonymous function/ expression works and is the best style: *)
(* Notice the function has no name *)

fun triple_n_times4 (n,x) = n_times((fn y => 3*y), n, x)

(* because triple_n_times4 does not call itself, we could use a val-binding
   to define it, but the fun binding above is better style *)
val triple_n_times5 = fn (n,x) => n_times((fn y => 3*y), n, x)
