class Person 
  attr_reader :age 
  attr_accessor :name 

  def initialize (name, ageVar) # CONSTRUCTOR 
    @name = name 
    self.age = ageVar # call the age= method 
    puts age 
  end 
  def age= (new_age) 
    @age = new_age unless new_age > 120 
  end 
end 

person1 = Person.new("Kim", 13) # => 13 
puts "My age is #{person1.age}" # => My age is 13 
person1.age = 130 # Try to change the age 
puts person1.age # => 13 (The setter didn't allow the change)
