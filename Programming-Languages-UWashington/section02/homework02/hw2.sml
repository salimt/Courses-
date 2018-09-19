(* @author: salimt *)


(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* Return NONE if the string is not in the list,
 else return SOME lst where lst is identical to the 
argument list except the string is not in it. *)
fun all_except_option(word: string, xs: string list)=
    let fun checker(word: string, xs: string list)=
	    case xs of
		[] => []
	      | (x::xs') => if x = word then checker(word, xs')
			    else x::checker(word,xs')
    in
	let val newlst = checker(word,xs)
	in if newlst=xs then NONE else SOME newlst
	end
    end

(* takes a string list list (a list of list of strings, the
substitutions) and a string s and returns a string list. *)
(* EXAMPLE: get_substitutions1([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]],"Fred");
   ANSWER: ["Fredrick","Freddie","F"] *)
fun get_substitutions1(xs: string list list, word: string)=    
    case xs of
	[] => []	  
      | x::xs' => let val restlst = xs'
		  in
		      case all_except_option(word,x) of
			  NONE => get_substitutions1(restlst, word)
		        | SOME x => x @ get_substitutions1(restlst, word)
		  end

(* same function as above except this uses a tail-recursive local helper function. *)	      
fun get_substitutions2(xs: string list list, word: string)=
    let fun merge(xs: string list, word: string)=
	    case all_except_option(word,xs) of
		NONE => []
	      | SOME x => x
    in
	case xs of
	    [] => []
	  | x::xs' => merge(x,word) @ get_substitutions2(xs', word)
    end

(* returns a list of full names (type {first:string,middle:string,last:string} list). *)
(* EXAMPLE: similar_names([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]],{first="Fred", middle="W", last="Smith"});
   ANSWER: [{first="Fred", last="Smith", middle="W"},
            {first="Fredrick", last="Smith", middle="W"},
            {first="Freddie", last="Smith", middle="W"},
            {first="F", last="Smith", middle="W"}] *)
fun similar_names(namelst: string list list, data: {first:string,middle:string,last:string})=	
    let fun recc(names: string list)=
	    case names of
		[] => nil
	      | x::names' => {first= x, last= #last data, middle= #middle data} :: recc(names')
    in
	{first= #first data, last= #last data, middle= #middle data} :: recc(get_substitutions2(namelst, #first data))
    end




	
(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* takes a card and returns its color (spades and clubs are black,
diamonds and hearts are red) *)
fun card_color(c: card)=
    case #1 c of
	Clubs  => Black
      | Spades => Black
      | _ => Red 

(* takes a card and returns its value (numbered cards have their
number as the value, aces are 11, everything else is 10) *)
fun card_value(c: card)=
    case #2 c of
	Ace => 11
      | Num(i2) => i2
      | _ => 10

(* takes a list of cards cs, a card c, and an exception e. It returns a
list that has all the elements of cs except c. If c is in the list more than once, remove only the first one.
If c is not in the list, raise the exception e. *)
fun remove_card(cs: card list, c: card, e: exn)=
    case cs of
	[] => raise e
      | x::cs' => if   x = c
		  then cs'
		  else remove_card(cs', c, e)

(* takes a list of cards and returns true if all the cards in the
list are the same color *)
fun all_same_color(cs: card list)= 
    case cs of
	[] => true
      | x::[] => true
      | (head::neck::rest) => if card_color(head) = card_color(neck)
			      then all_same_color(neck::rest)
                              else false

(* takes a list of cards and returns the sum of their values *)
fun sum_cards(cs: card list)=
    case cs of
	[] => 0
      | x::cs' => card_value(x) + sum_cards(cs') 

(* takes a card list (the held-cards) and an int (the goal) and computes
the score as described above. *)
fun score(cs: card list, n: int)=
    let val sum = sum_cards(cs)
	val color_s = all_same_color(cs)		    
    in
	if   sum > n andalso color_s 
	then (3 * (sum-n)) div 2
	else if sum > n andalso color_s = false
	then 3 * (sum-n)
	else if color_s
	then (n - sum) div 2
	else n - sum

    end

(* takes a card list (the card-list) a move list
(what the player \does" at each point), and an int (the goal) and returns the score at the end of the
game after processing (some or all of) the moves in the move list in order. *)
fun officiate(cs: card list, ms: move list, goal: int)=								 
    let fun play_game(_, [], heldcards) = score(heldcards, goal)
	  | play_game([], Draw::ms, heldcards) = score(heldcards, goal)
	  | play_game(cs, Discard x::ms, heldcards) =
	    play_game(cs, ms, remove_card(heldcards, x, IllegalMove))
	  | play_game(cs, Draw::ms, heldcards) = case cs of
						     [] => score(heldcards, goal)
						   | x::cs' => if   score(x::heldcards, goal) > goal
							       then score(x::heldcards, goal)
							       else play_game(remove_card(cs, x, IllegalMove), ms, x::heldcards)	     
    in
	play_game(cs, ms, [])
    end
	
