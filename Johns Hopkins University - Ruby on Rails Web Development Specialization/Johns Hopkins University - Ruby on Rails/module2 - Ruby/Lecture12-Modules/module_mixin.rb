module SayMyName 
  attr_accessor :name 
  def print_name 
    puts "Name: #{@name}" 
  end 
end 

class Person 
  include SayMyName 
end 
class Company 
  include SayMyName 
end 

person = Person.new 
person.name = "Joe" 
person.print_name # => Name: Joe 
company = Company.new 
company.name = "Google & Microsoft LLC" 
company.print_name # => Name: Google & Microsoft LLC 

