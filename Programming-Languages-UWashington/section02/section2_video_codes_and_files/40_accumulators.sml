(* Programming Languages, Dan Grossman *)
(* Section 2: Accumulators *)

fun sum1 xs =
    case xs of
        [] => 0
      | i::xs' => i + sum1 xs'

fun sum2 xs =
    let fun f (xs,acc) =
            case xs of
                [] => acc
              | i::xs' => f(xs',i+acc)
    in
        f(xs,0)
    end

fun rev1 xs =
   case xs of
       [] => []
     | x::xs' => (rev1 xs') @ [x]

fun rev2 xs =
    let fun aux(xs,acc) =
            case xs of
                [] => acc
              | x::xs' => aux(xs', x::acc)
    in
        aux(xs,[])
    end
