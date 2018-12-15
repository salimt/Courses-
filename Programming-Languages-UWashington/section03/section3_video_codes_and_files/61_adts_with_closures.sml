(* Programming Languages, Dan Grossman *)
(* Section 3: Optional: Abstract Data Types with Closures *)

(* a set of ints with three operations *)
(* this interface is immutable -- insert returns a new set -- but we could
   also have implemented a mutable version using ML's references *)
(* Note: a 1-constructor datatype is an SML trick for recursive types *)
datatype set = S of { insert : int -> set, 
		      member : int -> bool, 
		      size   : unit -> int }

(* implementation of sets: this is the fancy stuff, but clients using
   this abstraction do not need to understand it *)
val empty_set =
    let
        fun make_set xs = (* xs is a "private field" in result *)
            let (* contains a "private method" in result *)
                fun contains i = List.exists (fn j => i=j) xs
            in
                S { insert = fn i => if contains i 
                                     then make_set xs 
                                     else make_set (i::xs),
                    member = contains,
                    size   = fn () => length xs
                  }
            end
    in
        make_set []
    end 

(* example client *)
fun use_sets () =
    let val S s1 = empty_set
        val S s2 = (#insert s1) 34
        val S s3 = (#insert s2) 34
        val S s4 = #insert s3 19
    in
        if (#member s4) 42
        then 99
        else if (#member s4) 19
        then 17 + (#size s3) ()
        else 0
    end 
