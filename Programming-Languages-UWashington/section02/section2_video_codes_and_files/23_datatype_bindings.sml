(* Programming Languages, Dan Grossman *)
(* Section 2: Datatype Bindings *)

datatype mytype = TwoInts of int * int 
                | Str of string 
                | Pizza

val a = Str "hi"
val b = Str
val c = Pizza
val d = TwoInts(1+2,3+4)
val e = a

(* Do _not_ redo datatype bindings (e.g., via use "filename.sml".
   Doing so will shadow the type name and the constructors.) 
datatype mytype = TwoInts of int * int | Str of string | Pizza *)
