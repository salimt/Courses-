# representations_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Representations", :type => :routing do
    include Capybara::DSL
    include Test_utils

  before :all do
    $continue = true
  end

  before :each do   
    Race.delete_all
  end

  around  :each do |example|
    if $continue
      $continue = false 
      example.run 
      $continue = true unless example.exception
    else
      example.skip
    end
  end

  context "rq01" do
    scenario "GET /api/races/:id with Accept:application/xml returns xml formatted output/error" do    
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      page.driver.header('Accept', 'application/xml')  
      page.driver.get("/api/races/#{test_race.id}")
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("application/xml")
      expect(hash = Hash.from_xml(page.body)).to_not be_nil
      expect(hash["race"]["name"]).to eql(test_race.name)
      expect(hash["race"]["date"]).to eql(test_race.date.to_s)
    
      bad_id = BSON::ObjectId.new
      newDate = Date.current.iso8601
      page.driver.header('Content-Type', 'application/xml')
      page.driver.get("/api/races/#{bad_id}") 
      expect(page.status_code).to eql(404)
      expect(page.response_headers["Content-Type"]).to include("application/xml")      
      expect(hash = Hash.from_xml(page.body)).to_not be_nil
      expect(e_msg = hash["error"]["msg"]).to_not be_nil
      expect(e_msg.split()).to include("race[#{bad_id}]")
    end
  end

  context "rq02" do
    scenario "GET /api/races/:id with Accept:application/json returns json formatted output/error" do    
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      page.driver.header('Accept', 'application/json')  
      page.driver.get("/api/races/#{test_race.id}")
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("application/json")
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(hash["name"]).to eql(test_race.name)
      expect(hash["date"]).to eql(test_race.date.to_s)
    
      bad_id = BSON::ObjectId.new
      newDate = Date.current.iso8601
      page.driver.header('Content-Type', 'application/json')
      page.driver.get("/api/races/#{bad_id}") 
      expect(page.status_code).to eql(404)
      expect(page.response_headers["Content-Type"]).to include("application/json")      
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(e_msg = hash["msg"]).to_not be_nil
      expect(e_msg.split()).to include("race[#{bad_id}]")
    end
  end

  context "rq03" do
    scenario "GET /api/races/:id with unsupported Accept Mime Type results in a 415 code and message" do
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      page.driver.header('Accept', 'text/plain')  
      page.driver.get("/api/races/#{test_race.id}")
      expect(page.status_code).to eql(415)
      expect(page.response_headers["Content-Type"]).to include('text/plain')
      expect(page.body).to include("content-type[text/plain]")
    end
  end
end
