# methods_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Methods", :type => :routing do
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
    scenario "POST /api/races creates a new Race in database and satisfies requirements" do 
      expect(j_string = {race: {name:"First Race", date:Date.current.iso8601}}.to_json).to_not be_nil
      page.driver.header('Content-Type', 'application/json')
      page.driver.header('Accept', 'text/plain')
      page.driver.post("/api/races", j_string) 
      expect(page.status_code).to eql(201)
      expect(page.response_headers["Content-Type"]).to include("text/plain")
      expect(page.body).to eql("First Race")  
      expect(Race.find_by(:name=>"First Race", :date=>Date.current)).to_not be_nil
    end
  end

  context "rq02" do
    scenario "GET /api/races/:id retrieves an existing race from the database" do
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      page.driver.header('Accept', 'application/json')  
      page.driver.get("/api/races/#{test_race.id}")
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("application/json")
      expect(body_data = JSON.parse(page.body)).to_not be_nil
      expect(test_race.name).to eql(body_data["n"]).or eql(body_data["name"])
      expect(test_race.date.to_s).to eql body_data["date"]
    end    
  end

  context "rq03" do 
    # There is no test for this spec
  end

  context "rq04" do
    scenario "PUT /api/races/:id udpates an existing race in the database" do
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      newDate = Date.current.iso8601
      expect(j_string = {race: {name:"Modified Race", date:newDate}}.to_json).to_not be_nil
      page.driver.header('Content-Type', 'application/json')
      page.driver.put("/api/races/#{test_race.id}", j_string) 
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("application/json")      
      expect(body_data = JSON.parse(page.body)).to_not be_nil
      expect(body_data["_id"]["$oid"]).to eql(test_race.id.to_s)
      expect(body_data["n"]).to eql("Modified Race")
      db_race = Race.find(test_race.id)
      expect(db_race.name).to eql("Modified Race")
      expect(db_race.date.to_s).to eql(newDate)
    end
  end

  context "rq05" do 
    # There is no test for this spec
  end

  context "rq06" do 
    scenario "DELETE /api/races/:id destroys race by :id in database" do
      expect(test_race = Race.create(:name=>"First Race", :date=>Date.current)).to_not be_nil
      expect(Race.find(test_race.id)).to_not be_nil
      page.driver.delete("/api/races/#{test_race.id}") 
      expect(page.status_code).to eql(204)
      expect{ Race.find(test_race.id) }.to raise_error(Mongoid::Errors::DocumentNotFound)
    end
  end
end
