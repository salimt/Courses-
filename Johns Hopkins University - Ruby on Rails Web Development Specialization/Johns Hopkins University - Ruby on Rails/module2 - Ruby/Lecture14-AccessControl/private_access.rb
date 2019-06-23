class Person
  def initialize(age)
  	self.age = age # LEGAL - EXCEPTION
  	puts my_age
  	# puts self.my_age # ILLEGAL
  	                   # CANNOT USE self or any other receiver
  end

  private 
    def my_age
    	@age
    end
    def age=(age)
      @age = age
    end
end

Person.new(25) # => 25


