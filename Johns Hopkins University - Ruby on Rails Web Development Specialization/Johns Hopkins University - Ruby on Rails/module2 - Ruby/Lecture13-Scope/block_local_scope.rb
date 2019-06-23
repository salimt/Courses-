arr = [5, 4, 1] 
cur_number = 10 
arr.each do |cur_number| 
  some_var = 10 # NOT available outside the block 
  print cur_number.to_s + " " # => 5 4 1 
end 
puts # print a blank line 
puts cur_number # => 10 

adjustment = 5 
arr.each do |cur_number;adjustment| 
  adjustment = 10 
  print "#{cur_number + adjustment} " # => 15 14 11 
end 
puts 
puts adjustment # => 5 (Not affected by the block)


