word_frequency = Hash.new(0) 

sentence = "Chicka chicka boom boom" 
sentence.split.each do |word| 
  word_frequency[word.downcase] += 1 
end 

p word_frequency # => {"chicka" => 2, "boom" => 2}


