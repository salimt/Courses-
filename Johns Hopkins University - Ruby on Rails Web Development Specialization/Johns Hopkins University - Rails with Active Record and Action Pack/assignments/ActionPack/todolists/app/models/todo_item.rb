class TodoItem < ActiveRecord::Base
	def self.number_of_completed_todos
		self.where(completed: true).count
	end
end
