# racers.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Implement Racers Collection" do
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

  let(:lat) { -93.5 }
  let(:lon) { 45.5 }
  let(:city) { "Oakland" }
  let(:state) { "CA" }
  let(:firstname) { "cat" }
  let(:lastname) { "inhat" }
  let(:birthyear) { 1990 }
  let(:gender) { "M" }
  let(:point) {{ :type=>"Point", :coordinates=>[lon, lat] }}
  let(:residence) {{ :city=>city, :state=>state, :loc=>point }}

  context "rq01" do
	  it "RacerInfo class created" do
    	expect(class_exists?("RacerInfo"))
   	end

    it "RacerInfo class has fields for first_name, last_name, gender, birth_year, and residence" do
      expect(RacerInfo.new).to respond_to(:_id)
      expect(RacerInfo.new).to respond_to(:racer_id)
      expect(RacerInfo.new).to respond_to(:first_name)
      expect(RacerInfo.new).to respond_to(:last_name)
      expect(RacerInfo.new).to respond_to(:gender)
      expect(RacerInfo.new).to respond_to(:birth_year)
      expect(RacerInfo.new).to respond_to(:residence)
    end

    it "Racer class has field mappings fn, ln, g, yr, and res" do
      expect(RacerInfo.new).to respond_to(:fn)
      expect(RacerInfo.new).to respond_to(:ln)
      expect(RacerInfo.new).to respond_to(:g)
      expect(RacerInfo.new).to respond_to(:yr)
      expect(RacerInfo.new).to respond_to(:res)
    end

    it "Racer class values are accessible through fields and mappings" do
      r = RacerInfo.new(id:"000", fn:firstname, ln:lastname, g:gender, yr:birthyear, res:residence)
      expect("000").to eql(r._id).or eql(r.racer_id)
      expect(r.first_name).to eql firstname
      expect(r.fn).to eql firstname
      expect(r.last_name).to eql lastname
      expect(r.ln).to eql lastname
      expect(r.birth_year).to eql birthyear
      expect(r.yr).to eql birthyear 
      expect(r.gender).to eql gender
      expect(r.g).to eql gender 
      expect(r.residence.city).to eql city
      expect(r.res.city).to eql city
      expect(r.residence.state).to eql state
      expect(r.res.state).to eql state
      expect(r.residence.location.longitude).to eql lon
      expect(r.res.location.longitude).to eql lon
      expect(r.residence.location.latitude).to eql lat
      expect(r.res.location.latitude).to eql lat
    end
  end

  context "rq02" do  
    it "Racer class created" do
      expect(class_exists?("Racer"))
    end

    it "Racer class has an id field" do
      expect(Racer.new.attributes["_id"]).to_not be_nil
    end
  end

  context "rq03" do      
    it "Racer and RacerInfo have a 1:1 embdedded relationship" do
      expect(Racer).to embed_one(:info).of_type(RacerInfo).with_autobuild 
      expect(RacerInfo).to be_embedded_in(:parent)
    end

    it "Creating Racer also creates embedded RacerInfo with id assigned from Racer" do
      expect(r = Racer.create).to_not be_nil
      expect(result = Racer.collection.find(:_id=>r.id).first).to_not be_nil
      expect(result["info"]).to_not be_nil
      expect(result["info"]["racer_id"]).to eql result["_id"]
    end

    it "Building compound document Racer results in embedded document with RacerInfo id assigned" do
      expect(address = Address.demongoize(residence)).to_not be_nil
      expect(racer = Racer.new).to_not be_nil
      expect(racer.build_info(fn:firstname, ln:lastname, g:gender, yr:birthyear, res:address)).to_not be_nil
      expect(racer.save).to be true
      expect(result = Racer.collection.find(:_id=>racer.id).first).to_not be_nil
      expect(result["info"]).to_not be_nil
      expect(result["info"]["fn"]).to eql firstname
      expect(result["info"]["ln"]).to eql lastname
      expect(result["info"]["g"]).to eql gender
      expect(result["info"]["yr"]).to eql birthyear
      expect(result["info"]["res"]["city"]).to eql city
      expect(result["info"]["res"]["state"]).to eql state
      expect(result["info"]["res"]["loc"]["type"]).to eql "Point"
      expect(result["info"]["res"]["loc"]["coordinates"][0]).to eql lon
      expect(result["info"]["res"]["loc"]["coordinates"][1]).to eql lat
      expect(result["info"]["racer_id"]).to eql result["_id"]
    end
  end

  context "rq04" do
    it "RacerInfo implements validations on fields" do
      expect(RacerInfo).to validate_presence_of(:first_name)
      expect(RacerInfo).to validate_presence_of(:last_name)
      expect(RacerInfo).to validate_presence_of(:gender)
      expect(RacerInfo).to validate_presence_of(:birth_year)
      expect(RacerInfo).to validate_inclusion_of(:gender).to_allow("M", "F")
      expect(RacerInfo).to validate_numericality_of(:birth_year).less_than(Date.current.year)
    end

    it "RacerInfo validations are correctly validating or generating errors" do
      expect(i0 = RacerInfo.new).to_not be_nil
      expect(i0.validate).to be false
      expect(i0.errors).to_not be_nil
      expect(m0 = i0.errors.messages).to_not be_nil
      expect(m0[:first_name].count).to eql 1
      expect(m0[:last_name].count).to eql 1
      expect(m0[:gender].count).to eql 2
      expect(m0[:birth_year].count).to eql 2

      expect(i1 = RacerInfo.new(g:"X", yr:2100)).to_not be_nil
      expect(i1.validate).to be false
      expect(i1.errors).to_not be_nil
      expect(m1 = i1.errors.messages).to_not be_nil
      expect(m1[:first_name].count).to eql 1
      expect(m1[:last_name].count).to eql 1
      expect(m1[:gender].count).to eql 1
      expect(m1[:birth_year].count).to eql 1  

      expect(r = Racer.new).to_not be_nil
      expect(r.validate).to be true
      expect(i = r.info).to_not be_nil
      expect(r.validate).to be false
      expect(r.errors).to_not be_nil
      expect(mr = r.errors.messages).to_not be_nil
      expect(mr[:info].count).to eql 1

      expect(good = RacerInfo.new(fn:firstname, ln:lastname, g:gender, yr:birthyear)).to_not be_nil
      expect(good.validate).to be true
      expect(good.errors.messages.count).to be 0
    end
  end
end