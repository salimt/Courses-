# custome_types.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Custom Type Tests" do
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
  let(:city) { "Oakland" }
  let(:state) { "CA" }
  let(:name) { "masters" }
  let(:place) { 3 }
  let(:hash) {{ :type=>"Point", :coordinates=>[lon0, lat0] }}
  let(:address_hash) {{ :city=>city, :state=>state, :loc=>hash }}
  let(:place_hash) {{ :name=>name, :place=>place }}

  context "rq01" do 
    it "Point class created" do
      expect(class_exists?("Point"))
 	  end

    it "Point Model has an class method called demongoize" do
      expect(Point).to respond_to(:demongoize)
    end
      
    it "demongoize method takes a hash parameter" do 
      expect((Point.method(:demongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Point.method(:demongoize).parameters.flatten).to include :req
      expect(Point.method(:demongoize).parameters.flatten).to_not include :opt
    end

    it "point instance has accessors for latitude and longitude" do
      expect(p = Point.demongoize(hash)).to_not be_nil
      expect(p).to be_a Point
      expect(p).to respond_to(:latitude)
      expect(p).to respond_to(:longitude)
    end

    it "demongoize method returns a Point with specified latitude and longitude in hash" do
      expect(p = Point.demongoize(hash)).to_not be_nil
      expect(p).to be_a Point
      expect(p.latitude).to eql lat0
      expect(p.longitude).to eql lon0
    end

    it "demongoize method handles nil arguments gracefully" do
      expect(Point.demongoize(nil)).to be_nil
    end
  
    it "Point Model has an instance method called mongoize" do
      expect(p = Point.demongoize(hash)).to_not be_nil      
      expect(p).to respond_to(:mongoize)
    end
      
    it "mongoize method takes no parameters" do 
      expect(p = Point.demongoize(hash)).to_not be_nil      
      expect((p.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 0
    end

    it "mongoize method returns a hash with specified types and values" do
      expect(point = Point.demongoize(hash)).to_not be_nil
      expect(result = point.mongoize).to be_a Hash
      expect(result[:type]).to_not be_nil
      expect(result[:coordinates]).to_not be_nil
      expect(result[:coordinates][0]).to eql lon0
      expect(result[:coordinates][1]).to eql lat0
    end
    
    it "Point Model has an class method called mongoize" do
      expect(Point).to respond_to(:mongoize)
    end
      
    it "mongoize class method takes an object parameter that can be hash, Point or parameter hash" do 
      expect((Point.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Point.method(:mongoize).parameters.flatten).to include :req
      expect(Point.method(:mongoize).parameters.flatten).to_not include :opt
    end

    it "mongoize class method returns a Point with specified latitude and longitude in hash" do
      expect(p = Point.mongoize(hash)).to_not be_nil
      expect(p).to be_a Hash
      expect(p[:coordinates][0]).to eql lon0
      expect(p[:coordinates][1]).to eql lat0
      point = Point.demongoize(hash)
      expect(p1 = Point.mongoize(point)).to_not be_nil        
      expect(p1).to be_a Hash
      expect(p1[:coordinates][0]).to eql lon0
      expect(p1[:coordinates][1]).to eql lat0
    end

    it "mongoize class method handles nil arguments gracefully" do
      expect(Point.mongoize(nil)).to be_nil
    end

    it "Point Model has an class method called evolve" do
      expect(Point).to respond_to(:evolve)
    end
      
    it "evolve method takes an object parameter that can be hash, Point or parameter hash" do 
      expect((Point.method(:evolve).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Point.method(:evolve).parameters.flatten).to include :req
      expect(Point.method(:evolve).parameters.flatten).to_not include :opt
    end

    it "evolve method returns a Point with specified latitude and longitude in hash" do
      expect(p = Point.evolve(hash)).to_not be_nil
      expect(p).to be_a Hash
      expect(p[:coordinates][0]).to eql lon0
      expect(p[:coordinates][1]).to eql lat0
      point = Point.demongoize(hash)
      expect(p1 = Point.evolve(point)).to_not be_nil        
      expect(p1).to be_a Hash
      expect(p1[:coordinates][0]).to eql lon0
      expect(p1[:coordinates][1]).to eql lat0
    end

    it "evolve method handles nil arguments gracefully" do
      expect(Point.evolve(nil)).to be_nil
    end
  end  

  context "rq02" do 
    it "Address class created" do
      expect(class_exists?("Address"))
    end

    it "Address Model has an class method called demongoize" do
      expect(Address).to respond_to(:demongoize)
    end
      
    it "demongoize method takes a hash parameter" do 
      expect((Address.method(:demongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Address.method(:demongoize).parameters.flatten).to include :req
      expect(Address.method(:demongoize).parameters.flatten).to_not include :opt
    end

    it "address instance has accessors for city, state and location" do
      expect(a = Address.demongoize(address_hash)).to_not be_nil
      expect(a).to be_a Address
      expect(a).to respond_to(:city)
      expect(a).to respond_to(:state)
      expect(a).to respond_to(:location)
    end

    it "demongoize method returns an address with specified parameters from hash" do
      expect(a = Address.demongoize(address_hash)).to_not be_nil
      expect(a).to be_a Address
      expect(a.city).to eql city
      expect(a.state).to eql state
      expect(a.location).to be_a Point
      expect(a.location.longitude).to eql lon0
      expect(a.location.latitude).to eql lat0      
    end

    it "demongoize method handles nil arguments gracefully" do
      expect(Address.demongoize(nil)).to be_nil
    end
  
    it "Address Model has an instance method called mongoize" do
      expect(a = Address.demongoize(address_hash)).to_not be_nil
      expect(a).to respond_to(:mongoize)
    end
      
    it "mongoize method takes no parameters" do 
      expect(a = Address.demongoize(address_hash)).to_not be_nil
      expect((a.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 0
    end

    it "mongoize method returns a hash with specified types and values" do
      expect(a = Address.demongoize(address_hash)).to_not be_nil
      expect(result = a.mongoize).to be_a Hash
      expect(result[:city]).to eql city
      expect(result[:state]).to eql state
      expect(result[:loc][:type]).to eql "Point"
      expect(result[:loc][:coordinates][0]).to eql lon0
      expect(result[:loc][:coordinates][1]).to eql lat0
    end
    
    it "Address Model has an class method called mongoize" do
      expect(Address).to respond_to(:mongoize)
    end
      
    it "mongoize class method takes an object parameter that can be hash, Address or parameter hash" do 
      expect((Address.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Address.method(:mongoize).parameters.flatten).to include :req
      expect(Address.method(:mongoize).parameters.flatten).to_not include :opt
    end

    it "mongoize class method returns an Address with specified parameters in hash" do
      expect(a = Address.mongoize(address_hash)).to_not be_nil
      expect(a).to be_a Hash
      expect(a[:city]).to eql city
      expect(a[:state]).to eql state
      expect(a[:loc][:type]).to eql "Point"
      expect(a[:loc][:coordinates][0]).to eql lon0
      expect(a[:loc][:coordinates][1]).to eql lat0
      address = Address.demongoize(address_hash)
      expect(a1 = Address.mongoize(address)).to_not be_nil        
      expect(a1).to be_a Hash
      expect(a1[:city]).to eql city
      expect(a1[:state]).to eql state
      expect(a1[:loc][:type]).to eql "Point"
      expect(a1[:loc][:coordinates][0]).to eql lon0
      expect(a1[:loc][:coordinates][1]).to eql lat0
    end

    it "mongoize class method handles nil arguments gracefully" do
      expect(Address.mongoize(nil)).to be_nil
    end

    it "Address Model has an class method called evolve" do
      expect(Address).to respond_to(:evolve)
    end
      
    it "evolve method takes an object parameter that can be hash, Address or parameter hash" do 
      expect((Address.method(:evolve).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Address.method(:evolve).parameters.flatten).to include :req
      expect(Address.method(:evolve).parameters.flatten).to_not include :opt
    end

    it "evolve method returns a Address with specified parameters in hash" do
      expect(a = Address.evolve(address_hash)).to_not be_nil
      expect(a).to be_a Hash
      expect(a[:city]).to eql city
      expect(a[:state]).to eql state
      expect(a[:loc][:type]).to eql "Point"
      expect(a[:loc][:coordinates][0]).to eql lon0
      expect(a[:loc][:coordinates][1]).to eql lat0
      address = Address.demongoize(address_hash)
      expect(a1 = Address.evolve(address)).to_not be_nil        
      expect(a1).to be_a Hash
      expect(a1[:city]).to eql city
      expect(a1[:state]).to eql state
      expect(a1[:loc][:type]).to eql "Point"
      expect(a1[:loc][:coordinates][0]).to eql lon0
      expect(a1[:loc][:coordinates][1]).to eql lat0
    end

    it "evolve method handles nil arguments gracefully" do
      expect(Address.evolve(nil)).to be_nil
    end
  end  

  context "rq03" do 
    it "Placing class created" do
      expect(class_exists?("Placing"))
    end

    it "Placing Model has an class method called demongoize" do
      expect(Placing).to respond_to(:demongoize)
    end
      
    it "demongoize method takes a hash parameter" do 
      expect((Placing.method(:demongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Placing.method(:demongoize).parameters.flatten).to include :req
      expect(Placing.method(:demongoize).parameters.flatten).to_not include :opt
    end

    it "placing instance has accessors for name and place" do
      expect(p = Placing.demongoize(place_hash)).to_not be_nil
      expect(p).to be_a Placing
      expect(p).to respond_to(:name)
      expect(p).to respond_to(:place)
    end

    it "demongoize method returns an address with specified parameters from hash" do
      expect(p = Placing.demongoize(place_hash)).to_not be_nil
      expect(p).to be_a Placing
      expect(p.name).to eql name
      expect(p.place).to eql place   
    end

    it "demongoize method handles nil arguments gracefully" do
      expect(Placing.demongoize(nil)).to be_nil
    end
  
    it "Placing Model has an instance method called mongoize" do
      expect(p = Placing.demongoize(place_hash)).to_not be_nil
      expect(p).to respond_to(:mongoize)
    end
      
    it "mongoize method takes no parameters" do 
      expect(p = Placing.demongoize(place_hash)).to_not be_nil
      expect((p.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 0
    end

    it "mongoize method returns a hash with specified types and values" do
      expect(p = Placing.demongoize(place_hash)).to_not be_nil
      expect(result = p.mongoize).to be_a Hash
      expect(result[:name]).to eql name
      expect(result[:place]).to eql place
    end
    
    it "Placing Model has an class method called mongoize" do
      expect(Placing).to respond_to(:mongoize)
    end
      
    it "mongoize class method takes an object parameter that can be hash, Placing or parameter hash" do 
      expect((Placing.method(:mongoize).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Placing.method(:mongoize).parameters.flatten).to include :req
      expect(Placing.method(:mongoize).parameters.flatten).to_not include :opt
    end

    it "mongoize class method returns a Placing with specified parameters in hash" do
      expect(p = Placing.mongoize(place_hash)).to_not be_nil
      expect(p).to be_a Hash
      expect(p[:name]).to eql name
      expect(p[:place]).to eql place
      placing = Placing.demongoize(place_hash)
      expect(p1 = Placing.mongoize(placing)).to_not be_nil        
      expect(p1).to be_a Hash
      expect(p1[:name]).to eql name
      expect(p1[:place]).to eql place
    end

    it "mongoize class method handles nil arguments gracefully" do
      expect(Placing.mongoize(nil)).to be_nil
    end

    it "Placing Model has an class method called evolve" do
      expect(Placing).to respond_to(:evolve)
    end
      
    it "evolve method takes an object parameter that can be hash, Placing or parameter hash" do 
      expect((Placing.method(:evolve).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Placing.method(:evolve).parameters.flatten).to include :req
      expect(Placing.method(:evolve).parameters.flatten).to_not include :opt
    end

    it "evolve method returns a Placing with specified parameters in hash" do
      expect(p = Placing.evolve(place_hash)).to_not be_nil
      expect(p).to be_a Hash
      expect(p[:name]).to eql name
      expect(p[:place]).to eql place
      placing = Placing.demongoize(place_hash)
      expect(p1 = Placing.evolve(placing)).to_not be_nil        
      expect(p1).to be_a Hash
      expect(p1[:name]).to eql name
      expect(p1[:place]).to eql place
    end

    it "evolve method handles nil arguments gracefully" do
      expect(Placing.evolve(nil)).to be_nil
    end
  end  

end