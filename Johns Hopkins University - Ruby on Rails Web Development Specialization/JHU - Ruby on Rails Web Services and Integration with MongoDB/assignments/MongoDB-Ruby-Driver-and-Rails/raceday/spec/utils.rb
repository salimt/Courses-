module Utils

    #helper method to determine if Ruby class exists as a class
    def Utils.class_exists?(class_name)
      eval("defined?(#{class_name}) && #{class_name}.is_a?(Class)") == true
    end

    #helper method to determine if two files are the same
    def Utils.files_same?(file1, file2) 
      if (File.size(file1) != File.size(file2)) then
        return false
      end
      f1 = IO.readlines(file1)
      f2 = IO.readlines(file2)
      if ((f1 - f2).size == 0) then
        return true
      else
        return false
      end
    end

    def Utils.checkMongoidConfigFile(location)
    	filelist = IO.readlines("#{location}/config/mongoid.yml")
    	hash = Hash.new
    	temp = ""
    	key = ""
    	filelist.each do |line| 
    		tline = line.lstrip.chomp
    		if (!tline.start_with?('#')) 
    			if (line.start_with?('development:')) 
    				if (key != "")  
    					hash[key] = temp
    				end
    				key = "development"
    				temp = "" 

    			elsif (line.start_with?('test:'))     				
    				if (key != "")  
    					hash[key] = temp
    				end
    				key = "test"
    				temp = ""
    			else 
    				temp = temp + line.chomp
    			end    			
    		end
    	end
    	if (key != "")
    		hash[key] = temp
    	end
    	return hash
   	end

    def Utils.fileHasText(file_name, text) 
        content = IO.readlines(file_name)
        content.each do | line |
            if line.match /text/ 
                return true
            end
        end
        return false
    end
end
