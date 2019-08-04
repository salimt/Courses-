class Profile < ActiveRecord::Base
	belongs_to :user

	validate :both_cannot_be_null
	validate :male_or_female 
	validate :sue_cannot_be_male

	def self.get_all_profiles(min_year, max_year)
		self.where("birth_year BETWEEN ? and ?", min_year, max_year).order(:birth_year).to_a
	end


	def both_cannot_be_null
		if first_name == nil and last_name == nil
			errors.add(:first_name, "both cannot be both null at the same time!")
		end
	end

	def male_or_female
		if gender.in?(["male", "female"]) == false
			errors.add(:gender, "choose male or female")
		end
	end

	def sue_cannot_be_male
		if first_name == "Sue"
			errors.add(:first_name, "Sue cannot be a man's name!")
		end
		
	end

end
