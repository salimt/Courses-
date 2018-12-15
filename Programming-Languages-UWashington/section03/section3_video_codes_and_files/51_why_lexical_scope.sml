(* Programming Languages, Dan Grossman *)
(* Section 3: Why Lexical Scope *)

(* f1 and f2 are always the same, no matter where the result is used *)

fun f1 y =
    let 
	val x = y + 1
    in
	fn z => x + y + z
    end

fun f2 y =
    let 
	val q = y + 1
    in
	fn z => q + y + z
    end

val x = 17 (* irrelevant *)
val a1 = (f1 7) 4
val a2 = (f2 7) 4

(* f3 and f4 are always the same, no matter what argument is passed in *)

fun f3 g =
    let 
	val x = 3 (* irrelevant *)
    in
	g 2
    end

fun f4 g =
    g 2

val x = 17 
val a3 = f3 (fn y => x + y)
val a4 = f3 (fn y => 17 + y)

(* under dynamic scope, the call "g 6" below would try to add a string
(from looking up x) and would have an unbound variable (looking up y),
even though f1 type-checked with type int -> (int -> int) *)

val x = "hi"
val g = f1 7
val z = g 4

(* Being able to pass closures that have free variables (private data)
   makes higher-order functions /much/ more useful *)
fun filter (f,xs) =
    case xs of
	[] => []
      | x::xs' => if f x then x::(filter(f,xs')) else filter(f,xs')

fun greaterThanX x = fn y => y > x

fun noNegatives xs = filter(greaterThanX ~1, xs)

fun allGreater (xs,n) = filter (fn x => x > n, xs)

