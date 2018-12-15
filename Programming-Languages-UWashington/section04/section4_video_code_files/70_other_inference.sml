(* Programming Languages, Dan Grossman *)
(* Section 4: The Value Restriction and Other Type-Inference Challenges *)

(* first line is not polymorphic so next two lines do not type-check *)
val r = ref NONE 

(*
val _ = r := SOME "hi" 

val i = 1 + valOf (!r)
*)

type 'a foo = 'a ref
val f : 'a -> 'a foo = ref 
val r2 = f NONE (* also need value restriction here *)

(* where the value restriction arises despite no mutation *)
val pairWithOne = List.map (fn x => (x,1))

(* a workaround *)
fun pairWithOne2 xs = List.map (fn x => (x,1)) xs

