a = [1, 3, 4, 7, 8, 10] 
a.each { |num| print num } # => 1347810 (no new line) 
puts # => (print new line) 

new_arr = a.select { |num| num > 4 } 
p new_arr # => [7, 8, 10] 
new_arr = a.select { |num| num < 10 }
           .reject{ |num| num.even? } 
p new_arr # => [1, 3, 7] 

# Multiply each element by 3 producing new array
new_arr = a.map {|x| x * 3} 
p new_arr # => [3, 9, 12, 21, 24, 30] 

