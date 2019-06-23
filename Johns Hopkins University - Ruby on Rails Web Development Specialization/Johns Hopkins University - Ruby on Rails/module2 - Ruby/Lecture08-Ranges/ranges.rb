some_range = 1..3 
puts some_range.max # => 3 
puts some_range.include? 2 # => true 

puts (1...10) === 5.3 # => true 
puts ('a'...'r') === "r" # => false (end-exclusive) 

p ('k'..'z').to_a.sample(2) # => ["k", "w"]
# or another random array with 2 letters in range

age = 55 
case age 
  when 0..12 then puts "Still a baby" 
  when 13..99 then puts "Teenager at heart!" 
  else puts "You are getting older..." 
end 
# => Teenager at heart!

