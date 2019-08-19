# uri_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 URIs", :type => :routing do
    include Capybara::DSL
    include Test_utils
    include HTTParty

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
    scenario "Application must have custom URIs that route to a controller#action" do 
      test_racer_id = "00000001"
      test_race_id = "00000002"
      test_result_id = "00000003"
      test_entry_id = "00000004"
      expect(:get => "/api/races").to be_routable     
      expect(:get => "/api/races/#{test_race_id}").to be_routable       
      expect(:get => "/api/races/#{test_race_id}/results").to be_routable     
      expect(:get => "/api/races/#{test_race_id}/results/#{test_result_id}").to be_routable  

      expect(:get => "/api/racers").to be_routable    
      expect(:get => "/api/racers/#{test_racer_id}").to be_routable       
      expect(:get => "/api/racers/#{test_racer_id}/entries").to be_routable   
      expect(:get => "/api/racers/#{test_racer_id}/entries/#{test_entry_id}").to be_routable   
    end
  end

  context "rq03" do
    scenario "GET requests return 200/OK and echo URI when Accept format not given" do 
      uri = ['/api/racers', '/api/racers/abc', '/api/racers/abc/entries', '/api/racers/abc/entries/def', 
             '/api/races', '/api/races/abc', '/api/races/abc/results', '/api/races/abc/results/def']

      uri.each { |u|
        page.driver.header('Accept', nil)
        page.driver.get(u)
        expect(page.status_code).to eql(200)
        expect(page.body.split(',')[0]).to eql(u)
        expect(page.response_headers["Content-Type"]).to include("text/plain")
      }
    end

    scenario "Requests to URI that are not implemented result in a 404 error" do
      bad_uri = '/api/races/abc/foo/def'
      page.driver.get(bad_uri)
      expect(page.status_code).to eql(404)
    end
  end

  context "rq04" do
    scenario "Controller for /api/races accepts POST request and renders 200/OK response" do
      page.driver.post("/api/races") 
      expect(page.status_code).to eql(200)
    end
  end

  context "rq05" do 
    scenario "TriResultsWS class created as indicated" do
      expect(class_exists?("TriResultsWS"))
      expect(TriResultsWS.included_modules).to include(HTTParty)
      expect(TriResultsWS.base_uri).to start_with("http://")
    end
  end
end
