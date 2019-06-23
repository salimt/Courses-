class MathFunctions 
  def self.double(var) # 1. Using self 
    times_called; var * 2; 
  end 
  class << self # 2. Using << self 
    def times_called 
      @@times_called ||= 0; @@times_called += 1 
    end 
  end 
end 
def MathFunctions.triple(var) # 3. Outside of class 
  times_called; var * 3 
end

# No instance created! 
puts MathFunctions.double 5 # => 10 
puts MathFunctions.triple(3) # => 9 
puts MathFunctions.times_called # => 3 
