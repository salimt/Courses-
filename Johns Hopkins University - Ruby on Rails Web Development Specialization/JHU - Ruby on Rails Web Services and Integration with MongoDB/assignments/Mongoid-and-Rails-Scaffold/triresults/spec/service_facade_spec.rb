# service_facade.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Cross-Collection Service Requests" do
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
    it "Race class has Integer field next_bib that defaults to 0" do
      expect(Race).to have_field(:next_bib).of_type(Integer)
      expect(Race.new).to respond_to(:next_bib)
    end

    it "Point Model has an instance method called next_bib that increments value" do
      expect(Race.new.method(:next_bib).parameters.flatten.count).to eq 0      
      race = Race.new
      expect(race[:next_bib]).to eql 0
      (1..9).each {|i| 
        expect(race.next_bib).to eql i
      }
    end
  end  

  context "rq02" do 
    it "Race class has a get_group instance method" do
      expect(Race.new).to respond_to(:get_group)
      expect((Race.new.method(:get_group).parameters.flatten - [:opt, :req]).count).to eq 1            
      expect(Race.new.method(:get_group).parameters.flatten).to include(:req)        
      expect(Race.new.method(:get_group).parameters.flatten).to_not include(:opt)        
    end

    it "get_group method creates Placing object whose name is group by age of racer" do
      expect(race = Race.new(:date=>Date.new(2016, 8, 15))).to_not be_nil
      expect(racer = Racer.new(first_name:"Cat", last_name:"Inhat", gender:"M", birth_year:1990)).to_not be_nil
      age_at_race = race.date.year - racer.birth_year
      expect(result = race.get_group(racer)).to_not be_nil
      expect(result).to be_a Placing
      expect(parts = result.name.split).to_not be_nil
      expect(age_at_race - parts[0].to_i).to be <= 9
      expect(parts[0].to_i % 10).to eql 0
      expect(parts[2].to_i - parts[0].to_i).to eql 9
      expect(parts[3]).to eql "(#{racer.gender})"

      expect(racer = Racer.new(first_name:"Thing", last_name:"One", gender:"F", birth_year:1950)).to_not be_nil
      age_at_race = race.date.year - racer.birth_year
      expect(result = race.get_group(racer)).to_not be_nil
      expect(parts = result.name.split).to_not be_nil
      expect(parts[0]).to eql "masters"
      expect(parts[1]).to eql racer.gender
    end
  end

  context "rq03" do 
    before :all do
      Racer.collection.delete_many
      Race.collection.delete_many
      Entrant.collection.delete_many
    end

    it "Race class has a create_entrant instance method" do
      expect(Race.new).to respond_to(:create_entrant)
      expect((Race.new.method(:create_entrant).parameters.flatten - [:opt, :req]).count).to eq 1            
      expect(Race.new.method(:create_entrant).parameters.flatten).to include(:req)        
      expect(Race.new.method(:create_entrant).parameters.flatten).to_not include(:opt)        
    end

    it "if new Entrant not valid, no persist or change will occur and error will be returned" do
      expect(race = Race.create).to_not be_nil
      race.events.build(o:0, n:"swim", d:1, u:"miles") 
      race.events.build(o:1, n:"t1")                   
      race.events.build(o:2, n:"bike", d:25, u:"miles") 
      race.events.build(o:3, n:"t2")
      race.events.build(o:4, n:"run", d:10, u:"kilometers") 
      expect(race[:next_bib]).to eql 0
      expect(entrant = race.create_entrant(Racer.new)).to_not be_nil
      expect(entrant).to_not be_valid
      expect(entrant).to_not be_persisted
      expect(race[:next_bib]).to eql 0
      expect(entrant.errors.messages[:racer].count).to be > 0
      expect(errors = entrant.racer.errors.messages).to_not be_nil
      expect(errors[:first_name].count).to be > 0
      expect(errors[:last_name].count).to be > 0
      expect(errors[:gender].count).to be > 0
      expect(errors[:birth_year].count).to be > 0

    end

    it "If valid create_entrant assigns values from Race and Racer and updates bib" do
      expect(racer = Racer.create(first_name:"cat", last_name:"inhat", gender:"M", birth_year:1940)).to_not be_nil
      expect(racer.races.count).to eql 0
      expect(race = Race.create(name:"Oakland 10K", date:Date.new(2015, 12, 21))).to_not be_nil
      race.events.build(o:0, n:"swim", d:1, u:"miles") 
      expect(race[:next_bib]).to eql 0
      expect(race.entrants.count).to eql 0

      expect(entrant = race.create_entrant(racer)).to_not be_nil
      expect(entrant).to be_persisted
      expect(entrant).to be_valid
      expect(entrant.bib).to eql 1
      # test whether entrant is updated with race event data
      expect(entrant.results[0].event.o).to eq(0)
      expect(entrant.results[0].event.n).to eq("swim")
      expect(entrant.results[0].event.d).to eq(1)
      expect(entrant.results[0].event.u).to eq("miles")
      expect(race[:next_bib]).to eql 1

      expect(doc = Entrant.find(entrant.id).attributes).to_not be_nil
      expect(doc["bib"]).to eql entrant.bib
      expect(doc["race"]["_id"]).to eql race.id
      expect(doc["race"]["n"]).to eql race.name
      expect(doc["race"]["date"].to_date).to eql race.date

      expect(doc["racer"]["fn"]).to eql racer.first_name
      expect(doc["racer"]["ln"]).to eql racer.last_name
      expect(doc["racer"]["g"]).to eql racer.gender
      expect(doc["racer"]["yr"]).to eql racer.birth_year
      expect(doc["racer"]["racer_id"]).to eql racer.id

      expect(race = Race.find(race.id)).to_not be_nil
      expect(race.entrants.first.racer.first_name).to eql racer.first_name
      expect(race.entrants.first.racer.last_name).to eql racer.last_name
      expect(racer = Racer.find(racer.id)).to_not be_nil
      expect(racer.races.first.race_name).to eql race.name
      expect(racer.races.first.race_date).to eql race.date
      expect(racer.races.first.bib).to eql race[:next_bib]
    end
  end

  context "rq04" do 
    before :each do
      race1=Race.create(:name=>"Yesterday's Entrant",:date=>Date.yesterday) 
      race2=Race.create(:name=>"Today's Entrant",:date=>Date.today)              
      race3=Race.create(:name=>"Tomorrow's Entrant",:date=>Date.tomorrow)    
      racer=Racer.create(:first_name=>"thing",:last_name=>"two",:gender=>"M",:birth_year=>1960)              
      race1.create_entrant(racer)
      race2.create_entrant(racer)
      race3.create_entrant(racer)
    end

    it "Entrant has upcoming scope that returns all future item in query" do
      expect(results = Entrant.upcoming).to_not be_nil
      results.each {|r| 
        expect(r.race_date >= Date.today)
      }
    end

    it "Entrant has past scope that returns all past items in query" do
      expect(results = Entrant.past).to_not be_nil
      results.each {|r| 
        expect(r.race_date < Date.today)
      }
    end
  end

  context "rq05" do
    before :all do
      Racer.collection.delete_many
      Race.collection.delete_many
      Entrant.collection.delete_many
      @race1=Race.create(:name=>"Tomorrow's Race 1",:date=>Date.tomorrow) 
      race2=Race.create(:name=>"Yesterday's Race",:date=>Date.yesterday)              
      race3=Race.create(:name=>"Tomorrow's Race 2",:date=>Date.tomorrow)    
      @racer=Racer.create(:first_name=>"thing",:last_name=>"two",:gender=>"M",:birth_year=>1960)              
      race3.create_entrant(@racer)      
    end

    it "Race implements a class method upcoming_available_to" do
      expect(Race).to respond_to(:upcoming_available_to)
      expect((Race.method(:upcoming_available_to).parameters.flatten - [:opt, :req]).count).to eql 1
      expect(Race.method(:upcoming_available_to).parameters.flatten).to include(:req)
      expect(Race.method(:upcoming_available_to).parameters.flatten).to_not include(:opt)
    end

    it "upcoming_available_to gets all future races that given racer has not entered" do
      expect(races = Race.upcoming_available_to(@racer)).to_not be_nil
      expect(races).to be_a Mongoid::Criteria
      expect(races.count).to eql 1
      expect(races.first.id).to eql @race1.id
    end
  end
end