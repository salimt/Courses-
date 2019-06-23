v1 = "outside"

class MyClass 
  def my_method
  	# p v1 EXCEPTION THROWN - no such variable exists
    v1 = "inside"
    p v1
    p local_variables 
  end 
end 

p v1 # => outside
obj = MyClass.new 
obj.my_method # => inside 
              # => [:v1] 
p local_variables # => [:v1, :obj]
p self # => main


