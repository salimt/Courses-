require_relative '../config/environment'
require 'rails_helper'
require 'capybara'
require 'capybara/dsl'
require 'utils'
require 'json'

Mongo::Logger.logger.level = ::Logger::INFO

feature "Module 1 Scaffolding Tests", :type => :routing do
    include Capybara::DSL

    before :all do 
        @db = Racer.mongo_client
        @db[:racers].drop
        hash = JSON.parse(File.read("race_results.json"))
        @db[:racers].insert_many(hash)
        @num_records = Racer.collection.count
    end

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

    def get_id_from_url(path) 
        return path.split("/").last.to_s
    end

    context "Scaffolding links and functions are in place and operational" do 
        let!(:racer) { Racer.find((Racer.all({number: 5}).first)[:_id].to_s) }
        let!(:new_racer) { Racer.new({number:1, first_name:"Carly", last_name:"Fiorino", gender:"F", secs:2833, group:"60 to 69"}) }

        scenario "home page exists and is list of racers with links to show, edit and destroy for all racers" do 
            visit (root_path) 
            expect(page.status_code).to eq(200)
            expect(page).to have_content("Listing Racers")
            expect(page).to have_link('Show', :href =>"#{racer_path(racer.id)}")
            expect(page).to have_link("Edit", :href =>"#{edit_racer_path(racer.id)}")
            expect(page).to have_link("Destroy", :href =>"#{racer_path(racer.id)}")
            expect(page).to have_link("New Racer", :href =>"#{new_racer_path}")
        end

        scenario "show page exists and has links to edit page and home page" do 
            visit (root_path) 
            click_link('Show', :href =>"#{racer_path(racer.id)}")
            expect(page.status_code).to eq(200)
            expect(page).to have_content("First name: #{racer.first_name}")
            expect(page).to have_content("Last name: #{racer.last_name}")
            expect(page).to have_content("Number: #{racer.number}")
            expect(page).to have_content("Gender: #{racer.gender}")
            expect(page).to have_content("Group: #{racer.group}")
            expect(page).to have_content("Secs: #{racer.secs}")
            expect(page).to have_link('Back', :href =>"#{racers_path}")
            expect(page).to have_link('Edit', :href =>"#{edit_racer_path(racer.id)}")
        end

        scenario "edit page exists, has links to show page and home page as well as a button to update racer" do 
            visit (root_path) 
            click_link('Edit', :href =>"#{edit_racer_path(racer.id)}")
            expect(page.status_code).to eq(200)
            expect(page).to have_content("Editing Racer")
            expect(page).to have_field("racer_first_name", :with => "#{racer.first_name}")
            expect(page).to have_field("racer_last_name", :with => "#{racer.last_name}")
            expect(page).to have_field("racer_gender", :with => "#{racer.gender}")
            expect(page).to have_field("racer_number", :with => "#{racer.number}")
            expect(page).to have_field("racer_group", :with => "#{racer.group}")
            expect(page).to have_field("racer_secs", :with => "#{racer.secs}")
            expect(page).to have_link('Back', :href =>"#{racers_path}")
            expect(page).to have_link('Show', :href =>"#{racer_path(racer.id)}")
            expect(page).to have_button('Update Racer')
        end

        scenario "new racer page exists, has a link to home page and button to create new racer" do 
            visit(root_path)
            click_link("New Racer", :href =>"#{new_racer_path}")
            expect(page.status_code).to eq(200)
            expect(page).to have_content("New Racer")
            expect(page).to have_field("racer_first_name")
            expect(page).to have_field("racer_last_name")
            expect(page).to have_field("racer_gender")
            expect(page).to have_field("racer_number")
            expect(page).to have_field("racer_group")
            expect(page).to have_field("racer_secs")
            expect(page).to have_button('Create Racer')
        end

        scenario "creating racer from new racer page adds racer to db and returns to show page" do 
            visit(root_path)
            click_link("New Racer", :href =>"#{new_racer_path}")
            fill_in 'racer_first_name', with: new_racer.first_name
            fill_in 'racer_last_name', with: new_racer.last_name
            fill_in 'racer_number', with: new_racer.number
            fill_in 'racer_gender', with: new_racer.gender
            fill_in 'racer_group', with: new_racer.group
            fill_in 'racer_secs', with: new_racer.secs
            click_button('Create Racer')
            expect(page.status_code).to eq(200).or eq(201)
            db_racer = Racer.collection.find(last_name: new_racer.last_name, first_name: new_racer.first_name).first
            expect(db_racer).to_not be_nil
            local_id = db_racer[:_id].to_s
            expect(URI.parse(current_url).path).to eq racer_path(local_id)
        end

        scenario "update racer from edit page updates racer in db and returns to show page" do 
            db_racer = Racer.collection.find(number: new_racer.number).first
            local_id = db_racer[:_id].to_s
            visit(root_path)
            click_link('Edit', :href =>"#{edit_racer_path(local_id)}")
            fill_in 'racer_first_name', with: "Joe"
            fill_in 'racer_last_name', with: "Pesci"
            click_button('Update Racer')
            expect(page.status_code).to eq(200).or eq(201)
            expect(page).to have_content("Racer was successfully updated.")
            expect(page).to have_content("Number: #{db_racer[:number]}")
            up_racer = Racer.find(local_id)
            expect(up_racer.first_name).to eq("Joe")
            expect(up_racer.last_name).to eq("Pesci")
            expect(URI.parse(current_url).path).to eq racer_path(local_id)
        end

        scenario "clicking on destroy link from home page removes racer from db and returns to home page" do 
            RacersController.skip_before_action :verify_authenticity_token
            racer_count = Racer.collection.find.count
            visit(root_path)
            dlink = find_link("Destroy", :match => :first)
            deleteId = get_id_from_url(dlink[:href])
            click_link("Destroy", :match => :first)
            expect(page).to have_content("Racer was successfully destroyed")
            expect(Racer.collection.find.count).to eq(racer_count - 1)
            db_check = Racer.find(deleteId)
            expect(db_check).to be_nil
        end
    end
end
