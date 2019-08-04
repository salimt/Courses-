require_relative '../config/environment'
require 'rails_helper'

feature "Module 4 End-2-End Test" do

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

    before :all do 
        TodoItem.destroy_all
        TodoList.destroy_all
        User.destroy_all
        load "#{Rails.root}/db/seeds.rb"  
    end

    def get_id_from_url(path) 
        return path.split("/").last.to_i
    end


    context "rq14" do
        let (:user) { User.where(username: "rich").first }
        let (:userLists) { user.authenticate("123abc").todo_lists }
        let (:totalLists) { userLists.count }

        scenario "End to end works from login through TodoList and TodoItem operations" do
            expect(File.exist?("#{Rails.root}/db/seeds.rb")).to be true
            expect(user).to_not be_nil
            visit(login_path)
            fill_in "user[username]", with: user.username
            fill_in "user[password]", with: "123abc"
            click_button "Login"
            expect(URI.parse(page.current_url).path).to eq(root_path)
            expect(page).to have_content("Logged in successfully")
            expect(page).to have_content("Listing Todo Lists")
            expect(page).to have_link(2, :href => "/?page=2")
        
            click_link(2, :href => "/?page=2")          # go to second page of todo_lists for user
            click_link("Show", :match => :first)        # select first todo_list on second page

            # gather data about selected item for comparison tests later
            l_id = get_id_from_url(page.current_url)
            curList = TodoList.where(id: l_id).first
            numItems = curList.todo_items.count

            expect(page).to have_content(curList.list_name)
            expect(page).to have_content(curList.list_due_date)

            click_link("Show", :match => :first)        # select first todo item 
            i_id = get_id_from_url(page.current_url)    # get id of TodoItem on page
            curItem = TodoItem.where(id: i_id).first    # get TodoItem from db

            expect(page).to have_content(curItem.title)
            expect(page).to have_content(curItem.due_date)
            expect(page).to have_content(curItem.description)
            expect(page).to have_content(curItem.completed)

            # check if selected item is completed.  If not make it completed
            state = (page.find('p', :text => /\ACompleted:/).text).split(":").last.strip!
            if (state == 'false') then
                click_link("Edit")
                page.check 'todo_item[completed]'
                click_button("Update Todo item")
            else
                click_link("Back")
            end
            
            # confirmation test that TodoItem is complete
            curItem = TodoItem.where(id: i_id).first    # get updated TodoItem from db
            expect(curItem.completed).to be(true)
            expect(page).to have_link("New Todo Item")

            # add new Todo Item
            item = TodoItem.new(title:'Random', description:'Random entry', completed:false, due_date:Date.today)

            click_link("New Todo Item")                 # go to new TodoItem page
            expect(page).to have_content("New Todo Item")
            expect(page).to have_button("Create Todo item")

            fill_in 'todo_item[title]', with: item.title                # load form
            fill_in 'todo_item[description]', with: item.description
            select item.due_date.year, from: 'todo_item[due_date(1i)]'
            select item.due_date.strftime("%B"), from: 'todo_item[due_date(2i)]'
            select item.due_date.day, from: 'todo_item[due_date(3i)]'            
            click_button 'Create Todo item'

            # confirmation test that todo_item count has increased by 1 with new item
            expect(curList.todo_items.count).to eq(numItems + 1)
            # confirmation test that item exists in database
            expect(TodoItem.where(title: "Random").first.description).to eq(item.description)
            # confirm that we are back on summary TodoList item view
            expect(page).to have_content(curList.list_name)          
            expect(page).to have_link("Destroy")

            # delete the first TodoItem on the current todo_list page
            TodoItemsController.skip_before_action :verify_authenticity_token
            click_link("Destroy", :match => :first)

            # confirmation test to see that todo_item count has decreased back to original count
            expect(curList.todo_items.count).to eq(numItems)
            # confirm that we are back on summary TodoList item view
            expect(page).to have_content(curList.list_name) 

            click_link("Back")                      # Return to todo_list view for user
            expect(page).to have_content("Listing Todo Lists")
            expect(page).to have_link("New Todo list")

            # Test adding a new TodoList
            click_link("New Todo list")
            expect(page).to have_content("New Todo List")       # confirm location
            expect(page).to have_button("Create Todo list")

            list = TodoList.new(list_name:'New List', list_due_date:Date.today)
            select list.list_due_date.year, from: 'todo_list[list_due_date(1i)]'
            select list.list_due_date.strftime("%B"), from: 'todo_list[list_due_date(2i)]'
            select list.list_due_date.day, from: 'todo_list[list_due_date(3i)]'
            fill_in 'todo_list[list_name]', with: list.list_name
            TodoListsController.skip_before_action :verify_authenticity_token
            expect(totalLists).to be > 0        # lazy load todoLists before insert
            click_button("Create Todo list")

            expect(page).to have_content("Todo list was successfully created.")    # confirm location

            # confirmation test that new list has increased total list count by 1
            expect(user.authenticate("123abc").todo_lists.count).to eq(totalLists + 1)
            
            # confirmation test that new list was persisted to database
            expect(TodoList.where(list_name: "New List").first.list_due_date).to eq(list.list_due_date)
            
            click_link("Back")                                  # return to todo_list view for user
            expect(page).to have_content("Listing Todo Lists")  # confirm location
            expect(page).to have_link("New Todo list")       
            expect(page).to have_link("Destroy")     

            # delete the first Todo List on page
            dlink = find_link("Destroy", :match => :first)
            deleteId = get_id_from_url(dlink[:href])
            click_link("Destroy", :match => :first)

            expect(page).to have_content("Listing Todo Lists")  # confirm location
            expect(page).to have_link("New Todo list")  

            # confirmation test to see that todo_list count has decreased back to original count
            expect(user.authenticate("123abc").todo_lists.count).to eq(totalLists)

        end
    end
end