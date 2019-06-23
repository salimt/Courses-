class Dog 
  def to_s 
    "Dog" 
  end 
  def bark 
    "barks loudly" 
  end 
end 
class SmallDog < Dog 
  def bark # Override 
    "barks quietly" 
  end 
end 

dog = Dog.new # (btw, new is a class method) 
small_dog = SmallDog.new 
puts "#{dog}1 #{dog.bark}" # => Dog1 barks loudly 
puts "#{small_dog}2 #{small_dog.bark}" # => Dog2 barks quietly 


