# resources_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #4 Resource Implementation (JSON)", :type => :routing do
    include Capybara::DSL
    include Test_utils

  before :all do
    $continue = true
  end

  before :each do
    Race.delete_all
    init_complete_race
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
    # There is no test for this step
  end

  context "rq02" do
    scenario "GET /api/races/:race_id/results/:id returns a specific entrant for a race" do
      expect(race = Race.past.first).to_not be_nil
      expect(entrant = race.entrants.first).to_not be_nil
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results/#{entrant.id}")
      expect(page.status_code).to eql(200)
      expect(page.response_headers["Content-Type"]).to include("application/json")
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(hash["first_name"]).to eql(entrant.first_name)
      expect(hash["last_name"]).to eql(entrant.last_name)
      expect(hash["time"]).to eql(format_hours(entrant.secs))
      expect(hash["bib"]).to eql(entrant.bib)
      expect(hash["city"]).to eql(entrant.city)
      expect(hash["state"]).to eql(entrant.state)
      expect(hash["state"]).to eql(entrant.state)
      expect(hash["gender_place"]).to eql(entrant.gender_place)
      expect(hash["group"]).to eql(entrant.group.name)
      expect(hash["group_place"]).to eql(entrant.group_place)
      expect(hash["swim"]).to eql(format_hours(entrant.swim_secs))
      expect(hash["pace_100"]).to eql(format_minutes(entrant.swim_pace_100))
      expect(hash["bike"]).to eql(format_hours(entrant.bike_secs))
      expect(hash["mph"]).to eql(format_mph(entrant.bike_mph))
      expect(hash["run"]).to eql(format_hours(entrant.run_secs))
      expect(hash["mmile"]).to eql(format_minutes(entrant.run_mmile))
    end
  end

  context "rq03" do
    let(:evt_array) { [:swim, :t1, :bike, :t2, :run] }
    let(:time_array) { [15.minutes, 1.minute, 40.minutes, 40.seconds, 25.minutes]}
    scenario "PATCH /api/races/:race_id/results/:id accepts race result updates" do
      # setup Race with new entrant
      num_entrants = -1
      race = nil
      while (num_entrants != 0) do
        race = Race.upcoming.to_a.sample
        num_entrants = race.entrants.count
      end
      racer = Racer.first
      entrant = race.create_entrant(racer)
      expect(c_values = Entrant.where(id:entrant.id).pluck(:created_at, :updated_at, :secs).flatten).to_not be_nil
      c_updated_time = c_values[1]
      total_secs = c_values[2] ||= 0.0

      (0..evt_array.length-1).each { |n|
        j_string = {:result=>{evt_array[n]=>time_array[n]}}.to_json
        total_secs = total_secs + time_array[n]
        expect(:patch => "/api/races/#{race.id}/results/#{entrant.id}").to be_routable
        page.driver.header('Content-Type', 'application/json')
        page.driver.submit(:patch, "/api/races/#{race.id}/results/#{entrant.id}", j_string)
        expect(page.status_code).to eql 200
        expect(c_values = Entrant.where(id:entrant.id).pluck(:created_at, :updated_at, :secs).flatten).to_not be_nil
        expect(c_values[2]).to eql(total_secs)
        expect(c_values[1]).to be > c_updated_time
        c_updated_time = c_values[1]
      }

      # get final entrant and compare results
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results/#{entrant.id}")
      expect(page.status_code).to eql 200
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(hash["time"]).to eql format_hours(total_secs)
      expect(hash["swim"]).to eql format_hours(time_array[0])
      expect(hash["t1"]).to eql format_minutes(time_array[1])
      expect(hash["bike"]).to eql format_hours(time_array[2])
      expect(hash["t2"]).to eql format_minutes(time_array[3])
      expect(hash["run"]).to eql format_hours(time_array[4])
    end
  end

  context "rq04" do
    #There is no test for this step
  end

  context "rq05" do
    scenario "GET /api/races/:race_id/results returns a race's entrants" do
      # get an existing race with entrants
      race = Race.first
      entrants = race.entrants
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql 200
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(hash.count).to eql entrants.count
      #check over hash that all entrants are there
      entrants.each { |e|
        e_result = hash.select { |r| e.first_name == r["first_name"]  && e.last_name == r["last_name"]}[0]
        expect(e_result["first_name"]).to eql(e.first_name)
        expect(e_result["last_name"]).to eql(e.last_name)
        expect(e_result["time"]).to eql(format_hours(e.secs))
        expect(e_result["bib"]).to eql(e.bib)
        expect(e_result["city"]).to eql(e.city)
        expect(e_result["state"]).to eql(e.state)
        expect(e_result["state"]).to eql(e.state)
        expect(e_result["gender_place"]).to eql(e.gender_place)
        expect(e_result["group"]).to eql(e.group.name)
        expect(e_result["group_place"]).to eql(e.group_place)
        expect(e_result["swim"]).to eql(format_hours(e.swim_secs))
        expect(e_result["pace_100"]).to eql(format_minutes(e.swim_pace_100))
        expect(e_result["bike"]).to eql(format_hours(e.bike_secs))
        expect(e_result["mph"]).to eql(format_mph(e.bike_mph))
        expect(e_result["run"]).to eql(format_hours(e.run_secs))
        expect(e_result["mmile"]).to eql(format_minutes(e.run_mmile))
      }
    end

    scenario "GET /api/races/:race_id/results returns nil when race has no entrants" do
      race = Race.upcoming.first
      expect(race.entrants.count).to eql 0
      page.driver.header('Accept', 'application/json')
      page.driver.get("/api/races/#{race.id}/results")
      expect(page.status_code).to eql 200
      expect(hash = JSON.parse(page.body)).to_not be_nil
      expect(hash.count).to eql 0
      expect(hash.first).to be_nil
    end
  end
end
