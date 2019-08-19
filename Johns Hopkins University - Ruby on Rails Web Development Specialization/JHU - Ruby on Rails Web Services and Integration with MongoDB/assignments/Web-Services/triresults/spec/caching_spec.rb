# caching_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Headers and Caching", :type => :routing do
    include Capybara::DSL
    include Test_utils

  before :all do
    $continue = true
  end

  before :each do
    Race.delete_all
    init_complete_race
    Capybara.reset_sessions!
    sleep(2)
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
    scenario "Races controller returns a Last_Modified header with collection of race results" do
      expect(race = Race.past.first).to_not be_nil
      expect(race.entrants.count).to be > 0
      expect(entrants = race.entrants).to_not be_nil
      e_dates = entrants.map{ |e| e.updated_at.to_datetime.to_i }
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql(200)
      # convert to seconds since epoch for comparison
      expect(ret_date = page.response_headers["Last-Modified"].to_datetime.to_i).to_not be_nil
      expect(ret_date).to eql race.entrants.max(:updated_at).to_i
    end
  end

  context "rq02" do
    scenario "Races controller will only return an update if client does not have current version" do
      expect(race = Race.past.first).to_not be_nil
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql(200)
      expect(ret_date = page.response_headers["Last-Modified"]).to_not be_nil

      # without change re-issue request is If-Modified-Since header
      # and note no results and 304 code
      page.driver.header('If-Modified-Since', ret_date)
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql 304
      expect(ret_date = page.response_headers["Last-Modified"]).to_not be_nil
    end
  end

  context "rq03" do
    scenario "Races controller will return an update if data changes since last client request" do
      begin
        race = Race.all.sample
      end while race.entrants.count == 0
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql(200)
      expect(ret_date = page.response_headers["Last-Modified"]).to_not be_nil
      parsed_response = JSON.parse(page.body)
      expect(res_url = parsed_response.first["result_url"]).to_not be_nil

      # make change the re-issue request is If-Modified-Since header
      # and note the results and 200 code
      sleep(1)
      j_string = {:result=>{:swim=>55.minute}}.to_json
      page.driver.header('Accept', 'application/json')
      page.driver.header('Content-Type', 'application/json')
      page.driver.submit(:patch, res_url, j_string)
      expect(page.status_code).to eql 200
      # get updated race updated_at
      expect(Race.find(race.id).entrants.max(:updated_at).to_i).to be > ret_date.to_datetime.to_i

      # requery after change
      page.driver.header('If-Modified-Since', ret_date)
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql 200
      expect(ret_date = page.response_headers["Last-Modified"]).to_not be_nil
    end
  end
end
