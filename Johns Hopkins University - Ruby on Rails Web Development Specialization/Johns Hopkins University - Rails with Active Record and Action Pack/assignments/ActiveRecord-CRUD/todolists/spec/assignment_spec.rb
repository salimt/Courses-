require_relative '../assignment/assignment'


describe Assignment do
    subject(:assignment) { Assignment.new }

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

    context "rq02" do
      # Test to make sure model objects exist and are ActiveRecord objects
      context "Generate four(4) Model Classes and Properties" do
        context "rq02.1 User Model" do
          it "User class implemented" do
            expect(class_exists?("User"))
            expect(User < ActiveRecord::Base).to eq(true)
          end
          context "User class properties defined" do
            subject(:user) { User.new }
            it { is_expected.to respond_to(:username) } 
            it { is_expected.to respond_to(:password_digest) } 
            it { is_expected.to respond_to(:created_at) } 
            it { is_expected.to respond_to(:updated_at) } 
          end
          it "User database structure in place" do
            expect(User.column_names).to include "password_digest", "username"
            expect(User.column_types["username"].type).to eq :string
            expect(User.column_types["password_digest"].type).to eq :string
            expect(User.column_types["created_at"].type).to eq :datetime
            expect(User.column_types["updated_at"].type).to eq :datetime
          end
        end
        context "rq02.2 Profile Model" do
          it "Profile class implemented" do
            expect(class_exists?("Profile"))
            expect(Profile < ActiveRecord::Base).to eq(true)
          end
          context "Profile class properties defined" do
            subject(:profile) { Profile.new }
            it { is_expected.to respond_to(:gender) } 
            it { is_expected.to respond_to(:birth_year) } 
            it { is_expected.to respond_to(:first_name) } 
            it { is_expected.to respond_to(:last_name) } 
            it { is_expected.to respond_to(:created_at) } 
            it { is_expected.to respond_to(:updated_at) } 
          end
          it "Profile database structure in place" do
            expect(Profile.column_names).to include "gender", "birth_year", "first_name", "last_name"
            expect(Profile.column_types["gender"].type).to eq :string
            expect(Profile.column_types["birth_year"].type).to eq :integer
            expect(Profile.column_types["first_name"].type).to eq :string
            expect(Profile.column_types["last_name"].type).to eq :string
            expect(User.column_types["created_at"].type).to eq :datetime
            expect(User.column_types["updated_at"].type).to eq :datetime
          end
        end
        context "rq02.3 TodoList Model" do
          it "TodoList class implemented" do
            expect(class_exists?("TodoList"))
            expect(TodoList < ActiveRecord::Base).to eq(true)
          end
          context "TodoList class properties defined" do
            subject(:todolist) { TodoList.new }
            it { is_expected.to respond_to(:list_due_date) } 
            it { is_expected.to respond_to(:list_name) } 
            it { is_expected.to respond_to(:created_at) } 
            it { is_expected.to respond_to(:updated_at) } 
          end
          it "TodoList database structure in place" do
            # rails g model todo_list list_name list_due_date:date
            # rake db:migrate
            expect(TodoList.column_names).to include "list_name", "list_due_date"
            expect(TodoList.column_types["list_name"].type).to eq :string
            expect(TodoList.column_types["list_due_date"].type).to eq :date
            expect(TodoList.column_types["created_at"].type).to eq :datetime
            expect(TodoList.column_types["updated_at"].type).to eq :datetime
          end 
        end
        context "rq02.4 TodoItem Model" do
          it "TodoItem class implemented" do
            expect(class_exists?("TodoItem"))
            expect(TodoItem < ActiveRecord::Base).to eq(true)
          end
          context "TodoItem class properties defined" do
            subject(:todolist) { TodoItem.new }
            it { is_expected.to respond_to(:due_date) } 
            it { is_expected.to respond_to(:title) } 
            it { is_expected.to respond_to(:description) } 
            it { is_expected.to respond_to(:completed)}
            it { is_expected.to respond_to(:created_at) } 
            it { is_expected.to respond_to(:updated_at) } 
          end
          it "TodoItem database structure in place" do
            # rails g model todo_item due_date:date title description:text completed:boolean
            # rake db:migrate
              expect(TodoItem.column_names).to include "due_date", "title", "description", "completed"
            expect(TodoItem.column_types["due_date"].type).to eq :date
            expect(TodoItem.column_types["title"].type).to eq :string
            expect(TodoItem.column_types["description"].type).to eq :text            
            expect(TodoItem.column_types["completed"].type).to eq :boolean
            expect(TodoItem.column_types["created_at"].type).to eq :datetime
            expect(TodoItem.column_types["updated_at"].type).to eq :datetime
          end    
        end
      end
    end

    context "rq03" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq03.1 assignment code has create_user method" do
        it { is_expected.to respond_to(:create_user) } 
        it "should create_user with provided parameters" do
            expect(User.find_by username: "joesmith").to be_nil
            assignment.create_user(:username=> 'joesmith', :password_digest=>'xxx')
            testUser = User.find_by username: 'joesmith'
            expect(testUser.id).not_to be_nil
            expect(testUser.username).to eq "joesmith"
            expect(testUser.password_digest).to eq "xxx"
            expect(testUser.created_at).not_to be_nil
            expect(testUser.updated_at).not_to be_nil
        end
      end

      context "rq03.2 assignment code has create_todolist method" do
        it { is_expected.to respond_to(:create_todolist) } 
        it "should create_todolist with provided parameters" do
            expect(TodoList.find_by list_name: "mylist").to be_nil
            due_date=Date.today
            assignment.create_todolist(:name=> 'mylist', :due_date=>due_date)
            testList = TodoList.find_by list_name: 'mylist'
            expect(testList.id).not_to be_nil
            expect(testList.list_name).to eq "mylist"
            expect(testList.list_due_date).to eq due_date
            expect(testList.created_at).not_to be_nil
            expect(testList.updated_at).not_to be_nil
        end  
      end
    end

    context "rq04" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq04.1 Must retrieve paginated User results from DB" do
        it { is_expected.to respond_to(:find_allusers) } 
        it "should implement find_allusers that returns a collection of Users and
            honors offset and limit parameters" do
            lastTime = Time.now
            (0..20).each do |i|
                User.create(:username=> "user_#{i}", :password_digest=> "xxx")
            end
            testUsersGroup1 = assignment.find_allusers(0, 10)
            testUsersGroup2 = assignment.find_allusers(10, 5)

            # test that results are sorted by updated_at and are ascending
            testUsersGroup1.each do |t| 
                expect(t.updated_at).to be >= lastTime
                lastTime = t.updated_at
            end

            #Test that limits and offset are correct
            expect(testUsersGroup1.length).to be(10)
            expect(testUsersGroup2.length).to be(5)
            expect(testUsersGroup1[9].username).to eq('user_9')
            expect(testUsersGroup2[0].username).to eq('user_10')
        end
      end

      context "rq04.2 Must retrieve paginated TodoList results from DB" do
        it { is_expected.to respond_to(:find_alllists) } 
        it "should implement find_alllists that returns a collection of TodoLists and
            honors offset and limit parameters" do
            dateBase = Date.today
            dateTrack = dateBase
            (0..20).each do |i|
                dateTrack = dateBase + i.day
                TodoList.create(:list_name=> "list_#{i}", :list_due_date=>dateTrack)
            end
            testListsGroup1 = assignment.find_alllists(0, 10)
            testListsGroup2 = assignment.find_alllists(10, 5)

            # test that results are sorted by updated_at and are descending
            testListsGroup1.each do |t| 
                expect(t.list_due_date).to be <= dateTrack
                dateTrack = t.list_due_date
            end

            #Test that limits and offset are correct
            expect(testListsGroup1.length).to be(10)
            expect(testListsGroup2.length).to be(5)
            expect(testListsGroup1[9].list_name).to eq('list_11')
            expect(testListsGroup2[0].list_name).to eq('list_10')
        end
      end
    end

    context "rq05" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq05.1 Must Query DB with User exact match" do
        it { is_expected.to respond_to(:find_user_byname) } 
        it "should implement find_user_byname to return all users with a given name" do
            User.create(:username=> "rich", :password_digest=> "xxx")
            User.create(:username=> "bob", :password_digest=> "xxx")
            User.create(:username=> "joe", :password_digest=> "xxx")
            User.create(:username=> "rich", :password_digest=> "xxx")
            expect(assignment.find_user_byname("rich").length).to be(2)
            expect(assignment.find_user_byname("joe").length).to be(1)
        end
      end

      context "rq05.2 Must Query DB with TodoList exact match" do
        it { is_expected.to respond_to(:find_todolist_byname) } 
        it "should implement find_todolist_byname to return all list with a given name" do
            TodoList.create(:list_name=> "My list", :list_due_date=>Date.today)
            TodoList.create(:list_name=> "Bob's list", :list_due_date=>Date.today)
            TodoList.create(:list_name=> "Joe's list", :list_due_date=>Date.today)
            TodoList.create(:list_name=> "My list", :list_due_date=>Date.today)
            expect(assignment.find_todolist_byname("My list").length).to be(2)
            expect(assignment.find_todolist_byname("Bob's list").length).to be(1)
        end
      end
    end

    context "rq06" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq06.1 Must get rows from DB by User PK" do
        it { is_expected.to respond_to(:get_user_byid) } 
        it "should implement the get_user_byid method to return user by primary key" do
            User.create(:username=> 'joesmith', :password_digest=>'xxx')
            testUser = User.find_by username: 'joesmith'
            expect(testUser.id).not_to be_nil
            expect(assignment.get_user_byid(testUser.id).username).to eq("joesmith")
        end
      end

      context "rq06.2 Must get rows from DB by TodoList PK" do
        it { is_expected.to respond_to(:get_todolist_byid) }        
        it "should implement the get_todolist_byid method to return todo_list by primary key" do
            TodoList.create(:list_name=> 'my list', :list_due_date=>Date.today)
            testList = TodoList.find_by list_name: 'my list'
            expect(testList.id).not_to be_nil
            expect(assignment.get_todolist_byid(testList.id).list_name).to eq('my list')
        end
      end
    end

    context "rq07" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq07.1 Must update User rows in database" do
        it { is_expected.to respond_to(:update_password) }        
        it "should implement update_password method with parameters id and password_digest" do
            User.create(:username=> 'joesmith', :password_digest=>'xxx')
            testUser = User.find_by username: 'joesmith'
            assignment.update_password(testUser.id, 'newpassword')
            expect(User.find(testUser.id).password_digest).to eq("newpassword")
        end
      end

      context "rq07.2 Must update TodoList rows in database" do
        it { is_expected.to respond_to(:update_listname) }  
        it "should implement update_listname method with parameters id and name" do
            TodoList.create(:list_name=> 'my list', :list_due_date=>Date.today)
            testList = TodoList.find_by list_name: 'my list'
            assignment.update_listname(testList.id, 'Big list')
            expect(TodoList.find(testList.id).list_name).to eq("Big list")
        end            
      end
    end

    context "rq08" do 
      before do
        User.destroy_all
        TodoList.destroy_all
      end

      context "rq08.1 Must delete User rows from database" do
        it { is_expected.to respond_to(:delete_user) } 
        it "should implement delete_user method which takes a primary key parameter" do
            user=User.create(:username=>"deletetest",:password_digest=>"foobar")
            expect(User.find(user.id)).not_to be_nil
            assignment.delete_user(user.id)
            expect{ (User.find(user.id)) }.to raise_error(ActiveRecord::RecordNotFound)
        end
      end

      context "rq08.2 Must delete TodoList rows from database" do
        it { is_expected.to respond_to(:delete_todolist) }     
        it "should implement delete_todolist method which takes a primary key parameter" do
            list=TodoList.create(:list_name=>"delete list", :list_due_date=>Date.new(2020))
            expect(TodoList.find(list.id)).not_to be_nil
            assignment.delete_todolist(list.id)
            expect{ (TodoList.find(list.id)) }.to raise_error(ActiveRecord::RecordNotFound)
        end
      end
    end
end
