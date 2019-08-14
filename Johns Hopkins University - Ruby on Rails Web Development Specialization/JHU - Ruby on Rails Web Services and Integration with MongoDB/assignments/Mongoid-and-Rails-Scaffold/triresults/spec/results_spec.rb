# results.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Implement Results Collection" do
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

  before :each do
    Entrant.collection.delete_many
  end

  let(:overall_place) { 10 }
  let(:gender_place) { 8 }
  let(:group_place) { 5 }
  let(:bib) { 0 }
  let(:secs) { 100.123 }
  let(:gender) { "M" }
  let(:group) { "masters" }
  let(:overall_placing) {{ :place=>overall_place }}
  let(:gender_placing) {{ :name=>gender, :place=>gender_place }}
  let(:group_placing) {{ :name=>group, :place=>group_place }}

  context "rq01" do
	  it "Entrant class created" do
    	expect(class_exists?("Entrant"))
   	end

    it "Entrant is stored in results collection" do
      expect(Entrant.collection.name).to eql "results"
    end

    it "Entrant class has fields for name, date, location, and timestamps" do
      expect(Entrant).to have_field(:bib).of_type(Integer)
      expect(Entrant.new).to respond_to(:bib)    
      expect(Entrant).to have_field(:secs).of_type(Float)
      expect(Entrant.new).to respond_to(:secs)
      expect(Entrant).to have_field(:o).with_alias(:overall).of_type(Placing)      
      expect(Entrant.new).to respond_to(:overall)
      expect(Entrant.new).to respond_to(:o)      
      expect(Entrant).to have_field(:gender).of_type(Placing)      
      expect(Entrant).to have_field(:group).of_type(Placing)      
      expect(Entrant).to have_field(:created_at)
      expect(Entrant.new).to respond_to(:created_at)
      expect(Entrant).to have_field(:updated_at)      
      expect(Entrant.new).to respond_to(:updated_at)
    end

    it "Entrant class creates documents with all specified fields" do
      expect(e0 = Entrant.new(:bib=>bib, :secs=>secs)).to_not be_nil
      expect(e0.overall = Placing.demongoize(overall_placing)).to_not be_nil
      expect(e0.gender = Placing.demongoize(gender_placing)).to_not be_nil
      expect(e0.group = Placing.demongoize(group_placing)).to_not be_nil
      expect(e0.save).to be true

      expect(result = Entrant.find(e0.id).attributes).to_not be_nil
      expect(result["_id"]).to eql e0.id
      expect(result["bib"]).to eql bib
      expect(result["secs"]).to eql secs
      expect(result["created_at"]).to_not be_nil      
      expect(result["updated_at"]).to_not be_nil
      expect(result["o"]["place"]).to eql overall_place
      expect(result["gender"]["place"]).to eql gender_place
      expect(result["gender"]["name"]).to eql gender
      expect(result["group"]["place"]).to eql group_place
      expect(result["group"]["name"]).to eql group

      expect(Entrant.find(e0.id).group.name).to eql group
      expect(Entrant.find(e0.id).group.place).to eql group_place
      expect(Entrant.find(e0.id).gender.name).to eql gender
      expect(Entrant.find(e0.id).gender.place).to eql gender_place
    end
  end

  context "rq02" do
    it "LegResult class created" do
      expect(class_exists?("LegResult"))
    end

    it "LegResult class has a secs field" do
      expect(LegResult).to have_field(:secs).of_type(Float)
      expect(LegResult.new).to respond_to(:secs)   
    end

    it "LegResult has an empty calc_ave callback method to be used by subclasses" do
      expect(LegResult.new).to respond_to(:calc_ave)
      expect((LegResult.new.method(:calc_ave).parameters.flatten - [:req, :opt]).count).to eq 0
    end

    it "LegResult overrides secs= intance method" do
      expect(LegResult.new).to respond_to(:secs=)
      expect((LegResult.new.method(:secs=).parameters.flatten - [:req, :opt]).count).to eq 1
      expect(LegResult.new.method(:secs=).parameters.flatten).to include(:req)
    end

    it "LegResult returns a document with an id and secs" do
      expect(leg = LegResult.new).to_not be_nil
      expect(leg.id).to_not be_nil
      expect(leg.id).to be_a BSON::ObjectId
      expect(leg = LegResult.new(:secs=>60)).to_not be_nil
      expect(leg.id).to_not be_nil
      expect(leg.secs).to_not be_nil
      expect(leg.secs.round(1)).to eql 60.0
      leg.secs=120.0
      expect(leg.secs).to_not be_nil
      expect(leg.secs.round(1)).to eql 120.0
    end
  end

  context "rq03" do
    it "LegResult has an M:1 embedded relationship with Entrant" do
      expect(Entrant).to embed_many(:results).of_type(LegResult)     
      expect(LegResult).to be_embedded_in(:entrant)    
    end 

    it "LegResults that are created with Entrant are embedded within it" do
      expect(entrant = Entrant.new).to_not be_nil
      expect(result0 = entrant.results.build(:secs=>secs)).to_not be_nil
      expect(result1 = entrant.results.build(:secs=>1600)).to_not be_nil
      expect(entrant.results.count).to eql 0
      expect(entrant.results.to_a.count).to eql 2
      expect(entrant.results[0].secs).to eql result0.secs
      expect(entrant.results[1].secs).to eql result1.secs
    end
  end

  context "rq04" do
    it "LegResult has a polymorphic 1:1 embedded relationship with Event" do
      expect(Entrant).to embed_many(:results).of_type(LegResult)     
      expect(LegResult).to embed_one(:event).of_type(Event)    
      expect(LegResult).to validate_presence_of(:event)
    end 

    it "Entrant cannot be saved without presence of an event" do
      setup_data_for_testing
      expect(entrant = Entrant.new).to_not be_nil
      expect(result = entrant.results.build(:secs=>60.13)).to_not be_nil
      expect(entrant.save).to be false
      expect(entrant.errors).to_not be_nil
      expect(entrant.errors.messages).to_not be_nil
      expect(entrant.errors.messages[:results].count).to be > 0
      expect(result.errors.messages[:event].count).to be > 0
    end

    it "With events, Entrant embeds results and associated events" do
      setup_data_for_testing
      expect(entrant = Entrant.new).to_not be_nil
      expect(result = entrant.results.build(:secs=>60.13)).to_not be_nil
      expect(race = Race.find_by(:name=>Test_utils::RACE_FIELDS[:name])).to_not be_nil
      expect(event = race.events.where(:name=>"t1").first).to_not be_nil
      expect(result.build_event(event.attributes)).to_not be_nil
      expect(entrant.validate).to be true
      expect(entrant.save).to be true

      expect(event = race.events.where(:name=>"t2").first).to_not be_nil
      expect(entrant.results.create(:event=>event, :secs=>45)).to_not be_nil

      # Test to ensure that resulting Entant document has embedded results 
      # where results each have an embedded event      
      expect(entrant_doc = Entrant.find(entrant.id).attributes).to_not be_nil
      expect((result_doc = entrant_doc["results"]).count).to eql 2
      order_val = -100
      event_name_array = Array.new
      result_doc.each { |r| 
        expect(r["event"]).to_not be_nil
        expect(r["event"]["o"]).to be >= order_val
        order_val = r["event"]["o"]
        event_name_array.push(r["event"]["n"])
      }
      expect(event_name_array).to include("t1", "t2")    
    end    
  end

  context "rq05" do
    it "SwimResult class created" do
      expect(class_exists?("SwimResult"))
    end

    it "SwimResult class has a pace_100 field" do
      expect(SwimResult).to have_field(:pace_100).of_type(Float)
      expect(SwimResult.new).to respond_to(:pace_100)   
    end

    it "SwimResult has instance method calc_ave that takes no parameters and returns a float" do
      expect(SwimResult.new).to respond_to(:calc_ave)
      expect(SwimResult.new.method(:calc_ave).parameters.count).to eq 0
      expect(sr = SwimResult.new(event:Event.new(d:100, u:"meters"), secs:10.0)).to_not be_nil
      expect(sr.calc_ave).to_not be_nil
      expect(sr.calc_ave).to be_a Float
    end

    it "BikeResult class created" do
      expect(class_exists?("BikeResult"))
    end

    it "BikeResult class has a mph field" do
      expect(BikeResult).to have_field(:mph).of_type(Float)
      expect(BikeResult.new).to respond_to(:mph)   
    end

    it "BikeResult has instance method calc_ave that takes no parameters and returns a float" do
      expect(BikeResult.new).to respond_to(:calc_ave)
      expect(BikeResult.new.method(:calc_ave).parameters.count).to eq 0
      expect(br = BikeResult.new(event:Event.new(d:10, u:"miles"), secs:3600.0)).to_not be_nil
      expect(br.calc_ave).to_not be_nil
      expect(br.calc_ave).to be_a Float
    end  

    it "RunResult class created" do
      expect(class_exists?("RunResult"))
    end

    it "RunResult class has a minute_mile field" do
      expect(RunResult).to have_field(:mmile).with_alias(:minute_mile).of_type(Float)
      expect(RunResult.new).to respond_to(:mmile)  
      expect(RunResult.new).to respond_to(:minute_mile)  
    end

    it "RunResult has instance method calc_ave that takes no parameters and returns a float" do
      expect(RunResult.new).to respond_to(:calc_ave)
      expect(RunResult.new.method(:calc_ave).parameters.count).to eq 0
      expect(br = RunResult.new(event:Event.new(d:10, u:"miles"), secs:3600.0)).to_not be_nil
      expect(br.calc_ave).to_not be_nil
      expect(br.calc_ave).to be_a Float
    end 
  end

  context "rq06" do
    it "SwimResult calc_ave method calculates the secs to travel 100 m based on event" do
      expect(s = SwimResult.new(:event=>Event.new(distance:100, units:"meters"), :secs=>10)).to_not be_nil
      expect(s.calc_ave).to_not be_nil
      expect(s.calc_ave.round(1)).to eql 10.0
      expect(s = SwimResult.new(:event=>Event.new(distance:1, units:"miles"), :secs=>160.934)).to_not be_nil
      expect(s.calc_ave.round(1)).to eql 10.0
      expect(s = SwimResult.new(:event=>Event.new(distance:1, units:"miles"), :secs=>160.934)).to_not be_nil
      expect(s.pace_100.round(1)).to eql 10.0
    end 

    it "BikeResult calc_ave method calculates the average mph based on event" do
      expect(b = BikeResult.new(:event=>Event.new(distance:10, units:"miles"), :secs=>3600)).to_not be_nil
      expect(b.calc_ave).to_not be_nil
      expect(b.calc_ave.round(1)).to eql 10.0
      expect(b = BikeResult.new(:event=>Event.new(distance:100, units:"kilometers"), :secs=>3600)).to_not be_nil
      expect(b.calc_ave.round(2)).to eql 62.14
      expect(b = BikeResult.new(:event=>Event.new(distance:100, units:"kilometers"), :secs=>3600)).to_not be_nil
      expect(b.mph.round(2)).to eql 62.14
    end 

    it "RunResult calc_ave method calculates average time to run a mile based on event" do
      expect(r = RunResult.new(:event=>Event.new(distance:1, units:"miles"), :secs=>240)).to_not be_nil
      expect(r.calc_ave).to_not be_nil
      expect(r.calc_ave.round(1)).to eql 4.0
      expect(r = RunResult.new(:event=>Event.new(distance:10, units:"kilometers"), :secs=>6000)).to_not be_nil
      expect(r.calc_ave.round(2)).to eql 16.09
      expect(r = RunResult.new(:event=>Event.new(distance:10, units:"kilometers"), :secs=>6000)).to_not be_nil
      expect(r.mmile.round(2)).to eql 16.09
    end

    it "secs= method updates field values of the result classes" do
      expect(s = SwimResult.new(event:Event.new(d:100, u:"meters"))).to_not be_nil
      s.secs=1000.0
      expect(s.pace_100).to_not be_nil
      expect(s.pace_100.round(1)).to eq(1000.0)
      expect(b = BikeResult.new(event:Event.new(d:10, u:"miles"))).to_not be_nil
      b.secs=3600.0
      expect(b.mph).to_not be_nil
      expect(b.mph.round(1)).to eq(10.0)
      expect(r = RunResult.new(event:Event.new(d:5, u:"miles"))).to_not be_nil
      r.secs=1500.0
      expect(r.minute_mile).to_not be_nil
      expect(r.minute_mile.round(1)).to eq(5.0)
    end
  end

  context "rq07" do 
    it "Entrant has update_total instance method for callback that takes one parameter" do
      expect(Entrant.new).to respond_to(:update_total)
      expect((Entrant.new.method(:update_total).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Entrant.new.method(:update_total).parameters.flatten).to include(:req)
    end      

    it "update_total callback updates the Entrants secs field and updated_at with event additions and deletions" do
      expect(entrant = Entrant.create).to_not be_nil
      expect(entrant.secs).to be_nil
      update_time = entrant.updated_at
      total_secs = 0.0
      sec_val = 3600.0
      entrant.results << SwimResult.new(:event=>Event.new(o:0,n:"swim",distance:1,units:"miles"), :secs=>sec_val)
      expect(total_secs = total_secs + sec_val).to eql entrant.secs
      expect(entrant.updated_at).to be >= update_time
      update_time = entrant.updated_at
      entrant.results << BikeResult.new(:event=>Event.new(o:2,n:"bike",distance:100,units:"kilometers"), :secs=>sec_val)
      expect(total_secs = total_secs + sec_val).to eql entrant.secs
      expect(entrant.updated_at).to be >= update_time
      update_time = entrant.updated_at
      sec_val = 6000.0
      entrant.results << RunResult.new(:event=>Event.new(o:4,n:"run",distance:10,units:"kilometers"), :secs=>sec_val)
      expect(total_secs = total_secs + sec_val).to eql entrant.secs
      expect(entrant.updated_at).to be >= update_time
    end
  end
end
