# params_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Query Parameters and Post Data", :type => :routing do
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
    scenario "GET /api/races handles a query string with paging parameters" do 
      offset_val = 10
      limit_val = 50
      uri = "/api/races?offset=#{offset_val}&limit=#{limit_val}"
      page.driver.header('Accept', nil)
      page.driver.get(uri)
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("text/plain")      
      expect(qparts = page.body.split(",")).to_not be_nil
      expect(m = /offset=\[(\d+)\]/.match(qparts[1])).to_not be_nil
      expect(m[1]).to eql offset_val.to_s 
      expect(m = /limit=\[(\d+)\]/.match(qparts[2])).to_not be_nil
      expect(m[1]).to eql limit_val.to_s
    end
  end

  context "rq02" do
    scenario "Controller for /api/races accepts POST request and renders 200/OK response" do
      page.driver.header('Content-Type', nil)
      page.driver.post("/api/races", :race=>{:name=>"Meager Mile"}) 
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("text/plain")
      expect(page.body).to eql("Meager Mile")
    end

    scenario "Post to /api/races with Content-Type and data mismatch returns 400 error" do
      page.driver.header('Content-Type', 'application/json')
      page.driver.post("/api/races", :race=>{:name=>"Meager Mile"}) 
      expect(page.status_code).to eql(400)
    end    

    scenario "Post to /api/races with Content-Type and data as json returns 200/OK and data" do
      page.driver.header('Content-Type', 'application/json')
      page.driver.post("/api/races", {:race=>{:name=>"Meager Mile"}}.to_json) 
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("text/plain")
      expect(page.body).to eql("Meager Mile")      
    end     
  end
end
