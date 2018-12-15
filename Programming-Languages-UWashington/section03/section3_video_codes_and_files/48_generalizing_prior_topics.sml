(* Programming Languages, Dan Grossman *)
(* Section 3: Generalizing Prior Topics *)

(* Returning a function *)
fun double_or_triple f =
    if f 7
    then fn x => 2*x
    else fn x => 3*x

val double = double_or_triple (fn x => x-3 = 4)
val nine = (double_or_triple (fn x => x = 42)) 3

(* Higher-order functions over our own datatype bindings *)
datatype exp = Constant of int 
	     | Negate of exp 
	     | Add of exp * exp
	     | Multiply of exp * exp

fun true_of_all_constants(f,e) =
    case e of
	Constant i => f i
      | Negate e1 => true_of_all_constants(f,e1)
      | Add(e1,e2) => true_of_all_constants(f,e1)
		      andalso true_of_all_constants(f,e2)
      | Multiply(e1,e2) => true_of_all_constants(f,e1)
			   andalso true_of_all_constants(f,e2)

fun all_even e = true_of_all_constants((fn x => x mod 2 = 0),e)
