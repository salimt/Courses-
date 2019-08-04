require_relative '../config/environment'
require 'rails_helper'

feature "Module 4 Pagination Test" do

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

    context "rq13" do
        scenario "List summary limited to 8 lists with pagination" do
            user = User.where(username: "rich").first
            userLists = user.authenticate("123abc").todo_lists
            totalLists = userLists.count
            numPages = (totalLists / 8.to_f).ceil
            visit(login_path)
            fill_in "user[username]", with: "rich"
            fill_in "user[password]", with: "123abc"
            click_button "Login"
            # Check that there are no more lists than user has
            (1..numPages-1).each do |p|
                expect(page.all('td', :text => /\AList/).count).to be <= 8
                click_link("#{p+1}", :href => "/?page=#{p+1}")
            end
        end
    end
end