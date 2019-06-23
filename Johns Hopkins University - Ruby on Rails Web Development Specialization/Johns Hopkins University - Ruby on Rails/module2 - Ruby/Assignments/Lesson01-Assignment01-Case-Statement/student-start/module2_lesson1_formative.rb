#@author: salimt

some_var = "false"
another_var = "nil"

case
	when another_var.nil? then puts "Question mark in the method name?"
	when some_var == "pink elephant" then puts "Don't think about the pink elephant!"
	when some_var == false then puts "Looks like this one should execute"
	else puts "I guess nothing matched... But why?"
	end
