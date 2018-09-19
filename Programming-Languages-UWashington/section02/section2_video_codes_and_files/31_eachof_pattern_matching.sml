(* Programming Languages, Dan Grossman *)
(* Section 2: Pattern-Matching for Each-Of Types: 
              The Truth About Function Arguments *)

fun sum_triple1 (triple : int * int * int) =
    case triple of
      (x,y,z) => z + y + x

fun full_name1 (r : {first:string,middle:string,last:string}) =
    case r of 
        {first=x,middle=y,last=z} => x ^ " " ^ y ^ " " ^z

fun full_name2 (r : {first:string,middle:string,last:string}) =
    let val {first=x,middle=y,last=z} = r
    in
	x ^ " " ^ y ^ " " ^z
    end

fun sum_triple2 triple =
    let val (x,y,z) = triple 
    in 
        x + y + z 
    end

fun full_name3 {first=x,middle=y,last=z} =
    x ^ " " ^ y ^ " " ^z

fun sum_triple3 (x,y,z) =
    x + y + z

fun rotate_left (x,y,z) = (y,z,x)

fun rotate_right triple = rotate_left(rotate_left triple)
