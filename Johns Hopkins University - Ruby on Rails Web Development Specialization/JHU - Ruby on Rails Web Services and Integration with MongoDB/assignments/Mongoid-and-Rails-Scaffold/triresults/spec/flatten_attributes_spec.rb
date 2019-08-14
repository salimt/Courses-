# flatten_attributes.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'
require 'pp'

describe "Module #3 Summative: Implement Attribute Delegation" do
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
    before :each do
      @r = Racer.new(:first_name=>"thing", :last_name=>"two", :gender=>"M", :birth_year=>1960)
    end

    it "Racer class has delegation to simplify calls to RacerInfo" do
      expect(@r.info.last_name).to eql(@r.last_name).and eql("two")
      expect(@r.info.first_name).to eql(@r.first_name).and eql("thing")
      expect(@r.info.gender).to eql(@r.gender).and eql("M")
      expect(@r.info.birth_year).to eql(@r.birth_year).and eql(1960)
      # test setters
      @r.last_name="three"
      expect(@r.info.last_name).to eql(@r.last_name).and eql("three")
      @r.first_name="athing"
      expect(@r.info.first_name).to eql(@r.first_name).and eql("athing")
      @r.gender="F"
      expect(@r.info.gender).to eql(@r.gender).and eql("F")
      @r.birth_year=1959
      expect(@r.info.birth_year).to eql(@r.birth_year).and eql(1959)
      # test validation and saving
      expect(@r.validate).to be true
      expect(@r.save).to be true
      expect(doc = Racer.find(@r.id).attributes).to_not be_nil
      expect(doc["info"]["fn"]).to eql "athing"
      expect(doc["info"]["ln"]).to eql "three"
      expect(doc["info"]["g"]).to eql "F"
      expect(doc["info"]["yr"]).to eql 1959
      expect(doc["info"]["racer_id"]).to_not be_nil
    end 

    it "Racer delegation to custom types like Address is accounted for in model" do
      expect(@r.city).to be_nil
      @r.city="Oakland"
      @r.state="CA" 
      expect(@r.info.residence.city).to eql "Oakland"
      expect(@r.info.residence.state).to eql "CA"
      expect(@r.save).to be true
      expect(doc = Racer.find(@r.id).attributes).to_not be_nil
      expect(doc["info"]["fn"]).to eql "thing"
      expect(doc["info"]["ln"]).to eql "two"
      expect(doc["info"]["g"]).to eql "M"
      expect(doc["info"]["yr"]).to eql 1960
      expect(doc["info"]["res"]["city"]).to eql "Oakland"
      expect(doc["info"]["res"]["state"]).to eql "CA"      
      expect(doc["info"]["racer_id"]).to_not be_nil      
    end
  end

  context "rq02" do
    it "Race class has been flattened to access and set Event information" do
      expect(race = Race.new).to_not be_nil
      expect(e_swim = race.swim).to_not be_nil
      expect(e_swim).to be_a Event
      expect(e_swim.order).to eql Race::DEFAULT_EVENTS["swim"][:order]
      expect(e_swim.name).to eql Race::DEFAULT_EVENTS["swim"][:name]
      expect(e_swim.distance).to eql Race::DEFAULT_EVENTS["swim"][:distance].to_f
      expect(e_swim.units).to eql Race::DEFAULT_EVENTS["swim"][:units]

      expect(e_t1 = race.t1).to_not be_nil
      expect(e_t1).to be_a Event
      expect(e_t1.order).to eql Race::DEFAULT_EVENTS["t1"][:order]
      expect(e_t1.name).to eql Race::DEFAULT_EVENTS["t1"][:name]
      expect(e_t1.distance).to eql Race::DEFAULT_EVENTS["t1"][:distance]
      expect(e_t1.units).to eql Race::DEFAULT_EVENTS["t1"][:units]

      expect(e_bike = race.bike).to_not be_nil
      expect(e_bike).to be_a Event
      expect(e_bike.order).to eql Race::DEFAULT_EVENTS["bike"][:order]
      expect(e_bike.name).to eql Race::DEFAULT_EVENTS["bike"][:name]
      expect(e_bike.distance).to eql Race::DEFAULT_EVENTS["bike"][:distance].to_f
      expect(e_bike.units).to eql Race::DEFAULT_EVENTS["bike"][:units]

      expect(e_t2 = race.t2).to_not be_nil
      expect(e_t2).to be_a Event
      expect(e_t2.order).to eql Race::DEFAULT_EVENTS["t2"][:order]
      expect(e_t2.name).to eql Race::DEFAULT_EVENTS["t2"][:name]
      expect(e_t2.distance).to eql Race::DEFAULT_EVENTS["t2"][:distance]
      expect(e_t2.units).to eql Race::DEFAULT_EVENTS["t2"][:units] 

      expect(e_run = race.run).to_not be_nil
      expect(e_run).to be_a Event
      expect(e_run.order).to eql Race::DEFAULT_EVENTS["run"][:order]
      expect(e_run.name).to eql Race::DEFAULT_EVENTS["run"][:name]
      expect(e_run.distance).to eql Race::DEFAULT_EVENTS["run"][:distance].to_f
      expect(e_run.units).to eql Race::DEFAULT_EVENTS["run"][:units]  

      e_run.distance=1000.0
      e_swim.units="meters"    
      e_bike.distance=100.0
      race.events.to_a.each { |e| 
        case e.name
        when "swim"
          expect(e.units).to eql "meters"
        when "bike"
          expect(e.distance).to eql 100.0
        when "run"
          expect(e.distance).to eql 1000.0
        end
      }                
    end

    it "Race has a class method called default that takes no parameters and returns a Race with properties" do
      expect(race = Race.default).to_not be_nil
      expect(race.save).to be true
      expect(doc = Race.find(race.id).attributes).to_not be_nil
      doc["events"].each { |e|
        expect(etype = e["n"]).to_not be_nil
        expect(e["o"]).to eql Race::DEFAULT_EVENTS[etype][:order]
        expect(e["n"]).to eql Race::DEFAULT_EVENTS[etype][:name]
        expect(e["d"]).to eql (Race::DEFAULT_EVENTS[etype][:distance] ? Race::DEFAULT_EVENTS[etype][:distance].to_f : nil)
        expect(e["u"]).to eql Race::DEFAULT_EVENTS[etype][:units]
      }
    end

    it "Race class has been flattened to access and set location information" do
      expect(race = Race.new).to_not be_nil
      race.city="Houston"
      race.state="TX"
      expect(race.save).to be true
      expect(race = Race.find(race.id)).to_not be_nil
      expect(race["location"]["city"]).to eql "Houston"
      expect(race["location"]["state"]).to eql "TX"

      expect(race = Race.new).to_not be_nil
      expect(addr = Address.demongoize({})).to_not be_nil
      addr.city = "New York"
      addr.state = "NY"
      race.location = addr
      expect(race.city).to eql "New York"
      expect(race.state).to eql "NY"
    end

  end

  context "rq03" do
    it "Entrant class has flattened configuration relative to Race and Racer" do
      expect(entrant = Entrant.new(:bib=>0, :first_name=>"thing", :last_name=>"One", :racer_gender=>"M")).to_not be_nil
      expect(entrant.bib).to eql 0
      expect(entrant.first_name).to eql(entrant.racer.first_name).and eql("thing")
      expect(entrant.last_name).to eql(entrant.racer.last_name).and eql("One")
      entrant.birth_year=2000
      expect(entrant.birth_year).to eql(entrant.racer.birth_year).and eql (2000)
      entrant.city="Houston"
      entrant.racer.state="TX"
      expect(entrant.city).to eql(entrant.racer.city).and eql("Houston")
      expect(entrant.state).to eql(entrant.racer.state).and eql("TX")
    end

    it "gender is accessed via racer prefix and name, date are accessed via race prefix" do\
      expect(entrant = Entrant.new(:bib=>0, :first_name=>"thing", :last_name=>"One", :racer_gender=>"M")).to_not be_nil
      expect(entrant.racer_gender).to eql(entrant.racer.gender).and eql("M")
      entrant.race.name="Houston 500"
      entrant.race.date=Date.new(2016, 8,1)
      expect(entrant.race_name).to eql(entrant.race.name).and eql("Houston 500")
      expect(entrant.race_date).to eql(entrant.race.date).and eql(Date.new(2016, 8, 1))      
    end

    it "Embedded race and racer instances will be in place for access and update" do   
      expect(Entrant).to embed_one(:race).of_type(RaceRef).with_autobuild
      expect(Entrant).to embed_one(:racer).of_type(RacerInfo).with_autobuild
      expect(e = Entrant.new).to_not be_nil
      expect(e.race).to_not be_nil
      expect(e.racer).to_not be_nil
    end

    it "Overall, gender and group placings are flattened through methods" do
      expect(Entrant.new).to respond_to(:overall_place)
      expect(Entrant.new.method(:overall_place).parameters.flatten.count).to eq 0
      expect(Entrant.new).to respond_to(:gender_place)
      expect(Entrant.new.method(:gender_place).parameters.flatten.count).to eq 0
      expect(Entrant.new).to respond_to(:group_place)
      expect(Entrant.new.method(:group_place).parameters.flatten.count).to eq 0
      expect(Entrant.new).to respond_to(:group_name)
      expect(Entrant.new.method(:group_name).parameters.flatten.count).to eq 0

      expect(entrant = Entrant.new).to_not be_nil
      expect(entrant.overall_place).to be_nil
      expect(entrant.group_place).to be_nil
      expect(entrant.group_name).to be_nil
      expect(entrant.gender_place).to be_nil
      grp_place = Placing.demongoize(:name=>"Group", :place=>9)
      gen_place = Placing.demongoize(:name=>"M", :place=>43)
      ovr_place = Placing.demongoize(:name=>"Overall", :place=>133)
      entrant.overall = ovr_place
      entrant.group = grp_place
      entrant.gender = gen_place
      expect(entrant.overall_place).to eql 133
      expect(entrant.group_name).to eql "Group"
      expect(entrant.group_place).to eql 9
      expect(entrant.gender_place).to eql 43
    end
  end

  context "rq04" do 
    before :each do
      race = Race.new.tap {|race| ["swim", "t1", "bike", "t2", "run"].each {|event|race.send(event)}}
      expect(@entrant = Entrant.new(:bib=>0, :first_name=>"thing", :last_name=>"One", :racer_gender=>"M")).to_not be_nil
      @entrant.swim=race.swim
      @entrant.t1=race.t1
      @entrant.bike=race.bike
      @entrant.t2=race.t2
      @entrant.run=race.run
    end

    it "Using flat methods can create an entrant and assign event details from a Race" do
      expect(@entrant.swim).to be_a SwimResult
      expect(@entrant.swim.event.order).to eql Race::DEFAULT_EVENTS["swim"][:order]
      expect(@entrant.swim.event.name).to eql Race::DEFAULT_EVENTS["swim"][:name]
      expect(@entrant.swim.event.distance).to eql Race::DEFAULT_EVENTS["swim"][:distance].to_f
      expect(@entrant.swim.event.units).to eql Race::DEFAULT_EVENTS["swim"][:units]
      expect(@entrant.t1).to be_a LegResult
      expect(@entrant.t1.event.order).to eql Race::DEFAULT_EVENTS["t1"][:order]
      expect(@entrant.t1.event.name).to eql Race::DEFAULT_EVENTS["t1"][:name]
      expect(@entrant.t1.event.distance).to eql Race::DEFAULT_EVENTS["t1"][:distance]
      expect(@entrant.t1.event.units).to eql Race::DEFAULT_EVENTS["t1"][:units]
      expect(@entrant.bike).to be_a BikeResult
      expect(@entrant.bike.event.order).to eql Race::DEFAULT_EVENTS["bike"][:order]
      expect(@entrant.bike.event.name).to eql Race::DEFAULT_EVENTS["bike"][:name]
      expect(@entrant.bike.event.distance).to eql Race::DEFAULT_EVENTS["bike"][:distance].to_f
      expect(@entrant.bike.event.units).to eql Race::DEFAULT_EVENTS["bike"][:units]      
      expect(@entrant.t2).to be_a LegResult
      expect(@entrant.t2.event.order).to eql Race::DEFAULT_EVENTS["t2"][:order]
      expect(@entrant.t2.event.name).to eql Race::DEFAULT_EVENTS["t2"][:name]
      expect(@entrant.t2.event.distance).to eql Race::DEFAULT_EVENTS["t2"][:distance]
      expect(@entrant.t2.event.units).to eql Race::DEFAULT_EVENTS["t2"][:units]
      expect(@entrant.run).to be_a RunResult
      expect(@entrant.run.event.order).to eql Race::DEFAULT_EVENTS["run"][:order]
      expect(@entrant.run.event.name).to eql Race::DEFAULT_EVENTS["run"][:name]
      expect(@entrant.run.event.distance).to eql Race::DEFAULT_EVENTS["run"][:distance].to_f
      expect(@entrant.run.event.units).to eql Race::DEFAULT_EVENTS["run"][:units]      
    end

    it "Entant has methods to flatten access to result secs and update total secs" do
      # Use entrant from previous test and assign swim_secs, t1_secs, etc (TEST)
      @entrant.swim_secs=1000
      @entrant.t1_secs=100
      @entrant.bike_secs=3000
      @entrant.t2_secs=100
      @entrant.run_secs=9000
      expect(s_array = @entrant.results.to_a.map{|r| r.secs}).to_not be_nil
      expect((s_array.reduce 0, :+)).to eql @entrant.secs
    end

    it "Entrant has methods to facilitate access to specific event properties" do
      @entrant.swim_secs=160.934
      expect(@entrant.swim_pace_100.round(1)).to eql 10.0
      @entrant.bike_secs=9000.0
      expect(@entrant.bike_mph.round(1)).to eql 10.0
      @entrant.run_secs=6000.0
      expect(@entrant.run_mmile.round(2)).to eql 16.09
    end



    
  end
end
