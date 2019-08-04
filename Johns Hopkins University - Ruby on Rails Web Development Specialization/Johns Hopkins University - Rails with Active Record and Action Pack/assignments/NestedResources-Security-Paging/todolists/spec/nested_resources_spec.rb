require_relative '../config/environment'
require 'rails_helper'

describe "Module #4: Navigation Tests", :type => :routing do
    include Capybara::DSL

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

    context "rq04" do 
        scenario "TodoList scaffolding was generated and it was set as root path" do 
            visit (root_path) 
            expect(page.status_code).to eq(200)
            rootPage = page
            visit (todo_lists_path)
            expect(page.status_code).to eq(200)
            expect(page).to be(rootPage)
        end
        scenario "TodoItems is not directly accessible as it is nested in TodoList" do        
            visit ("/todo_items")
            expect(page.status_code).to eq(404)
        end
    end

    context "rq05" do

        before :all do    
            TodoItem.destroy_all
            TodoList.destroy_all
            User.destroy_all
            load "#{Rails.root}/db/seeds.rb"  
        end

        context "rq05b" do

            scenario "TodoList show page displays associated todo_items" do
                tl_id = TodoList.all.sample.id
                list_items = TodoList.find(tl_id).todo_items
                visit(todo_list_path(tl_id))

                list_items.each do |l|
                    expect(page).to have_content(l.title)
                    expect(page).to have_content(l.due_date)
                    expect(page).to have_content(l.description)
                end
            end
        end

        context "rq05d" do  

            scenario "TodoItem endpoint is accessible (show and destroy) through TodoList" do 
                tl_id = TodoList.all.sample.id
                list_items = TodoList.find(tl_id).todo_items
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                list_items.each do |l|
                    visit(todo_list_path(tl_id))
                    expect(page).to have_link('Show', :href => "#{todo_list_todo_item_path(tl_id, l.id)}")
                    expect(page).to have_link('Destroy', :href => "#{todo_list_todo_item_path(tl_id, l.id)}")
                end
            end

        end

        context "rq05e" do 

            scenario "TodoList page has new TodoItem link with specified URI and method" do 
                tl_id = TodoList.all.sample.id
                expect(:get => new_todo_list_todo_item_path(tl_id)).to route_to(:controller => "todo_items", :action => "new", :todo_list_id => "#{tl_id}")
                list_items = TodoList.find(tl_id).todo_items
                visit(todo_list_path(tl_id))
                expect(page).to have_link('New Todo Item', :href => "#{new_todo_list_todo_item_path(tl_id)}")
            end
        end 
    end

    context "rq07" do        
    
        before :all do    
            TodoItem.destroy_all
            TodoList.destroy_all
            User.destroy_all
            load "#{Rails.root}/db/seeds.rb"  
        end

        subject(:user) {
            User.find_by(:username=>"jim").authenticate("abc123")
        }

        context "rq07a" do
            scenario "Links navigate from TodoList-TodoItem summary page to Item show page" do
                tl_id = TodoList.all.sample.id
                list_item = TodoList.find(tl_id).todo_items.sample
                li_id = list_item.id
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                visit(todo_list_path(tl_id))
                expect(page).to have_link('Show', :href => "#{todo_list_todo_item_path(tl_id, li_id)}")
                click_link('Show', :href => "#{todo_list_todo_item_path(tl_id, li_id)}")
                expect(page).to have_content(list_item.title)
                expect(page).to have_content(list_item.due_date)
                expect(page).to have_content(list_item.description)
                expect(page).to have_content(list_item.completed)
            end

            scenario "Links navigate from item display back to TodoList-TodoItem summary page" do
                testList = TodoList.all.sample
                tl_id = testList.id
                list_item = TodoList.find(tl_id).todo_items.sample
                li_id = list_item.id
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                visit(todo_list_todo_item_path(tl_id, li_id))
                expect(page).to have_link('Back', :href => "#{todo_list_path(tl_id)}")
                click_link('Back', :href => "#{todo_list_path(tl_id)}");
                expect(page).to have_content(testList.list_name)
                expect(page).to have_content(testList.list_due_date)
            end

            scenario "Link navigates from TodoList-TodoItem summary page to Item destroy" do
                TodoItemsController.skip_before_action :ensure_login
                TodoItemsController.skip_before_action :verify_authenticity_token
                tl_id = TodoList.where(:user_id=>user.id).sample.id
                list_items = TodoList.find(tl_id).todo_items
                li_id = list_items.sample.id
                # replace with navigation here
                visit(todo_list_path(tl_id))
                expect(page).to have_link('Destroy', :href => "#{todo_list_todo_item_path(tl_id, li_id)}")
                click_link('Destroy', :href => "#{todo_list_todo_item_path(tl_id, li_id)}");               
                expect(TodoItem.find_by(:id => li_id)).to be_nil
            end
        end

        context "rq07c" do
            scenario "Link navigates from TodoItem display page to Item edit page and updates item" do
                tl_id = TodoList.all.sample.id
                list_item = TodoList.find(tl_id).todo_items.sample
                li_id = list_item.id
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                visit(todo_list_todo_item_path(tl_id, li_id))
                expect(page).to have_link('Edit', :href => "#{edit_todo_list_todo_item_path(tl_id, li_id)}")
                click_link('Edit', :href => "#{edit_todo_list_todo_item_path(tl_id, li_id)}");
                expect(page).to have_content("Editing Todo Item")
                expect(find_field('todo_item[title]').value).to eq(list_item.title)
                expect(find_field('todo_item[description]').value).to eq(list_item.description)
                expect(page).to have_button("Update Todo item")
                uString = "Updated value of #{Random.new.rand(10000)}"
                fill_in "todo_item[description]", with: uString
                click_button("Update Todo item")
                expect(URI.parse(page.current_url).path).to eq("#{todo_list_path(tl_id)}")
                expect(TodoItem.find_by(:id => li_id).description).to eq(uString)
            end

            scenario "Can navigate from TodoItem edit to show via Show link" do
                testList = TodoList.all.sample
                tl_id = testList.id
                list_item = TodoList.find(tl_id).todo_items.sample
                li_id = list_item.id
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                visit(edit_todo_list_todo_item_path(tl_id, li_id))
                expect(page).to have_link('Show', :href => "#{todo_list_todo_item_path(tl_id, li_id)}")
                click_link('Show', :href => "#{todo_list_todo_item_path(tl_id, li_id)}");
                expect(page).to have_content(list_item.title)
                expect(page).to have_content(list_item.description)
                expect(page).to have_content(list_item.due_date)
                expect(page).to have_content(list_item.completed)
            end

            scenario "Can navigate from TodoItem edit to home page with back link" do
                testList = TodoList.all.sample
                tl_id = testList.id
                list_item = TodoList.find(tl_id).todo_items.sample
                li_id = list_item.id
                # Confirm links in list page are nested and that the
                # show page results in expected method behavior
                visit(edit_todo_list_todo_item_path(tl_id, li_id))
                expect(page).to have_link('Back', :href => "#{todo_list_path(tl_id)}")
                click_link('Back', :href => "#{todo_list_path(tl_id)}");
                expect(page).to have_content(testList.list_name)
                expect(page).to have_content(testList.list_due_date)
            end            
        end

        context "rq07d" do
            let(:testList) { TodoList.all.sample }
            let(:tl_id) { testList.id }
            let(:listItem) { TodoList.find(tl_id).todo_items.sample }
            let(:li_id) { listItem.id }

            scenario "Navigation link from list item view to new item form" do
                visit(todo_list_path(tl_id, li_id))
                expect(page).to have_link('New Todo Item', :href => "#{new_todo_list_todo_item_path(tl_id)}")
                click_link('New Todo Item', :href => "#{new_todo_list_todo_item_path(tl_id)}")
                expect(URI.parse(page.current_url).path).to eq("#{new_todo_list_todo_item_path(tl_id)}")
                expect(page).to have_content("New Todo Item")
            end

            scenario "New item form create adds new item and navigates to list item view" do 
                # confirm that route is correct
                expect(:post => todo_list_todo_items_path(tl_id)).to route_to(:controller => "todo_items", :action => "create", :todo_list_id => "#{tl_id}")
                visit(new_todo_list_todo_item_path(tl_id))
                item = TodoItem.new(title:'Random', description:'Random entry', completed:false, due_date:Date.today)
                select item.due_date.year, from: 'todo_item[due_date(1i)]'
                select item.due_date.strftime("%B"), from: 'todo_item[due_date(2i)]'
                select item.due_date.day, from: 'todo_item[due_date(3i)]'
                fill_in 'todo_item[title]', with: item.title
                fill_in 'todo_item[description]', with: item.description
                click_button 'Create Todo item'
                new_task = TodoItem.find_by! title: "Random"
                expect(new_task.description).to eq(item.description)
                expect(page).to have_content "Todo item was successfully created."
            end

            scenario "Back from new item form returns to list item view" do
                visit(new_todo_list_todo_item_path(tl_id))
                expect(page).to have_link('Back', :href => "#{todo_list_path(tl_id)}")
                click_link('Back', :href => "#{todo_list_path(tl_id)}")
                expect(page).to have_content(testList.list_name)
                expect(page).to have_content(testList.list_due_date)
            end
        end

        context "rq07e" do
            let(:testList) { TodoList.all.sample }
            let(:tl_id) { testList.id }
            let(:listItem) { TodoList.find(tl_id).todo_items.sample }
            let(:li_id) { listItem.id }

            scenario "Completed field will be present in edit form" do
                # Go to edit page and check that completed field is present
                visit(edit_todo_list_todo_item_path(tl_id, li_id))
                expect(page.has_field?('todo_item[completed]')).to be true
            end

            scenario "Completed field will not be present in new form" do
                visit(new_todo_list_todo_item_path(tl_id))
                expect(page.has_field?('todo_item[completed]')).to be false
            end
        end

    end   
 end
