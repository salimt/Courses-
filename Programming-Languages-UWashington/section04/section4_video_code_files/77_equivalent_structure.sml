(* Programming Languages, Dan Grossman *)
(* Section 4: An Equivalent Structure *)

(* this signature hides gcd and reduce.  
That way clients cannot assume they exist or 
call them with unexpected inputs. *)
signature RATIONAL_A = 
sig
datatype rational = Frac of int * int | Whole of int
exception BadFrac
val make_frac : int * int -> rational
val add : rational * rational -> rational
val toString : rational -> string
end

(* the previous signature lets clients build 
 any value of type rational they
 want by exposing the Frac and Whole constructors.
 This makes it impossible to maintain invariants 
 about rationals, so we might have negative denominators,
 which some functions do not handle, 
 and print_rat may print a non-reduced fraction.  
 We fix this by making rational abstract. *)
signature RATIONAL_B =
sig
type rational (* type now abstract *)
exception BadFrac
val make_frac : int * int -> rational
val add : rational * rational -> rational
val toString : rational -> string
end
	
(* as a cute trick, it is actually okay to expose
   the Whole function since no value breaks
   our invariants, and different implementations
   can still implement Whole differently.
*)
signature RATIONAL_C =
sig
type rational (* type still abstract *)
exception BadFrac
val Whole : int -> rational 
   (* client knows only that Whole is a function *)
val make_frac : int * int -> rational
val add : rational * rational -> rational
val toString : rational -> string
end 

(* this structure can have all three signatures we gave
   Rationa1 in previous segments, and/but it is 
   /equivalent/ under signatures RATIONAL_B and RATIONAL_C 

   this structure does not reduce fractions until printing
*)
       
structure Rational2 :> RATIONAL_A (* or B or C *) =
struct
  datatype rational = Whole of int | Frac of int*int
  exception BadFrac

   fun make_frac (x,y) =
       if y = 0
       then raise BadFrac
       else if y < 0
       then Frac(~x,~y)
       else Frac(x,y)

   fun add (r1,r2) = 
       case (r1,r2) of
	   (Whole(i),Whole(j))   => Whole(i+j)
	 | (Whole(i),Frac(j,k))  => Frac(j+k*i,k)
	 | (Frac(j,k),Whole(i))  => Frac(j+k*i,k)
	 | (Frac(a,b),Frac(c,d)) => Frac(a*d + b*c, b*d)

   fun toString r =
       let fun gcd (x,y) =
	       if x=y
	       then x
	       else if x < y
	       then gcd(x,y-x)
	       else gcd(y,x)

	   fun reduce r =
	       case r of
		   Whole _ => r
		 | Frac(x,y) => 
		   if x=0
		   then Whole 0
		   else
		       let val d = gcd(abs x,y) in 
			   if d=y 
			   then Whole(x div d) 
			   else Frac(x div d, y div d) 
		       end
       in 
	   case reduce r of
	       Whole i   => Int.toString i
	     | Frac(a,b) => (Int.toString a) ^ "/" ^ (Int.toString b)
       end
end
