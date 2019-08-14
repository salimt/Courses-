# web_resources.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

feature "Module #3 Web Race/Racer Resource Access", :type => :routing do
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
    before :all do
      #init_racer
      init_mongo_db
    end

    scenario "Racer must have expected controller and routes" do 
      test_racer_id = Racer.first.id
      expect(Dir.entries("./app/controllers")).to include("racers_controller.rb")
      expect(Dir.entries("./app/views/racers")).to include("index.html.erb")
      expect(Dir.entries("./app/views/racers")).to include("edit.html.erb")
      expect(Dir.entries("./app/views/racers")).to include("show.html.erb")
      expect(Dir.entries("./app/views/racers")).to include("new.html.erb")
      expect(Dir.entries("./app/views/racers")).to include("_form.html.erb")
      expect(:get => racers_path).to route_to(:controller => "racers", :action => "index")
      expect(:get => racer_path(test_racer_id)).to route_to(:controller => "racers", :action => "show", :id => "#{test_racer_id}")
    end

    scenario "Query to Racer URLs returns the selected image" do 
      visit(racers_path)
      expect(page.status_code).to eq(200)      
      expect(page).to have_content("Listing Racers")
      expect(page).to have_link('Show')
      page.click_link("Show", :match => :first)
      expect(page.status_code).to eq(200)
      link_uri = URI.parse(current_url).request_uri
      cur_racer_id = (link_uri.split("/"))[2]      
      expect(page).to have_content("First name: #{Racer.find(cur_racer_id).first_name}")
    end
  end

  context "rq02" do
    before :all do
      #init_race
      init_mongo_db
    end
    
    scenario "Race must have expected controller and routes" do 
      test_race_id = Race.first.id
      expect(Dir.entries("./app/controllers")).to include("races_controller.rb")
      expect(Dir.entries("./app/views/races")).to include("index.html.erb")
      expect(Dir.entries("./app/views/races")).to include("edit.html.erb")
      expect(Dir.entries("./app/views/races")).to include("show.html.erb")
      expect(Dir.entries("./app/views/races")).to include("new.html.erb")
      expect(Dir.entries("./app/views/races")).to include("_form.html.erb")
      expect(:get => races_path).to route_to(:controller => "races", :action => "index")
      expect(:get => race_path(test_race_id)).to route_to(:controller => "races", :action => "show", :id => "#{test_race_id}")
    end

    scenario "Query to Race URLs returns the selected image" do 
      visit(races_path)
      expect(page.status_code).to eq(200)      
      expect(page).to have_content("Listing Races")
      expect(page).to have_link('Show')
      page.click_link("Show", :match => :first)
      expect(page.status_code).to eq(200)
      link_uri = URI.parse(current_url).request_uri
      cur_race_id = (link_uri.split("/"))[2]      
      expect(page).to have_content("Name: #{Race.find(cur_race_id).name}")
    end
  end

  context "rq03" do
    before :all do
      init_mongo_db
    end

    scenario "Race show page must have table of all entrants information" do
      r_id = Entrant.where(:race.ne=>nil).first.race.id
      race = Race.find(r_id)
      entrants = race.entrants
      visit(race_path(r_id))
      expect(page.status_code).to eq(200)  
      expect(page.assert_selector('tr', :count=> entrants.length+1))
      page.all('tr').each { |row|
        cells = row.all('td')
        if (cells != nil && cells.count > 0)
          # use name field to get entrant to match
          fname = cells[2].text.split(",")[1].strip
          lname = cells[2].text.split(",")[0].strip
          expect(c_entrant = entrants.select{ |e| lname==e.last_name && fname==e.first_name}[0]).to_not be_nil
          expect(cells[0].text.to_i).to eql c_entrant.overall.place
          expect(cells[1].text).to eql format_hours(c_entrant.secs).strip
          expect(cells[3].text.to_i).to eql c_entrant.bib
          expect(cells[4].text).to eql c_entrant.city
          expect(cells[5].text).to eql c_entrant.state
          expect(cells[6].text).to eql c_entrant.racer.gender
          expect(cells[7].text.to_i).to eql c_entrant.gender.place
          expect(cells[10].text).to eql format_hours(c_entrant.swim_secs).strip
          expect(cells[11].text).to eql format_minutes(c_entrant.swim_pace_100).strip
          expect(cells[13].text).to eql format_hours(c_entrant.bike_secs).strip
          expect(cells[14].text.to_f).to eql c_entrant.bike_mph.round(1)
          expect(cells[16].text).to eql format_hours(c_entrant.run_secs).strip
          expect(cells[17].text).to eql format_minutes(c_entrant.run_mmile).strip
        end
      }
    end

    scenario "Race show page has links to all racers in entrants data" do
      r_id = Entrant.where(:race.ne=>nil).first.race.id
      race = Race.find(r_id)
      entrants = race.entrants
      visit(race_path(r_id))
      expect(page.status_code).to eq(200)  
      page.all('a').each { |l|
        link_uri = URI.parse(l[:href]).to_s
        if ((link_uri.split("/"))[1] == "racers") then    
          cur_racer_id = (link_uri.split("/"))[2]   
          r = Racer.find(cur_racer_id)
          page.click_link('a', :href=>l[:href])
          expect(page.status_code).to eq 200
          expect(page).to have_content("First name: #{r.first_name}")
          expect(page).to have_content("Last name: #{r.last_name}")
          expect(page).to have_content("Birth year: #{r.birth_year}")
          expect(page).to have_content("City: #{r.city}")
          expect(page).to have_content("State: #{r.state}")
          visit(race_path(r_id))
        end
      }
    end
  end

  context "rq04" do
    before :all do
      init_mongo_db
    end

    scenario "Racer show page must have table of all races racer has registered for" do
      r_id = Entrant.where(:racer.ne=>nil).first.racer.id
      racer = Racer.find(r_id)
      races = racer.races
      visit(racer_path(r_id))
      expect(page.status_code).to eq(200)  
      expect(page.assert_selector('tr', :count=> races.length+1))
      page.all('tr').each { |row|
        cells = row.all('td')
        if (cells != nil && cells.count > 0)
          # use name field to get entrant to match
          name = cells[0].text.strip
          expect(c_race = races.select{ |r| name==r.race.name}[0]).to_not be_nil
          expect(cells[1].text).to eql c_race.race.date.to_s
          expect(cells[2].text.to_i).to eql c_race.overall.place
          expect(cells[3].text.to_i).to eql c_race.gender.place
        end
      }
    end

    scenario "Race show page has links to all racers in entrants data" do
      r_id = Entrant.where(:racer.ne=>nil).first.racer.id
      racer = Racer.find(r_id)
      races = racer.races
      visit(racer_path(r_id))
      expect(page.status_code).to eq(200) 
      page.all('a').each { |l|
        link_uri = URI.parse(l[:href]).to_s
        if ((link_uri.split("/"))[1] == "races") then    
          cur_race_id = (link_uri.split("/"))[2]   
          r = Race.find(cur_race_id)
          page.click_link("Results")
          expect(page.status_code).to eq 200
          expect(page).to have_content("Name: #{r.name}")
          expect(page).to have_content("Date: #{r.date}")
          expect(page).to have_content("City: #{r.city}")
          expect(page).to have_content("State: #{r.state}")
          expect(page).to have_content("Swim distance: #{r.swim_distance}")          
          expect(page).to have_content("Swim units: #{r.swim_units}")
          expect(page).to have_content("Bike distance: #{r.bike_distance}")          
          expect(page).to have_content("Bike units: #{r.bike_units}")          
          expect(page).to have_content("Run distance: #{r.run_distance}")          
          expect(page).to have_content("Run units: #{r.run_units}")
          visit(racer_path(r_id))
        end
      }
    end
  end

  context "rq05" do
    scenario "Racers edit page has view of upcoming races not yet registered for" do
      r_id = Entrant.where(:racer.ne=>nil).first.racer.id
      racer = Racer.find(r_id)
      races = Race.upcoming_available_to(racer).order_by(:date.asc).to_a
      visit(edit_racer_path(r_id))
      expect(page.status_code).to eq(200)  
      expect(page.assert_selector('tr', :count=> races.length+1))
      page.all('tr').each { |row|
        cells = row.all('td')
        if (cells != nil && cells.count > 0)
          # use name field to get entrant to match
          name = cells[0].text.strip
          expect(c_race = races.select{ |r| name==r.name}[0]).to_not be_nil
          expect(cells[1].text).to eql c_race.date.to_s
          expect(cells[2].text).to eql c_race.city
          expect(cells[3].text).to eql c_race.state
          expect(cells[4].text.to_f).to eql c_race.swim_distance
          expect(cells[5].text).to eql c_race.swim_units
          expect(cells[6].text.to_f).to eql c_race.bike_distance
          expect(cells[7].text).to eql c_race.bike_units
          expect(cells[8].text.to_f).to eql c_race.run_distance
          expect(cells[9].text).to eql c_race.run_units          
        end
      }
    end
  end
end
