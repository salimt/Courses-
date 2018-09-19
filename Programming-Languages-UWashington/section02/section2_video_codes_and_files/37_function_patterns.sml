(* Programming Languages, Dan Grossman *)
(* Section 2: *Optional*: Function Patterns *)

datatype exp = Constant of int 
             | Negate of exp 
             | Add of exp * exp
             | Multiply of exp * exp

fun old_eval e =
    case e of
        Constant i => i
      | Negate e2  => ~ (old_eval e2)
      | Add(e1,e2) => (old_eval e1) + (old_eval e2)
      | Multiply(e1,e2) => (old_eval e1) * (old_eval e2)

fun eval (Constant i) = i
  | eval (Negate e2) = ~ (eval e2)
  | eval (Add(e1,e2)) = (eval e1) + (eval e2)
  | eval (Multiply(e1,e2)) = (eval e1) * (eval e2)

fun append ([],ys) = ys
  | append (x::xs',ys) = x :: append(xs',ys)
