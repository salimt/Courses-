(* Programming Languages, Dan Grossman *)
(* Section 3: Mutable References *)

val x = ref 42 

val y = ref 42 

val z = x

val _ = x := 43

val w = (!y) + (!z) (* 85 *)

(* x + 1 does not type-check *)
