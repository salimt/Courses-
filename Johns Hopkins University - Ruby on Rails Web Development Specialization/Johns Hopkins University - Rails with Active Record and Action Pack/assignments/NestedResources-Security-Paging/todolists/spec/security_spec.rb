require_relative '../config/environment'
require 'rails_helper'

feature "Module 4 Security Tests" do

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

    context "rq02" do
        subject(:user) { User.new({:username => "goodUser", :password => "mux833c"}) }
        before :each do 
            User.destroy_all
            user.save
        end

        context "User must have has_secure_password for bcrypt" do
            it { is_expected.to respond_to(:authenticate) } 
            it { is_expected.to respond_to(:password) } 

            it "only users with passwords will be persisted" do
                expect(User.new({:username => "test"}).save).to be(false)
                expect(user).to_not be_nil
            end
            it "only access user with a valid password" do
                expect(User.find_by(:username => "goodUser").authenticate("test")).to be(false)
                expect(User.find_by(:username => "goodUser").authenticate("mux833c")).to_not be_nil
            end
            it "password confirmation must match password to persist a user" do
                expect(User.new({:username => "test", :password => "goodPw!!", :password_confirmation => "notsame"}).save).to be(false)
                expect(User.new({:username => "test", :password => "goodPw!!", :password_confirmation => "goodPw!!"}).save).to_not be_nil
            end
        end
    end

    context "rq08" do
        subject(:dbUser) { User.where(username:"rich").first } 

        scenario "will only authenticate user with proper password" do
            expect(dbUser.authenticate("wrongPass")).to be false
            expect(dbUser.authenticate("123abc").id).to eq(dbUser.id)
        end

        scenario "can get TodoList for an authenticated user" do
            number = dbUser.todo_lists.count
            expect(dbUser.authenticate("123abc").todo_lists.count).to eq(number)
        end
    end

    context "rq09" do
        scenario "Login URI returns valid status" do 
            visit (login_path) 
            expect(page.status_code).to eq(200)
        end

        scenario "Logout URI returns valid status" do
            SessionsController.skip_before_action :ensure_login
            SessionsController.skip_before_action :verify_authenticity_token
            page.driver.submit :delete, logout_path, { :params => {} }
            expect(page.status_code).to eq(200)
        end
    end

    context "rq10" do
        context "rq10b" do
            scenario "Login URI returns valid page" do 
                visit (login_path) 
                expect(page).to have_field('user[username]')
               expect(page).to have_field('user[password]')
                expect(page).to have_button("Login")
            end
        end

        context "rq10c" do
            scenario "Successful login results in navigation to list items page" do
                visit(login_path)
                fill_in "user[username]", with: "rich"
                fill_in "user[password]", with: "123abc"
                click_button "Login"
                expect(URI.parse(page.current_url).path).to eq(root_path)
                expect(page).to have_content("Logged in successfully")
            end

            scenario "Unsuccessful login results in return to login page" do 
                visit (login_path) 
                fill_in "user[username]", with: "rich"
                fill_in "user[password]", with: "xxxxx"
                click_button "Login"
                expect(URI.parse(page.current_url).path).to eq(login_path)
            end
        end

        context "rq10d" do
            scenario "Logging out returns client to login page" do 
                SessionsController.skip_before_action :ensure_login
                SessionsController.skip_before_action :verify_authenticity_token
                page.driver.submit :delete, logout_path, {}
                expect(URI.parse(page.current_url).path).to eq(login_path)
            end            
        end
    end
end
