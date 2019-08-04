require_relative '../config/environment'

describe "Assignment" do

    before :all do
        $continue = true
    end

    around :each do |example|
        if $continue
            $continue = false 
            example.run 
            $continue = true unless example.exception
        else
            example.skip
        end
    end

    #helper method to determine if Ruby class exists as a class
    def class_exists?(class_name)
      eval("defined?(#{class_name}) && #{class_name}.is_a?(Class)") == true
    end

    #helper method to determine if two files are the same
    def files_same?(file1, file2) 
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

    context "rq01" do
      context "Generate Rails application" do
        it "must have top level structure of a rails application" do
          expect(File.exists?("Gemfile")).to be(true)
          expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
          expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
        end
      end
    end

    context "rq02" do # Re-use 'User' model and database table
      context "User Model:" do
        it "User class created" do
          expect(class_exists?("User"))
          expect(User < ActiveRecord::Base).to eq(true)        
        end
        context "User class properties added" do
          subject(:user) { User.new }
          it { is_expected.to respond_to(:username) } 
          it { is_expected.to respond_to(:password_digest) } 
          it { is_expected.to respond_to(:created_at) } 
          it { is_expected.to respond_to(:updated_at) } 
        end
        it "User database structure in place" do
          # rails g model user username password_digest 
          # rake db:migrate
          expect(User.column_names).to include "password_digest", "username"
          expect(User.column_types["username"].type).to eq :string
          expect(User.column_types["password_digest"].type).to eq :string
          expect(User.column_types["created_at"].type).to eq :datetime
          expect(User.column_types["updated_at"].type).to eq :datetime
        end
      end
    end

    context "rq03" do # Re-use and update 'Profile' model and database table
      context "Profile Model:" do
        it "Profile class created" do
          expect(class_exists?("Profile"))
          expect(Profile < ActiveRecord::Base).to eq(true)
        end
        context "Profile class properties added" do
          subject(:profile) { Profile.new }
          it { is_expected.to respond_to(:gender) } 
          it { is_expected.to respond_to(:birth_year) } 
          it { is_expected.to respond_to(:first_name) } 
          it { is_expected.to respond_to(:last_name) } 
          it { is_expected.to respond_to(:user) } 
          it { is_expected.to respond_to(:created_at) } 
          it { is_expected.to respond_to(:updated_at) } 
        end
        it "Profile database structure in place" do
          # rails g model profile gender birth_year:integer first_name last_name 
          # rake db:migrate
          expect(Profile.column_names).to include "gender", "birth_year", "first_name", "last_name"
          expect(Profile.column_types["gender"].type).to eq :string
          expect(Profile.column_types["birth_year"].type).to eq :integer
          expect(Profile.column_types["first_name"].type).to eq :string
          expect(Profile.column_types["last_name"].type).to eq :string
          expect(User.column_types["created_at"].type).to eq :datetime
          expect(User.column_types["updated_at"].type).to eq :datetime
        end
        it "Profile 1:1 belongs_to relationship to User in place" do
          expect(Profile.reflect_on_association(:user).macro).to eq :belongs_to
        end
        it "User 1:1 has_one relationship to Profile in place" do
          expect(User.reflect_on_association(:profile).macro).to eq :has_one
        end
      end
    end

    context "rq04" do # Re-use and update 'TodoList' model and database table
      context "TodoList Model" do
        it "TodoList class" do
          expect(class_exists?("TodoList"))
          expect(TodoList < ActiveRecord::Base).to eq(true)
       end
      end
      context "TodoList class properties added" do
        subject(:todolist) { TodoList.new }
        it { is_expected.to respond_to(:list_due_date) } 
        it { is_expected.to respond_to(:list_name) } 
        it { is_expected.to respond_to(:created_at) } 
        it { is_expected.to respond_to(:updated_at) } 
      end
      it "TodoList database structure in place" do
        # rails g model todo_list list_name list_due_date:date         s
        # rake db:migrate
        expect(TodoList.column_names).to include "list_name", "list_due_date"
        expect(TodoList.column_types["list_name"].type).to eq :string
        expect(TodoList.column_types["list_due_date"].type).to eq :date
        expect(TodoList.column_types["created_at"].type).to eq :datetime
        expect(TodoList.column_types["updated_at"].type).to eq :datetime
      end
    end

    context "rq05" do #add migration for TodoList -> User relationship
      it "TodoList has a many:1 belongs_to relationship with User" do
        expect(TodoList.reflect_on_association(:user).macro).to eq :belongs_to
      end
      it "User has a 1:many has_many relationship with TodoList" do
        expect(User.reflect_on_association(:todo_lists).macro).to eq :has_many
      end
    end

    context "rq06" do # Re-use and update 'TodoItem' model and database table
      context "TodoItem Model" do
        it "TodoItem class" do
          expect(class_exists?("TodoItem"))
          expect(TodoItem < ActiveRecord::Base).to eq(true)
        end
      end
      context "TodoItem class properties added" do
        subject(:todoItem) { TodoItem.new }
        it { is_expected.to respond_to(:due_date) } 
        it { is_expected.to respond_to(:title) } 
        it { is_expected.to respond_to(:completed) }
        it { is_expected.to respond_to(:description) } 
        it { is_expected.to respond_to(:created_at) } 
        it { is_expected.to respond_to(:updated_at) } 
      end
      it "TodoItem database structure in place" do
        # rails g model todo_item due_date:date title description:text         
        # rake db:migrate
        expect(TodoItem.column_names).to include "due_date", "title", "description", "completed"
        expect(TodoItem.column_types["due_date"].type).to eq :date
        expect(TodoItem.column_types["title"].type).to eq :string
        expect(TodoItem.column_types["description"].type).to eq :text            
        expect(TodoItem.column_types["created_at"].type).to eq :datetime
        expect(TodoItem.column_types["updated_at"].type).to eq :datetime
        expect(TodoItem.column_types["completed"].type).to eq :boolean
      end
      it "TodoItem has a many:1 belongs_to relationship with TodoList" do
        expect(TodoItem.reflect_on_association(:todo_list).macro).to eq :belongs_to
      end         
      it "TodoList has a 1:many has_many relationship with TodoItem" do
        expect(TodoList.reflect_on_association(:todo_items).macro).to eq :has_many
      end         
      it "TodoItem will be destroyed when TodoList deleted" do
         expect(TodoList.reflect_on_association(:todo_items).options[:dependent]).to eq :destroy
      end         
    end

    context "rq08" do # Re-use and update 'TodoItem' model and database table
      it "User has a 1:many relationship with TodoItem" do
        expect(User.reflect_on_association(:todo_items).macro).to eq :has_many
      end         
      it "User has a 1:many relationship with TodoItem through TodoList" do
        expect(User.reflect_on_association(:todo_items).options[:through]).to eq :todo_lists  
        expect(User.reflect_on_association(:todo_items).options[:source]).to eq :todo_items  
      end         
    end

    context "rq09" do
      context "check seed file" do
        user_list = [
              ["Carly", "Fiorina", "female", 1954],
              ["Donald", "Trump", "male", 1946],
              ["Ben", "Carson", "male", 1951],
              ["Hillary", "Clinton", "female", 1947]
        ]

        before do
          User.destroy_all
          TodoList.destroy_all
          TodoItem.destroy_all
          Profile.destroy_all
          load "#{Rails.root}/db/seeds.rb"
        end

        it "has a file for seeding the database" do
          expect(File).to exist("#{Rails.root}/db/seeds.rb")
        end
        it "must have Users with lastnames for usernames as directed in assignment" do
          expect(User.all.to_a.length).to be(4)
          expect(User.all.map{ |x| x.username }).to include("Trump", "Fiorina", "Carson", "Clinton")
        end

        it "must have Profiles set up for each user with the given data" do 
          expect(Profile.all.length).to be(4)
          user_list.each do | fname, lname, gender, byear | 
            p = Profile.find_by(last_name: lname)
            expect(p.first_name).to eql(fname)
            expect(p.gender).to eql(gender)
            expect(p.birth_year).to eql(byear)
          end
        end

        it "must have TodoList set up as directed" do
          expect(TodoList.all.length).to be(4)
          user_list.each do | fname, lname, gender, byear |
            expect(TodoList.find_by(user: User.find_by(username: lname))).to_not be_nil
          end
        end        

        it "must have TodoItems set up as directed" do
          expect(TodoItem.all.length).to be(20)
          user_list.each do | fname, lname, gender, byear |
            user = User.find_by(username: lname)
            expect(user.todo_items.count).to be(5)
          end
        end
      end
    end

    context "rq10" do
      it "Default scope on TodoItem should result in collection in ascending order by due_date" do
        user = User.create(:username=>"testUser", :password_digest=>"xxxx")
        tdlist = TodoList.create(:list_name=>"my list", :list_due_date=>Date.today, :user_id=>user.id)
        dateRange = 100
        dateTrack = Date.today
        (0..20).each do |i|
            dateTrack = Date.today + rand(0..dateRange).day
            TodoItem.create(:due_date=>dateTrack, :title=>"My Item #{i}", :description=>"Item Details #{i}", :todo_list_id=>tdlist.id)
        end
        todoItemGroup = TodoItem.all
        # test that results are sorted by updated_at and are ascending
        lastTime = Date.today
        todoItemGroup.each do |t| 
          expect(t.due_date).to be >= lastTime
          lastTime = t.due_date
        end
      end

      it "Default scope on TodoList should result in collection in ascending order by list_due_date" do
        user = User.create(:username=>"testUser", :password_digest=>"xxxx")
        dateRange = 100
        dateTrack = Date.today
        (0..20).each do |i|
          dateTrack = Date.today + rand(0..dateRange).day
          TodoList.create(:list_name=>"my list #{i}", :list_due_date=>dateTrack, :user_id=>user.id)
        end
        todoListGroup = TodoList.all
        # test that results are sorted by updated_at and are ascending
        lastTime = Date.today
        todoListGroup.each do |t| 
          expect(t.list_due_date).to be >= lastTime
          lastTime = t.list_due_date
        end
      end
    end

    context "rq11" do
      context "Validators:" do
        it "does not allow a User without a username" do
          expect(User.new(:username=> "")).to_not be_valid
        end

        it "does not allow a Profile with a null first and last name" do
          expect(Profile.new(:first_name=>nil, :last_name=>nil, :gender=>"male")).to_not be_valid
        end
        it "allows a Profile with a null first name when last name present" do
          expect(Profile.new(:first_name=>nil, :last_name=>"Smith", :gender=>"male")).to be_valid
        end
        it "allows a Profile with a null last name when first name present" do
          expect(Profile.new(:first_name=>"Joe", :last_name=>nil, :gender=>"male")).to be_valid
        end

        it "does not allow a Profile with a gender other than male or female " do
          expect(Profile.new(:first_name=>"first", :last_name=>"last", :gender=>"neutral")).to_not be_valid
        end
        it "does not allow a boy named Sue" do
          expect(Profile.new(:first_name=>"Sue", :last_name=>"last", :gender=>"male")).to_not be_valid
        end
        it "allows a Profile with gender male" do
          expect(Profile.new(:first_name=>"first", :last_name=>"last", :gender=>"male")).to be_valid
        end
        it "allows a Profile with gender female" do
          expect(Profile.new(:first_name=>"first", :last_name=>"last", :gender=>"female")).to be_valid
        end
      end
    end

    context "check database operations" do
      # We will only test User and TodoList for this assignment
      before do
        User.destroy_all
        TodoList.destroy_all
        TodoItem.destroy_all
        Profile.destroy_all
      end
 
      context "rq12" do
        it "Cascading delete is represented in model dependencies" do
          expect(User.reflect_on_association(:profile).options[:dependent]).to eq :destroy
          expect(User.reflect_on_association(:todo_lists).options[:dependent]).to eq :destroy
          expect(TodoList.reflect_on_association(:todo_items).options[:dependent]).to eq :destroy
        end
        it "User delete must cascade delete to Profile, TodoList and TodoItems" do
          user = User.create(:username=>"testUser", :password_digest=>"xxxx")
          profile = Profile.create(:first_name=>"Joe", :last_name=>"Smith", :birth_year=>2000, :gender=>"male", :user_id=>user.id)
          tdlist = TodoList.create(:list_name=>"my list", :list_due_date=>Date.today, :user_id=>user.id)
          tditem = TodoItem.create(:due_date=>Date.today, :title=>"My Item", :description=>"Item Details", :todo_list_id=>tdlist.id)
          expect(User.find_by(id: user.id)).to_not be_nil           
          expect(Profile.find_by(id: profile.id)).to_not be_nil           
          expect(TodoList.find_by(id: tdlist.id)).to_not be_nil           
          expect(TodoItem.find_by(id: tditem.id)).to_not be_nil     
          # remove user with method and test that all related objects are nil
          lastId = user.id
          user.destroy
          # test that user and all other objects link to that user no longer exist
          expect(User.find_by(id: lastId)).to be_nil           
          expect(Profile.find_by(id: profile.id)).to be_nil           
          expect(TodoList.find_by(id: tdlist.id)).to be_nil           
          expect(TodoItem.find_by(id: tditem.id)).to be_nil  
        end
      end

      context "rq13" do
        context "User has a get_completed_count method" do
          subject(:user) { User.new }
          it { is_expected.to respond_to(:get_completed_count) }
        end
        it "Will return the number of completed todo_items for a specified user" do
          user = User.create(:username=>"testUser", :password_digest=>"xxxx")
          tdlist1 = TodoList.create(:list_name=>"my list 1", :list_due_date=>Date.today, :user_id=>user.id)
          tdlist2 = TodoList.create(:list_name=>"my list 2", :list_due_date=>Date.today, :user_id=>user.id)
          completeCount = 0
          (0..10).each do |i|
            cval = [true, false].sample
            if cval 
              completeCount = completeCount + 1
            end
            todoItem = TodoItem.create(:completed=>cval, :due_date=>Date.today, :title=>"My List 1 Item #{i}", :description=>"Item Details #{i}", :todo_list_id=>tdlist1.id)
            cval = [true, false].sample
            if cval 
              completeCount = completeCount + 1
            end
            todoItem = TodoItem.create(:completed=>cval, :due_date=>Date.today, :title=>"My List 2 Item #{i}", :description=>"Item Details #{i}", :todo_list_id=>tdlist2.id)
          end
          expect(user.get_completed_count).to eq completeCount
        end
      end

      context "rq14 - Profile" do
        it "should have a get_all_profiles class method" do
          expect(Profile).to respond_to(:get_all_profiles)
        end
        it "will return a list of profiles between requested birth years in ascending order" do
          user = User.create(:username=>"testUser", :password_digest=>"xxxx")
          startYear = 1960
          endYear = 2000
          testYear = 1985
          testCount = 0
          (0..20).each do |i|
            birthYear = startYear + rand(0..(endYear - startYear))
            if (birthYear <= testYear)
              testCount = testCount + 1
            end
            profile = Profile.create(:user_id=>user.id, :gender=>"male", :birth_year=>birthYear, :first_name=>"User #{i}", :last_name=>"Smith#{i}")
          end
          profileGroup = Profile.get_all_profiles(startYear, testYear)
          expect(profileGroup.length).to be(testCount)
          # test that results are sorted by birthyear and are ascending
          year = startYear
          profileGroup.each do |t| 
            expect(t.birth_year).to be >= year
            year = t.birth_year
          end
        end
      end
    end
end
