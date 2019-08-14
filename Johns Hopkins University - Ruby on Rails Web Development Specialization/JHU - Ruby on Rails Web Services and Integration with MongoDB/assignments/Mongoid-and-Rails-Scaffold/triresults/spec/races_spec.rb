# races.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Implement Races Collection" do
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
    Race.collection.delete_many
  end

  let(:lat) { -93.5 }
  let(:lon) { 45.5 }
  let(:name) { "Oakland 10K" }
  let(:city) { "Oakland" }
  let(:state) { "CA" }  
  let!(:date) { Date.current }
  let(:point) {{ :type=>"Point", :coordinates=>[lon, lat] }}
  let(:address) {{ :city=>city, :state=>state, :loc=>point }}

  context "rq01" do
	  it "Race class created" do
    	expect(class_exists?("Race"))
   	end

    it "Race class has fields for name, date, location, and timestamps" do
      expect(Race).to have_field(:n).with_alias(:name).of_type(String)
      expect(Race.new).to respond_to(:name)
      expect(Race.new).to respond_to(:n)      
      expect(Race).to have_field(:date).of_type(Date)
      expect(Race.new).to respond_to(:date)
      expect(Race).to have_field(:loc).with_alias(:location).of_type(Address)      
      expect(Race.new).to respond_to(:location)
      expect(Race.new).to respond_to(:loc)      
      expect(Race).to have_field(:created_at)
      expect(Race.new).to respond_to(:created_at)
      expect(Race).to have_field(:updated_at)      
      expect(Race.new).to respond_to(:updated_at)
    end

    it "Race class creates documents with all specified fields" do
      expect(r0 = Race.new(name:name, date:date, location:address)).to_not be_nil
      expect(r0.save).to be true
      expect(result0 = Race.find(r0.id).attributes).to_not be_nil
      expect(result0["_id"]).to_not be_nil
      expect(result0["n"]).to eql name
      expect(Date.parse(result0["date"].to_s)).to eql date
      expect(result0["loc"]["city"]).to eql city
      expect(result0["loc"]["state"]).to eql state
      expect(result0["loc"]["loc"]["type"]).to eql "Point"
      expect(result0["loc"]["loc"]["coordinates"][0]).to eql lon
      expect(result0["loc"]["loc"]["coordinates"][1]).to eql lat
      expect(result0["updated_at"]).to_not be_nil
      expect(result0["created_at"]).to_not be_nil

      expect(r1 = Race.create(n:name, date:date, loc:address)).to_not be_nil
      expect(result1 = Race.find(r1.id).attributes).to_not be_nil
      expect(result1["_id"]).to_not be_nil
      expect(result1["n"]).to eql name
      expect(Date.parse(result1["date"].to_s)).to eql date
      expect(result1["loc"]["city"]).to eql city
      expect(result1["loc"]["state"]).to eql state
      expect(result1["loc"]["loc"]["type"]).to eql "Point"
      expect(result1["loc"]["loc"]["coordinates"][0]).to eql lon
      expect(result1["loc"]["loc"]["coordinates"][1]).to eql lat
      expect(result1["updated_at"]).to_not be_nil
      expect(result1["created_at"]).to_not be_nil
    end
  end

  context "rq02" do
    before :each do
      Event.collection.delete_many
    end

    let(:order) { 5 }
    let(:units) { "kilometers" }
    let(:name) { "Oakland 10K" }
    let(:distance) { 25.0 }

    it "Event class created" do
      expect(class_exists?("Event"))
    end

    it "Event class has fields for order, name, distance, and units" do
      expect(Event).to have_field(:n).with_alias(:name).of_type(String)
      expect(Event.new).to respond_to(:name)
      expect(Event.new).to respond_to(:n)      
      expect(Event).to have_field(:o).with_alias(:order).of_type(Integer)
      expect(Event.new).to respond_to(:order)
      expect(Event.new).to respond_to(:o)
      expect(Event).to have_field(:d).with_alias(:distance).of_type(Float)      
      expect(Event.new).to respond_to(:distance)
      expect(Event.new).to respond_to(:d)  
      expect(Event).to have_field(:u).with_alias(:units).of_type(String)      
      expect(Event.new).to respond_to(:units)
      expect(Event.new).to respond_to(:u) 
    end

    it "Event class values are accessible through fields and mappings" do
      expect(e0 = Event.new(n:name, o:order, u:units, d:distance)).to_not be_nil
      expect(e0.name).to eql name
      expect(e0.n).to eql name
      expect(e0.order).to eql order
      expect(e0.o).to eql order
      expect(e0.units).to eql units
      expect(e0.u).to eql units
      expect(e0.distance).to eql distance
      expect(e0.d).to eql distance

      expect(e1 = Event.new(name:name, order:order, units:units, distance:distance)).to_not be_nil
      expect(e1.name).to eql name
      expect(e1.n).to eql name
      expect(e1.order).to eql order
      expect(e1.o).to eql order
      expect(e1.units).to eql units
      expect(e1.u).to eql units
      expect(e1.distance).to eql distance
      expect(e1.d).to eql distance
    end

    it "Event has an instance method called meters that takes no parameters" do
      expect(Event.new).to respond_to(:meters)
      expect((Event.new.method(:meters).parameters.flatten - [:opt, :req]).count).to eq 0
      expect(Event.new.method(:meters).parameters.flatten).to_not include :req
      expect(Event.new.method(:meters).parameters.flatten).to_not include :opt
    end

    it "meters method converts miles, kilometers, meters and yards to meters or returns nil" do
      expect(Event.new(u:"miles", d:1).meters.round(2)).to eql 1609.34
      expect(Event.new(u:"kilometers", d:1).meters.round(1)).to eql 1000.0
      expect(Event.new(u:"meters", d:12).meters.round(1)).to eql 12.0
      expect(Event.new(u:"yards", d:10000).meters.round(1)).to eql 9144.0
      expect(Event.new(u:"inches", d:144).meters).to be_nil
      expect(Event.new(d:100).meters).to be_nil
    end

    it "Event has an instance method called miles that takes no parameters" do
      expect(Event.new).to respond_to(:miles)
      expect((Event.new.method(:miles).parameters.flatten - [:opt, :req]).count).to eq 0
      expect(Event.new.method(:miles).parameters.flatten).to_not include :req
      expect(Event.new.method(:miles).parameters.flatten).to_not include :opt
    end

    it "miles method converts miles, kilometers, meters and yards to meters or returns nil" do
      expect(Event.new(u:"miles", d:111).miles.round(1)).to eql 111.0
      expect(Event.new(u:"kilometers", d:100000).miles.round(1)).to eql 62137.1
      expect(Event.new(u:"meters", d:100000).miles.round(2)).to eql 62.14
      expect(Event.new(u:"yards", d:10000).miles.round(2)).to eql 5.68
      expect(Event.new(u:"inches", d:144).miles).to be_nil
      expect(Event.new(d:100).miles).to be_nil
    end
  end

  context "rq03" do  
    it "Event has an M:1 embedded relationship with Race" do
      expect(Race).to embed_many(:events).of_type(Event)     
      expect(Event).to be_embedded_in(:parent)    
    end  

    it "Events are embedded within Race document and sorted by order" do
      expect(Race.create(n:name, date:date, loc:address)).to_not be_nil
      expect(race = Race.find_by(n:name)).to_not be_nil
      expect(race.events.build(o:4, n:"run", d:10, u:"kilometers")).to_not be_nil      
      expect(race.events.build(o:0, n:"swim", d:1, u:"miles")).to_not be_nil
      expect(race.events.build(o:2, n:"bike", d:25, u:"miles")).to_not be_nil
      expect(race.events.build(o:1, n:"t1")).to_not be_nil
      expect(race.events.build(o:3, n:"t2")).to_not be_nil
      expect(race.save).to eql true

      expect(result = Race.find(race.id)).to_not be_nil
      expect(events = result.events).to be_a Array
      expect(events.count).to eql 5
      local_o = -1
      events.each { |e| 
        expect(e["o"]).to be >= local_o
        local_o = e["o"]
        case e["n"]
        when "swim"
          expect(e["d"]).to eql 1.0
          expect(e["u"]).to eql "miles"
        when "t1", "t2"
          expect(e["d"]).to be_nil
          expect(e["u"]).to be_nil
        when "bike"
          expect(e["d"]).to eql 25.0
          expect(e["u"]).to eql "miles"          
        when "run"
          expect(e["d"]).to eql 10.0
          expect(e["u"]).to eql "kilometers"
        end    
      } 

      expect(race.events.first.parent.name).to eql name
    end
  end

  context "rq04" do
    it "Event validates the presence of order and name" do
      expect(Event).to validate_presence_of(:order)
      expect(Event).to validate_presence_of(:name)
    end

    it "Event validations are correctly validating or generating errors" do
      expect(e0 = Event.new).to_not be_nil
      expect(e0.validate).to be false
      expect(e0.errors).to_not be_nil
      expect(m0 = e0.errors.messages).to_not be_nil
      expect(m0[:order].count).to eql 1
      expect(m0[:name].count).to eql 1

      expect(r = Race.new).to_not be_nil
      expect(e1 = r.events.build).to_not be_nil
      expect(r.validate).to be false
      expect(r.errors).to_not be_nil
      expect(m1 = r.errors.messages).to_not be_nil   
      expect(m1[:events].count).to eql 1   
      expect(r.save).to be false

      # Mongo won't change valid Race by added invalid empty Event
      expect(r2 = Race.create).to_not be_nil
      expect(e2 = r2.events.create).to_not be_nil
      expect(res2 = Race.where(:id=>r2.id).first.attributes).to_not be_nil
      expect(res2["events"]).to be_nil

      # Mongo will change Race if adding valid Event
      expect(r3 = Race.create).to_not be_nil
      expect(e3 = r3.events.create(o:0, n:"foo")).to_not be_nil
      expect(res3 = Race.where(:id=>r3.id).first.attributes).to_not be_nil
      expect(res3["events"][0]["_id"]).to eql e3.id
      expect(res3["events"][0]["o"]).to eql 0
      expect(res3["events"][0]["n"]).to eql "foo"
    end
  end

  context "rq05" do 
    it "Race contains scopes for contraining results to future or past" do
      expect(Race.scopes[:upcoming]).to_not be_nil
      expect(Race.upcoming).to be_a Mongoid::Criteria
      expect(Race.scopes[:past]).to_not be_nil
      expect(Race.past).to be_a Mongoid::Criteria
    end

    it "Race scopes constrain results to those from past or from today on" do
      expect(Race.create(:name=>"Yesterday's Challenge", :date=>Date.yesterday)).to_not be_nil
      expect(Race.create(:name=>"Today's Challenge", :date=>Date.current)).to_not be_nil
      expect(Race.create(:name=>"Tomorrow's Challenge", :date=>Date.tomorrow)).to_not be_nil
      expect(res = Race.upcoming.where(:name=>{:$regex=>"Challenge"}).pluck(:name, :date)).to_not be_nil
      expect(res.count).to eql 2
      expect(res.flatten).to include("Today's Challenge", "Tomorrow's Challenge")
      expect(res = Race.past.where(:name=>{:$regex=>"Challenge"}).pluck(:name, :date)).to_not be_nil
      expect(res.count).to eql 1
      expect(res.flatten).to include("Yesterday's Challenge")      
    end
  end
end
