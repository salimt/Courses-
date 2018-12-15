(* Programming Languages, Dan Grossman *)
(* Section 4: What is Type Inference *)


fun f x = (* infer val f : int -> int *) 
    if x > 3
    then 42 
    else x * 2
(*
fun g x = (* report type error *) 
    if x > 3
    then true 
    else x * 2
*)
