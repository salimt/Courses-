editor_props = { "font" => "Arial", "size" => 12, "color" => "red"} 

# THE ABOVE IS NOT A BLOCK - IT'S A HASH 
puts editor_props.length # => 3 
puts editor_props["font"] # => Arial 

editor_props["background"] = "Blue" 
editor_props.each_pair do |key, value| 
  puts "Key: #{key} value: #{value}" 
end
# => Key: font value: Arial 
# => Key: size value: 12 
# => Key: color value: red 
# => Key: background value: Blue


