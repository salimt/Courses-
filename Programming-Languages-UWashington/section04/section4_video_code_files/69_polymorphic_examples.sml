(* Programming Languages, Dan Grossman *)
(* Section 4: Polymorphic Examples *)

(*
  First several steps are just like with sum from previous segment:

   length : T1 -> T2 [must be a function; all functions take one argument]
   xs : T1 [must have type of f's argument]

   x : T3 [pattern match against T3 list]
   xs' : T3 list [pattern match against T3 list]

   T1 = T3 list [else pattern-match on xs doesn't type-check]

   0 : int, so case-expresssion : int, so body : int, so T2=int

   recursive call type-checks because xs' has type T3 list, which = T1
   and T2=int, so fine argument to addition

   so with all our constraints, length : T3 list -> int
   so 'a list -> int
*)
fun length xs =
    case xs of
	[] => 0
      | x::xs' => 1 + (length xs')


(* 
   f : T1 * T2 * T3 -> T4
   x : T1
   y : T2
   z : T3

   both conditional branches must have type T4 (the type of the function body),
   so T1 * T2 * T3 = T4 and T2 * T1 * T3 = T4, which means T1 = T2

   putting it all together, f : T1 * T1 * T3 -> T1 * T1 * T3
   now replace unconstrained types /consistently/ with type variables:
     'a * 'a * 'b -> 'a * 'a * 'b
*)
fun f (x,y,z) =
    if true
    then (x,y,z)
    else (y,x,z)

(*
   compose : T1 * T2 -> T3
   f : T1
   g : T2
   x : T4

   from body of compose being a function, T3 = T4->T5 for some T4 and T5
   from g being passed x, T2 = T4->T6 for some T6
   from f being passed result of g, T1 = T6->T7 for some T7
   from f being body of anonymous function, T7=T5
 
   putting it all together:
     T1=T6->T5, T2=T4->T6, and T3=T4->T5
   so compose: (T6->T5) * (T4->T6) -> (T4->T5)
   now replace unconstrained types /consistently/ with type variables:
   ('a -> 'b) * ('c -> 'a) -> ('c -> 'b)
*)
fun compose (f,g) = fn x => f (g x)

