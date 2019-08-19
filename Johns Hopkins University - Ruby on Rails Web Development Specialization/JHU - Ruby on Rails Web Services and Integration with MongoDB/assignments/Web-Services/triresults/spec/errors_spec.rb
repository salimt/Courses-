# errors_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Error Conditions", :type => :routing do
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
    scenario "PUT /api/races/:id to a nonexistant record error results in formatted error msg" do
      bad_id = BSON::ObjectId.new
      newDate = Date.current.iso8601
      expect(j_string = {race: {name:"Modified Race", date:newDate}}.to_json).to_not be_nil
      page.driver.header('Content-Type', 'application/json')
      page.driver.put("/api/races/#{bad_id}", j_string) 
      expect(page.status_code).to eql(404)
      if (page.response_headers["Content-Type"]).include?("text/plain") then   
        expect(body_data = page.body).to_not be_nil
        expect(body_data.split()).to include("race[#{bad_id}]")
      else
        expect(body_data = JSON.parse(page.body)["msg"]).to_not be_nil
        expect(body_data.split()).to include("race[#{bad_id}]")
      end
    end
  end

end
