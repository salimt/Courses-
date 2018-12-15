(* Programming Languages, Dan Grossman *)
(* Section 3: Callbacks *)

(* these two bindings would be internal (private) to the library *)
val cbs : (int -> unit) list ref = ref []
fun onEvent i =
   let fun loop fs =
        case fs of 
          [] => ()
        | f::fs' => (f i; loop fs')
    in loop (!cbs) end

(* clients call only this function (public interface to the library) *)
fun onKeyEvent f = cbs := f::(!cbs)

(* some clients where closures are essential
   notice different environments use bindings of different types
 *)
val timesPressed = ref 0
val _ = onKeyEvent (fn _ => timesPressed := (!timesPressed) + 1)

fun printIfPressed i =
    onKeyEvent (fn j => if i=j
                        then print ("you pressed " ^ Int.toString i ^ "\n")
                        else ())

val _ = printIfPressed 4
val _ = printIfPressed 11
val _ = printIfPressed 23
val _ = printIfPressed 4

