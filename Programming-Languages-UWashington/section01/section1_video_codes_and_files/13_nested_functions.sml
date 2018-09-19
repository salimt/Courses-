(* Programming Languages, Dan Grossman *)
(* Section 1: Nested Functions *)

fun countup_from1 (x : int) =
    let fun count (from:int, to:int) =
	    if from=to
	    then to::[] (* note: can also write [to] *)
	    else from :: count(from+1,to)
    in
	count(1,x)
    end

fun countup_from1_better (x : int) =
    let fun count (from:int) =
	    if from=x
	    then x::[]
	    else from :: count(from+1)
    in
	count 1
    end
