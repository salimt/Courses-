(* Programming Languages, Dan Grossman *)
(* Section 1: Our first ML program *)

(* val is a keyword
   x is a variable name
   = is used as a keyword here (has different meaning in expressions)
   34 is a very simple expression (and value)
   ; is used as a keyword here (has different meaning in expressions)
 *)
val x = 34;
(* static environment: x-->int *)
(* dynamic environment: x-->34 *)

val y = 17;
(* static environment: y-->int, x-->int *)
(* dynamic environment: y-->17, x-->34 *)

(* to evaluate an addition, evaluate the subexpressions and add *)
(* to evaluate a variable, lookup its value in the environment  *)

val z = (x + y) + (y + 2);
(* static environment: z-->int, y-->int, x-->int *)
(* dynamic environment: z-->70, y-->17, x-->34 *)

val q = z+1;
(* static environment: q-->int, z-->int, y-->int, x-->int *)
(* dynamic environment: q-->71, z-->70, y-->17, x-->34 *)
 
val abs_of_z = if z < 0 then 0 - z else  z;
(* static environment: abs_of_z-->int, q-->int, z-->int, y-->int, x-->int *)
(* dynamic environment: abs_of_z-->70, q-->71, z-->70, y-->17, x-->34 *)
 
val abs_of_z_simpler = abs z;
