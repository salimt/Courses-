class Person 
  attr_reader :age 
  attr_accessor :name 
  
  def initialize (name, age) # CONSTRUCTOR 
    @name = name 
    self.age = age # call the age= method 
  end 
  def age= (new_age) 
    @age ||= 5 # default 
    @age = new_age unless new_age > 120 
  end 
end 
person1 = Person.new("Kim", 130) 
puts person1.age # => 5 (default) 
person1.age = 10 # change to 10 
puts person1.age # => 10 
person1.age = 200 # Try to change to 200 
puts person1.age # => 10 (still) 


