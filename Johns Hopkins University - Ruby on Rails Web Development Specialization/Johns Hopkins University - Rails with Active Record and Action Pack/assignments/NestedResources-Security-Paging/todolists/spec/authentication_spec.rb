require_relative '../config/environment'
require 'rails_helper'

feature "Module 4 Authentication Test" do

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

    let ( :tl_id ) { TodoList.all.sample.id }
    let ( :item_id ) { TodoList.find(tl_id).todo_items.sample.id }

    context "rq11" do

        scenario "When not logged in only accessible page is login page" do
            visit(root_path)
            expect(URI.parse(page.current_url).path).to eq(login_path)
            visit(new_todo_list_todo_item_path(tl_id))
            expect(URI.parse(page.current_url).path).to eq(login_path)            
            visit(edit_todo_list_todo_item_path(tl_id, item_id))
            expect(URI.parse(page.current_url).path).to eq(login_path)
            visit(todo_list_todo_item_path(tl_id, item_id))
            expect(URI.parse(page.current_url).path).to eq(login_path)   
            visit(todo_lists_path)
            expect(URI.parse(page.current_url).path).to eq(login_path)     
            visit(new_todo_list_path)
            expect(URI.parse(page.current_url).path).to eq(login_path)     
            visit(edit_todo_list_path(tl_id))
            expect(URI.parse(page.current_url).path).to eq(login_path)   
            visit(todo_list_path(tl_id))
            expect(URI.parse(page.current_url).path).to eq(login_path)  
        end
    end

    context "rq12" do
        scenario "List summary page has only lists for logged in user" do
            user = User.where(username: "rich").first
            userLists = user.authenticate("123abc").todo_lists
            visit(login_path)
            fill_in "user[username]", with: "rich"
            fill_in "user[password]", with: "123abc"
            click_button "Login"
            if not page.has_css?('div.pagination') then
                # Check that there are no more lists than user has
                expect(page.all('td', :text => /\AList/).count).to eq(userLists.count)
                # Now check that all lists match user lists
                userLists.each do |u|
                    expect(page).to have_content(u.list_name)
                    expect(page).to have_content(u.list_due_date)
                end
            else
              #if pagination is implemented then skip this test
            end    
        end
    end
end
