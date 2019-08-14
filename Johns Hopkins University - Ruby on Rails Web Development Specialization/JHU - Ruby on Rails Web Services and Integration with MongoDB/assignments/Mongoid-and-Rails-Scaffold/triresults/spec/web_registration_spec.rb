# web_registration_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #3 Web Racer/Race Registration", :type => :routing do
    include Capybara::DSL
    include Test_utils

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

  context "rq01" do
    scenario "racers#create_entry route exists and is correct" do
      expect(:post => "/racers/1/entries").to route_to("racers#create_entry", racer_id: "1")
    end
  end

  context "rq02" do
    before :all do
      init_mongo_db
    end
    
    scenario "racer#create_entry route will create a registration in the db" do 
      # get race and racer for post
      test_racer = Racer.first
      test_race = Race.upcoming_available_to(test_racer).first
      orig_entrant_ids = test_race.entrants.to_a.map{|e| e.id}
      page.driver.post("racers/#{test_racer.id}/entries?race_id=#{test_race.id}") 
      expect(page.status_code).to be < 400 
      updated_entrants = Race.find(test_race.id).entrants.to_a
      # confirm there is one more entrant that before
      expect(updated_entrants.count).to eql orig_entrant_ids.count + 1
      # now filter out the new entrant record and make sure it reflects racer and race
      new_entrant = updated_entrants.reject{|e| orig_entrant_ids.include? e.id}[0]
      expect(new_entrant.race.id).to eql test_race.id
      expect(new_entrant.racer.id).to eql test_racer.id
    end
  end

  context "rq03" do
    before :all do
      init_mongo_db
    end
    
    scenario "There is a Register link on the racers#edit view page" do 
      racer = Racer.first
      race_ids = (Race.upcoming_available_to(racer).to_a.map{ |r| r.id.to_s }).sort
      visit(edit_racer_path(racer.id))
      expect(page.status_code).to eq(200)  
      
      race_id_links = Array.new
      links = Array.new
      page.all('a').each { |l|
        link_uri = URI.parse(l[:href]).to_s
        if ((link_uri.split("/")).length > 3) then   
          cur_racer_id = (link_uri.split("/"))[2]   
          # now get race_id param
          links.push(l)
          race_id_links.push(((link_uri.split("/"))[3]).split("=")[1])
        end
      }
      expect(race_id_links.length).to eql race_ids.length
      expect(race_id_links.sort).to eql race_ids
      # now test link
      test_race_id = race_ids.sample
      orig_entrant_ids = Race.find(test_race_id).entrants.to_a.map{|e| e.id}

      find(:xpath, "//a[@href='#{racer_entries_path(racer.id, :race_id=>test_race_id)}']").click
      expect(page.status_code).to eq 200
      updated_entrants = Race.find(test_race_id).entrants.to_a
      # confirm there is one more entrant that before
      expect(updated_entrants.count).to eql orig_entrant_ids.count + 1
      # now filter out the new entrant record and make sure it reflects racer and race
      new_entrant = updated_entrants.reject{|e| orig_entrant_ids.include? e.id}[0]
      expect(new_entrant.race.id.to_s).to eql test_race_id
      expect(new_entrant.racer.id).to eql racer.id
    end
  end
end
