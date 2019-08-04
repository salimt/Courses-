require_relative '../config/environment'
require 'rails_helper'

feature "Module #4 Setup Tests" do

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

    context "rq1.0" do
      it "must have top level structure of a rails application" do
        expect(File.exists?("Gemfile")).to be(true)
        expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
        expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
      end
    end

    context "rq1.1" do # Resuse 'User' model and database table
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

    context "rq1.2" do # Re-use and update 'TodoList' model and database table
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

    context "rq1.3" do #add migration for TodoList -> User relationship
      it "TodoList has a many:1 belongs_to relationship with User" do
        expect(TodoList.reflect_on_association(:user).macro).to eq :belongs_to
      end
      it "User has a 1:many has_many relationship with TodoList" do
        expect(User.reflect_on_association(:todo_lists).macro).to eq :has_many
      end
    end

    context "rq1.4" do # Re-use and update 'TodoItem' model and database table
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

    context "rq1.5" do # Re-use and update 'TodoItem' model and database table
      it "User has a 1:many relationship with TodoItem" do
        expect(User.reflect_on_association(:todo_items).macro).to eq :has_many
      end         
      it "User has a 1:many relationship with TodoItem through TodoList" do
        expect(User.reflect_on_association(:todo_items).options[:through]).to eq :todo_lists  
        expect(User.reflect_on_association(:todo_items).options[:source]).to eq :todo_items  
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
 
      context "rq1.6" do
        it "Cascading delete is represented in model dependencies" do
          expect(User.reflect_on_association(:todo_lists).options[:dependent]).to eq :destroy
          expect(TodoList.reflect_on_association(:todo_items).options[:dependent]).to eq :destroy
        end
        it "User delete must cascade delete to TodoList and TodoItems" do
          user = User.create(:username=>"testUser", :password_digest=>"xxxx")
          tdlist = TodoList.create(:list_name=>"my list", :list_due_date=>Date.today, :user_id=>user.id)
          tditem = TodoItem.create(:due_date=>Date.today, :title=>"My Item", :description=>"Item Details", :todo_list_id=>tdlist.id)
          expect(User.find_by(id: user.id)).to_not be_nil           
          expect(TodoList.find_by(id: tdlist.id)).to_not be_nil           
          expect(TodoItem.find_by(id: tditem.id)).to_not be_nil     
          # remove user with method and test that all related objects are nil
          lastId = user.id
          user.destroy
          # test that user and all other objects link to that user no longer exist
          expect(User.find_by(id: lastId)).to be_nil           
          expect(TodoList.find_by(id: tdlist.id)).to be_nil           
          expect(TodoItem.find_by(id: tditem.id)).to be_nil  
        end
      end
    end
end
