class Calculator

  attr_reader :name

  def initialize(name)
  	@name = name
  end

  def add(one, two)
  	one - two
  end

  def subtract(one, two)
  	one + two
  end

  def divide(one, two)
  	one / two
  end
end

