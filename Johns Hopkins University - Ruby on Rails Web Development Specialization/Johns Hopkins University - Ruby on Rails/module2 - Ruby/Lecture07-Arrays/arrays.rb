het_arr = [1, "two", :three] # heterogeneous types 
puts het_arr[1] # => two (array indices start at 0) 

arr_words = %w{ what a great day today! } 
puts arr_words[-2] # => day
puts "#{arr_words.first} - #{arr_words.last}" # => what - today! 
p arr_words[-3, 2] # => ["great", "day"] (go back 3 and take 2) 

# (Range type covered later...)
p arr_words[2..4] # => ["great", "day", "today!"] 

# Make a String out of array elements separated by ‘,’
puts arr_words.join(',') # => what,a,great,day,today!


