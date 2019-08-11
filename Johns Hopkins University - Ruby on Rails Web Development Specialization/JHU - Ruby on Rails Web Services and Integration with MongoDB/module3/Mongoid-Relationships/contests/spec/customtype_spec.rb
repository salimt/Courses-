# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Custom Type Tests" do
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

  let(:lat0) { -93.5 }
  let(:lon0) { 45.5 }

  context "rq01" do

    it "must have top level structure of a rails application" do
      expect(File.exists?("Gemfile")).to be(true)
      expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
      expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
    end

	  it "Point class created" do
    	expect(class_exists?("Point"))
   	end
    it "Point class has accessors for latitude and longitude" do
      expect(p = Point.new(lon0, lat0)).to_not be_nil
      expect(p).to respond_to(:latitude)
      expect(p).to respond_to(:longitude)

      expect(p.latitude).to eql lat0
      expect(p.longitude).to eql lon0
    end
  end

  context "rq02" do
      let (:point) { Point.new(lon0, lat0) }

      it "Point Model has an instance method called mongoize" do
        expect(point).to respond_to(:mongoize)
      end
      
      it "mongoize method takes no parameters" do 
        expect((point.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "mongoize method returns a hash with specified types and values" do
        expect(result = point.mongoize).to be_a Hash
        expect(result[:type]).to_not be_nil
        expect(result[:coordinates]).to_not be_nil
        expect(result[:coordinates][0]).to eql lon0
        expect(result[:coordinates][1]).to eql lat0
      end
  end

  context "rq03" do
      let(:hash) {{:type=>"Point", :coordinates=>[lon0, lat0]}}
      it "Point Model has an class method called demongoize" do
        expect(Point).to respond_to(:demongoize)
      end
      
      it "demongoize method takes a hash parameter" do 
        expect((Point.method(:demongoize).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Point.method(:demongoize).parameters.flatten).to include :req
        expect(Point.method(:demongoize).parameters.flatten).to_not include :opt
      end

      it "demongoize method returns a Point with specified latitude and longitude in hash" do
        expect(p = Point.demongoize(hash)).to_not be_nil
        expect(p).to be_a Point
        expect(p.latitude).to eql lat0
        expect(p.longitude).to eql lon0
      end
  end

  context "rq04" do
      let(:phash) {{:type=>"Point", :coordinates=>[lon0, lat0]}}
      it "Point Model has an class method called mongoize" do
        expect(Point).to respond_to(:mongoize)
      end
      
      it "mongoize method takes an object parameter that can be hash, Point or parameter hash" do 
        expect((Point.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Point.method(:mongoize).parameters.flatten).to include :req
        expect(Point.method(:mongoize).parameters.flatten).to_not include :opt
      end

      it "mongoize method returns a Point with specified latitude and longitude in hash" do
        expect(p = Point.mongoize(phash)).to_not be_nil
        expect(p).to be_a Hash
        expect(p[:coordinates][0]).to eql lon0
        expect(p[:coordinates][1]).to eql lat0
        point = Point.new(lon0, lat0)
        expect(p1 = Point.mongoize(point)).to_not be_nil        
        expect(p1).to be_a Hash
        expect(p1[:coordinates][0]).to eql lon0
        expect(p1[:coordinates][1]).to eql lat0
      end
  end

  context "rq05" do
      let(:phash) {{:type=>"Point", :coordinates=>[lon0, lat0]}}
      it "Point Model has an class method called evolve" do
        expect(Point).to respond_to(:evolve)
      end
      
      it "mongoize method takes an object parameter that can be hash, Point or parameter hash" do 
        expect((Point.method(:evolve).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Point.method(:evolve).parameters.flatten).to include :req
        expect(Point.method(:evolve).parameters.flatten).to_not include :opt
      end

      it "evolve method returns a Point with specified latitude and longitude in hash" do
        expect(p = Point.evolve(phash)).to_not be_nil
        expect(p).to be_a Hash
        expect(p[:coordinates][0]).to eql lon0
        expect(p[:coordinates][1]).to eql lat0
        point = Point.new(lon0, lat0)
        expect(p1 = Point.evolve(point)).to_not be_nil        
        expect(p1).to be_a Hash
        expect(p1[:coordinates][0]).to eql lon0
        expect(p1[:coordinates][1]).to eql lat0
      end
  end  

  context "rq06" do

    before :all do
      Address.collection.find.delete_many
    end

    it "Address class created" do
      expect(class_exists?("Address"))
    end
    it "Address class has the specified fields" do
      expect(address = Address.new(:geolocation=>Point.new(lon0, lat0))).to_not be_nil
      expect(address).to be_a Address
      expect(address.geolocation).to be_a Point
      expect(id = address.id).to_not be_nil
      expect(address).to respond_to(:street)
      expect(address).to respond_to(:city)
      expect(address).to respond_to(:state)
      expect(address).to respond_to(:country)
      expect(address).to respond_to(:geolocation)
      # could not store address as object and have test work
      # after it becomes and embedded object, requires it be 
      # embedded in a parent which is not the case when this 
      # test case is initially run.  As such, the DB tests are
      # commented out
      # db_addr = Address.collection.find.first
      # expect(db_addr[:_id]).to eql address.id
      # expect(db_addr[:geolocation][:type]).to eql "Point"
      # expect(db_addr[:geolocation][:coordinates][0]).to eql address.geolocation.longitude
      # expect(db_addr[:geolocation][:coordinates][1]).to eql address.geolocation.latitude
    end
  end

end