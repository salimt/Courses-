module Test_utils

	#helper method to determine if Ruby class exists as a class
    def class_exists?(class_name)
      eval("defined?(#{class_name}) && #{class_name}.is_a?(Class)") == true
    end

end
