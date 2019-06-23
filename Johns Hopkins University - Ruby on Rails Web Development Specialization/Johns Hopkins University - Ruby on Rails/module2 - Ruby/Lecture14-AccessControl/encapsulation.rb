class Car
  def initialize(speed, comfort)
    @rating = speed * comfort
  end
   
  # Can't SET rating from outside
  def rating
    @rating
  end
end

puts Car.new(4, 5).rating # => 20



