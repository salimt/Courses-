require 'rails_helper'

feature "Module #3" do

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

  # check that TodoItem exists with fields and migration/db exists
  context "rq02" do
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end

    context "Scaffolding generated" do
      it "must have at least one controller and views" do 
        expect(Dir.entries("./app/controllers")).to include("todo_items_controller.rb")
        expect(Dir.entries("./app/views/")).to include("todo_items")
        expect(Dir.entries("./app/views/todo_items")).to include("index.html.erb")
      end
    end
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
  end

  context "rq03" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end

    # Check that database has been initialized with seed file prior to test
    it "has the file that seeded the database" do
      expect(File).to exist("#{Rails.root}/db/seeds.rb")
    end
    it "must have TodoItems as provided by assignment seed file" do
      expect(TodoItem.all.length).to eq(3)
      expect(TodoItem.all.map{ |x| x.title }).to include("Task 1", "Task 2", "Task 3")
    end
  end

  context "rq04" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end

    scenario "todo_items URI should return a valid page" do 
      visit todo_items_path  
      expect(page.status_code).to eq(200)
    end
  end

  context "rq05" do
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
      # start at main page...
      visit todo_items_path
    end

    scenario "Main page displays all items" do
      expect(page).to have_content "Task 1"
      expect(page).to have_content "Task 2"
      expect(page).to have_content "Task 3"
    end

    scenario "New action from index and create action to generate a new item" do 
      expect(page).to have_link("New Todo item")
      click_link("New Todo item")
      expect(page).to have_content "Title"
      item = TodoItem.new(title:'Random', description:'', completed:true, due_date:Date.today)
      fill_in "Title", with: item.title
      select item.due_date.year, from: 'todo_item[due_date(1i)]'
      select item.due_date.strftime("%B"), from: 'todo_item[due_date(2i)]'
      select item.due_date.day, from: 'todo_item[due_date(3i)]'
      fill_in 'todo_item[title]', with: item.title
      fill_in 'todo_item[description]', with: item.description
      click_button 'Create Todo item'
      new_task = TodoItem.find_by! title: "Random"
      expect(TodoItem.count).to eq 4
      expect(page).to have_content "Todo item was successfully created."
    end

=begin
    scenario "Show link takes user to detail page about an item" do
      task2 = TodoItem.find_by title: "Task 2"
      expect(page).to have_link("Show", href: "#{todo_item_path(task2)}")
      click_link("Show", href: "#{todo_item_path(task2)}")
      expect(page).to have_content task2.due_date.strftime("%F")
      expect(page).to have_content task2.title
      expect(page).to have_content task2.description
      expect(page.current_path).to eq(todo_item_path(task2.id))
    end
 
    scenario "Edit and update action" do  
      new_task = TodoItem.create!(:title => "Random", :description => "", :completed => true, :due_date => Date.today)
      visit edit_todo_item_path(new_task)
      expect(page).to have_content "Description"
      expect(page).to have_field("Title", with: "Random")
      fill_in "Description", with: "fascinating"    
      click_button "Update Todo item"
      new_task = TodoItem.find_by! title: "Random"
      expect(new_task.description).to_not be_blank
      expect(page).to have_content "Todo item was successfully updated."
    end

    scenario "Delete action" do
      new_task = TodoItem.create!(:title => "Random", :description => "", :completed => true, :due_date => Date.today)
      visit todo_items_path
      expect(page).to have_link("Destroy", href: "#{todo_item_path(new_task)}")
      click_link("Destroy", href: "#{todo_item_path(new_task)}")
      expect(TodoItem.count).to eq 3
      expect(TodoItem.find_by title: "Random").to be_nil
    end
=end
  end

  context "rq06" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end
    scenario "after creating an item should go to listing page" do
      visit new_todo_item_path
      fill_in "Title", with: "some title"
      click_button "Create Todo item"
      expect(page).to have_content "Listing Todo Items"
      expect(page.current_path).to eq(todo_items_path)
    end 
  end

  context "rq07" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end
    scenario "remove edit link from listing page" do
      visit todo_items_path
      expect(page).to_not have_content('Edit')
    end
  end

  context "rq08" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end
    scenario "only display completed checkbox in the form when editing" do
      visit new_todo_item_path 
      expect(page).to_not have_content('Completed')
      item = TodoItem.all.to_a[0]
      expect(item.id).to_not be_nil
      visit edit_todo_item_path(item.id)
      expect(page).to have_content('Completed')
    end
  end

  context "rq09" do 
    before :each do    
      TodoItem.destroy_all
      load "#{Rails.root}/db/seeds.rb"  
    end
    scenario "display somewhere on the listing page number of completed todos" do 
      # seed the page with random items so test is specific to each run
      TodoItem.destroy_all
      numCompleted = 0;
      (0..15).each do |i|
        random_boolean = [true, false].sample  
        if (random_boolean) then
          numCompleted = numCompleted + 1
        end
        TodoItem.create!(title: "Task #{i}", due_date: Date.today, description: "description #{i}", completed: random_boolean)
      end
      visit todo_items_path
      expect(page).to have_content "Number of Completed Todos: #{numCompleted}"  
    
      TodoItem.create! [
        { title: "Task xyz", due_date: Date.today, completed: false },
        { title: "Task zyx", due_date: Date.tomorrow, completed: true},
      ]
      numCompleted = numCompleted + 1
      visit todo_items_path
      expect(page).to have_content "Number of Completed Todos: #{numCompleted}"  
    end
  end
end
