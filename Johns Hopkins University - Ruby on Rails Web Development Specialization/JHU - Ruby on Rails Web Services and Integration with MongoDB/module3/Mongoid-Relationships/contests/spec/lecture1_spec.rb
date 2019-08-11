# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 1:1 Embedded Relationship Tests" do
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

	  it "Racer class created" do
    	expect(class_exists?("Racer"))
   	end

    it "Racer class has fields for first_name, last_name and date_of_birth" do
      expect(Racer.new).to respond_to(:first_name)
      expect(Racer.new).to respond_to(:last_name)
      expect(Racer.new).to respond_to(:date_of_birth)
    end

    it "Racer class has field mappings fn, ln and dob" do
      expect(Racer.new).to respond_to(:fn)
      expect(Racer.new).to respond_to(:ln)
      expect(Racer.new).to respond_to(:dob)
    end

    it "Racer class values are accessible through fields and mappings" do
      racer = Racer.new(:first_name=>"Thing", :last_name=>"Two", :date_of_birth=>Date.new(1962, 12, 12))
      expect(racer.first_name).to eql "Thing"
      expect(racer.last_name).to eql "Two"
      expect(racer.date_of_birth).to eql Date.new(1962, 12,12)
      expect(racer.fn).to eql "Thing"
      expect(racer.ln).to eql "Two"
      expect(racer.dob).to eql Date.new(1962, 12,12)   
    end
  end

  context "rq02" do

      before :all do
        @address = Address.new(:city=>"somewhere", :geolocation=>Point.new(0,1))
        Racer.collection.delete_many
        @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :primary_address=>@address)  
      end    

      it "Address is embeded within Racer" do
        expect(first_racer = Racer.collection.find.first).to_not be_nil
        expect(first_racer[:primary_address][:_id]).to eql @address.id
      end

      it "Can navigate from Racer to Address" do
        expect(@racer.primary_address.city).to eql @address.city
        expect(@racer.primary_address.geolocation.longitude).to eql @address.geolocation.longitude
        expect(@racer.primary_address.geolocation.latitude).to eql @address.geolocation.latitude
      end
  end

  context "rq03" do
      
      before :all do
        @address = Address.new(:city=>"somewhere", :geolocation=>Point.new(0,1))
        Racer.collection.delete_many
        @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :primary_address=>@address)  
      end    

      it "Address is embeded within Racer" do
        expect(first_racer = Racer.collection.find.first).to_not be_nil
        expect(first_racer[:primary_address][:_id]).to eql @address.id
      end

      it "Can navigate from Racer to Address" do
        expect(@racer.primary_address.city).to eql @address.city
        expect(@racer.primary_address.geolocation.longitude).to eql @address.geolocation.longitude
        expect(@racer.primary_address.geolocation.latitude).to eql @address.geolocation.latitude
      end

      it "Can navigate from Address back to Racer" do
        r = Racer.where(:fn=>@racer.first_name, :ln=>@racer.last_name).first
        add1 = r.primary_address 
        if (add1.methods.include?(:racer))
          expect(add1.racer.id).to eql r.id
        else
          expect(add1.addressable.id).to eql r.id
        end
      end
  end

  context "rq04" do
      
      before :all do
        @address = Address.new(:city=>"somewhere", :geolocation=>Point.new(0,1))
        Racer.collection.delete_many
        @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :primary_address=>@address)  
      end    

      it "Address is embeded within Racer" do
        expect(first_racer = Racer.collection.find.first).to_not be_nil
        expect(first_racer[:primary_address][:_id]).to eql @address.id
      end

      it "Can navigate from Racer to Address" do
        expect(@racer.primary_address.city).to eql @address.city
        expect(@racer.primary_address.geolocation.longitude).to eql @address.geolocation.longitude
        expect(@racer.primary_address.geolocation.latitude).to eql @address.geolocation.latitude
      end

      it "Can navigate from Address back to Racer via Polymorhic Relationship" do
        r = Racer.where(:fn=>@racer.first_name, :ln=>@racer.last_name).first
        add1 = r.primary_address 
        expect(add1.addressable.id).to eql r.id
      end
  end   

  context "rq05" do 
    before :all do 
      Venue.collection.delete_many
    end
    
    let(:name) { "Venue Name" }
  
    it "Venue class created" do
      expect(class_exists?("Venue"))
    end

    it "Venue class has the specified field" do
      expect(venue = Venue.create(:name=>name)).to_not be_nil
      expect(venue).to be_a Venue
      expect(venue).to respond_to(:name)      
      expect(venue.name).to be_a String
      expect(n = venue.name).to_not be_nil
      db_venue = Venue.collection.find.first
      expect(db_venue[:name]).to eql venue.name
    end
  end

  context "rq06" do

    before :all do
        Racer.collection.delete_many
        Address.collection.delete_many
        Venue.collection.delete_many
        @venue = Venue.create(:name=>"Boston")
        @address = @venue.create_address(:city=>"Boston", :state=>"MA", :geolocation=>Point.new(71.5, 42.21))
        @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :primary_address=>@address)  
    end  

    it "Address is embedded within venue object" do
      expect(v = Venue.collection.find.first).to_not be_nil
      expect(v[:address]).to_not be_nil
      expect(v[:address][:_id]).to eql @address.id
      expect(v[:address][:city]).to eql @address.city
      expect(v[:address][:state]).to eql @address.state
      expect(v[:address][:geolocation][:coordinates][0]).to eql @address.geolocation.longitude
      expect(v[:address][:geolocation][:coordinates][1]).to eql @address.geolocation.latitude
    end

    it "Address is embedded within Racer object" do
      expect(r = Racer.collection.find.first).to_not be_nil
      expect(r[:primary_address]).to_not be_nil
      expect(r[:primary_address][:_id]).to eql @address.id
      expect(r[:primary_address][:city]).to eql @address.city
      expect(r[:primary_address][:state]).to eql @address.state
      expect(r[:primary_address][:geolocation][:coordinates][0]).to eql @address.geolocation.longitude
      expect(r[:primary_address][:geolocation][:coordinates][1]).to eql @address.geolocation.latitude
    end

    it "Navigating from address back (to venue or racer) returns correct object" do
      expect(r = Racer.where(:ln=>"inhat").first).to_not be_nil
      add1 = r.primary_address 
      expect(add1.addressable).to be_a Racer
      expect(add1.addressable.id).to eql r.id
      expect(v = Venue.where(:name=>"Boston").first).to_not be_nil
      add2 = v.address 
      expect(add2.addressable).to be_a Venue
      expect(add2.addressable.id).to eql v.id      
    end
  end
end