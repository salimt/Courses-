module Test 
  PI = 3.14 
  class Test2 
    def what_is_pi 
      puts PI 
    end 
  end 
end 
Test::Test2.new.what_is_pi # => 3.14 

module MyModule 
  MyConstant = 'Outer Constant' 
  class MyClass 
    puts MyConstant # => Outer Constant 
    MyConstant = 'Inner Constant' 
    puts MyConstant # => Inner Constant 
  end
  puts MyConstant # => Outer Constant 
end


