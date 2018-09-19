(* @author: salimt *)

(* 01 *)
(* compares two dates first by year then month and lastly by day if equal return false *)
fun is_older(fDate: int*int*int, comparedDate: int*int*int) =
    if fDate = comparedDate
    then false     
    else if #1 fDate > #1 comparedDate orelse (#1 fDate = #1 comparedDate andalso
					       #2 fDate > #2 comparedDate) orelse
                                              (#1 fDate = #1 comparedDate andalso
					      (#2 fDate > #2 comparedDate orelse
					       #2 fDate = #2 comparedDate andalso
					       #3 fDate > #3 comparedDate))                       
    then false
    else true
	     
(* 02 *)
(* returns how many dates in the list are in the given month. *)
fun number_in_month(dates: (int*int*int) list, month: int) =
    if null dates
    then 0
    else
	(if #2 (hd dates) = month then 1 else 0) + number_in_month (tl dates,month);

(* 03 *)
(* returns the number of dates in the list of dates that are in any of the months in the list of months. *)
fun number_in_months(dates: (int*int*int) list, months: int list)=
    if null dates orelse null months
    then 0
    else
	number_in_month(dates, hd months) + number_in_months(dates, tl months);

(* 04 *)
(* returns a list holding the dates from the argument list of dates that are in the month. *)
fun dates_in_month(dates: (int*int*int) list, month: int)=
    if null dates
    then []
    else
	(if #2 (hd dates) = month then hd dates::[] else []) @ dates_in_month(tl dates,month)

(* 05 *)									     
(* returns a list holding the dates from the argument list of dates that are in any of the months in
the list of months. *)								     
fun dates_in_months(dates: (int*int*int) list, months: int list)=
    if null dates orelse null months
    then []
    else
	dates_in_month(dates, hd months) @ dates_in_months(dates, tl months);

(* 06 *)
(* returns the nth element of the list where the head of the list is first *)
fun get_nth(L: string list, n: int)=
    List.nth(L, n-1);

(* 07 *)
(* takes a date and returns a string of the form January 20, 2013(for example) *)
fun date_to_string(date: int*int*int)=
    let
	val months = ["January", "February", "March", "April","May", "June", "July", "August", "Septembr", "October", "November", "December"]
    in
	get_nth(months, #2 date)^" "^Int.toString(#3 date)^", "^Int.toString(#1 date)
    end

(* 08 *)	
(*  return an int n such that the first n elements of the list add to less than sum, but the first n+1 elements of the list add to sum or more. *)
fun number_before_reaching_sum(sum: int, L: int list)=
    if null L
    then 0
    else
	if   sum <= hd L
	then 0
	else 1 + number_before_reaching_sum(sum-hd L, tl L)

(* 09 *)					   
(* returns what month that day is in (1 for January, 2 for February, etc.).  *)
fun what_month(m: int)=
    let
	val days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    in
	1 + number_before_reaching_sum(m, days)
    end
	
(* 10 *)
(* returns an int list [m1,m2,...,mn] where m1 is the month of day1, m2 is the month of day1+1, ..., and mn is the month of day day2.  *)
fun month_range(firstDay: int, secondDay: int)=
    if firstDay > secondDay
    then []
    else
	let fun count (from:int) =
		if   from=secondDay
		then what_month(from)::[]
		else what_month(from)::count(from+1)
	in
	    count firstDay
	end
	    
(* 11 *)
(* evaluates to NONE if the list has no dates and SOME d if the date d is the oldest date in the list. *)
fun oldest(dates: (int*int*int) list)=
    if null dates
    then NONE
    else let fun find_oldest(firstDate: int*int*int, otherDates: (int*int*int) list)= 
		 if      null otherDates
		 then    SOME firstDate
		 else if is_older(firstDate, hd otherDates)
		 then    find_oldest(firstDate, tl otherDates)
		 else    find_oldest(hd otherDates, tl otherDates)
	 in
	     find_oldest(hd dates, dates)
	 end

	     

(* CHALLENGE PROBLEMS *)
	     
(* checks if the given value is in the given list "L" *)
fun is_in(num: int, L: int list)=
    if null L
    then 0
    else
	(if num = hd L then 1 else 0) + is_in (num,tl L);
		  	
(* removes all the duplicate ints in the given list and returns int list with unique ints *)
fun remove_duplicates(L: int list)=
    if null L
    then []
    else
	let val newList = remove_duplicates(tl L)
	in if is_in(hd L, newList) = 0
	   then hd L::newList
	   else newList
	end
    
(* 12 *)
(* like  problems 3 and 5 except having a month in the second argument multiple times has no more effect than having it once. *)
fun number_in_months_challenge(dates: (int*int*int) list, months: int list)=
    number_in_months(dates, remove_duplicates(months))

fun dates_in_months_challenge(dates : (int * int * int) list, months : int list) =
    dates_in_months(dates, remove_duplicates(months))

(* 13 *)
(* takes a date and determines if it describes a real date in the common era.  *)
fun reasonable_date(date: int*int*int)=
    if #1 date <= 0 orelse #2 date <= 0 orelse #3 date <= 0
    then false
    else
	let
	    val leap_year_days = [31,29,31,30,31,30,31,31,30,31,30,31]
	    val common_year_days = [31,28,31,30,31,30,31,31,30,31,30,31]
	in
	    if (#1 date >= 400 andalso #1 date mod 400 = 0) orelse
	       (#1 date >= 4 andalso #1 date mod 4 = 0 andalso #1 date mod 100 <> 0)
	    then List.nth(leap_year_days, #2 date-1) >= #3 date
	    else List.nth(common_year_days, #2 date-1) >= #3 date
	end
	
    
    

	     
	     
	     
	     
	
	
    
    
	    
	     

	     
	    

					      
