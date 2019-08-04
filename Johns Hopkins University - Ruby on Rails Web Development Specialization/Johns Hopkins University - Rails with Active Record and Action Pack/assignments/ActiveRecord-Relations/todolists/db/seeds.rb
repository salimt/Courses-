# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

User.destroy_all; Profile.destroy_all; TodoList.destroy_all; TodoItem.destroy_all


user1= ["Carly", "Fiorina", 1954, "female", "#1"]
user2= ["Donald", "Trump", 1946, "male", "#2"]
user3= ["Ben", "Carson", 1951, "male", "#3"]
user4= ["Hillary", "Clinton", 1947, "female", "#4"]
datePlusOneYear = Date.today+1.year
pass = "no_pass"

for user in [user1, user2, user3, user4]
	regUser = User.create! username: user[1], password_digest: pass
	profile = Profile.create! first_name: user[0], last_name: user[1], birth_year: user[2], gender: user[3], user_id: user[4]
	list = TodoList.create! list_name: user[1]+"List", list_due_date: datePlusOneYear 
	regUser.profile = profile
	regUser.todo_lists << list
	for num in (1..5)
		item = TodoItem.create! due_date: datePlusOneYear, title: "#{user[0]} Item#{num}", description: "I#{num}", completed: false
		list.todo_items << item
	end
end