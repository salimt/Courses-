require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'
require 'place_utils'

feature "Module #2 Setup and Collection Tests" do

    include Test_utils
    include Place_utils

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

    context "rq00" do
      it "must have top level structure of a rails application" do
        expect(File.exists?("Gemfile")).to be(true)
        expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
        expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
      end
    end
    context "rq01" do # Resuse 'User' model and database table
      context "Place Model:" do
        it "Place class created" do
          expect(class_exists?("Place"))
        end
        it "Place class has methods for mongo_client and collection" do
          expect(Place).to respond_to(:mongo_client)
          expect(Place.mongo_client).to_not be_nil
          expect(Place.mongo_client).to be_a Mongo::Client
          expect(Place).to respond_to(:collection)
          expect(Place.collection).to_not be_nil
          expect(Place.collection).to be_a Mongo::Collection
        end
      end
    end

    context "rq02" do
      it "Place implements an class method called load_all" do
        expect(Place).to respond_to(:load_all)
      end

      it "Instance method load_all takes a parameter with JSON string of data" do 
        expect((Place.method(:load_all).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Place.method(:load_all).parameters.flatten).to include(:req) 
        expect(Place.method(:load_all).parameters.flatten).to_not include(:opt) 
      end


      it "load_all reads JSON string into a places collection" do
        Place.collection.delete_many
        Place.load_all(File.open("./db/places.json"))
        expect(Place.collection.count).to eq 39
      end
    end

    context "rq03" do 
      before :all do
        @lat = 53.82856035
        @lon = -1.8625303
        @p1 = Point.new({lat:@lat, lng:@lon})
        @p2 = Point.new({type:"Point", coordinates:[@lon, @lat]})
      end
      context "Point Model:" do
        it "Point class created" do
          expect(class_exists?("Point"))
        end
        it "Point class has expected attributes" do
          expect(@p1).to respond_to(:longitude)
          expect(@p1).to respond_to(:latitude)
          expect(@p1.latitude).to eq @lat
          expect(@p1.longitude).to eq @lon
          expect(@p2.latitude).to eq @lat
          expect(@p2.longitude).to eq @lon   
        end
        it "Point class initializer takes a hash of GeoJSON point format or with keys lat and lng" do
          expect((Point.method(:initialize).parameters.flatten - [:req, :opt]).count).to eq 1
          expect(@p1).to_not be_nil
          expect(@p1).to be_a Point
          expect(@p2).to_not be_nil
          expect(@p2).to be_a Point
        end
        it "Point class has initialize methods and instance method to_hash to create a GeoJSON point hash" do
          expect((@p1.method(:to_hash).parameters.flatten - [:req, :opt]).count).to eq 0         
          expect(@p1).to respond_to(:to_hash)
          expect(@p1.to_hash).to_not be_nil
          expect(@p1.to_hash[:type]).to eq 'Point'
          expect(@p1.to_hash[:coordinates][0]).to eq @lon
          expect(@p1.to_hash[:coordinates][1]).to eq @lat
          expect(@p2).to respond_to(:to_hash)
          expect((@p2.method(:to_hash).parameters.flatten - [:req, :opt]).count).to eq 0                   
          expect(@p2.to_hash).to_not be_nil
          expect(@p2.to_hash[:type]).to eq 'Point'
          expect(@p2.to_hash[:coordinates][0]).to eq @lon
          expect(@p2.to_hash[:coordinates][1]).to eq @lat
        end
      end
    end

    context "rq04" do 
      before :all do
        @ln = "Bradford District"
        @sn = "BD"
        @types = ["administrative_area_level_3", "political"]
        @test_hash = {long_name:@ln, short_name:@sn, types:@types}
        @ac = AddressComponent.new(@test_hash)
      end
      context "AddressComponent Model:" do
        it "AddressComponent class created" do
          expect(class_exists?("AddressComponent"))
        end
        it "AddressComponent class has expected attributes" do
          expect(@ac).to respond_to(:long_name)
          expect(@ac).to respond_to(:short_name)
          expect(@ac).to respond_to(:types)
          expect(@ac.long_name).to eq @ln
          expect(@ac.short_name).to eq @sn
          expect(@ac.types).to eq @types
        end
        it "AddressComponent class initializer takes a hash" do
          expect((AddressComponent.method(:initialize).parameters.flatten - [:req, :opt]).count).to eq 1
          expect(@ac).to_not be_nil
          expect(@ac).to be_a AddressComponent
        end
      end
    end    

    context "rq05" do 
      before :all do
        @hash = {:_id=>BSON::ObjectId('56521833e301d0284000003d'), 
                 formatted_address:"Wilsden, West Yorkshire, UK",
                 geometry:
                  {
                    location:{lat:53.8256035, lng:-1.8625303},
                    geolocation:{type:"Point", coordinates:[-1.8625303, 53.8256035]}
                  },
                  address_components: 
                  [
                    {long_name:"Wilsden", short_name:"Wilsden",
                     types:["administrative_area_level_4", "political"]},
                    {long_name:"Bradford District", short_name:"Bradford District",
                     types:["administrative_area_level_3", "political"]}
                  ]
        }
        @place = Place.new(@hash)
      end

      context "Place methods return expected types" do
        it "place.address_components returns an array of AddressComponent" do
          expect(@place.address_components).to be_a Array
          @place.address_components.each{ |ac| 
            expect(ac).to be_a AddressComponent
          }
        end

        it "place.location is an instance of the Point class" do
          expect(@place.location).to be_a Point
        end
      end

      context "Place Model Updates:" do
        it "Place class has expected attributes" do
          expect(@place).to respond_to(:id)
          expect(@place).to respond_to(:formatted_address)
          expect(@place).to respond_to(:location)
          expect(@place.id).to eq @hash[:_id].to_s
          expect(@place.formatted_address).to eq @hash[:formatted_address]
          @hash[:address_components].each { |a| 
            ac = AddressComponent.new(a)
            expect(contains_address_component?(@place.address_components, ac)).to be true
          }
        end
        it "Place class initializer takes a hash" do
          expect((Place.method(:initialize).parameters.flatten - [:req, :opt]).count).to eq 1
          expect(@place).to_not be_nil
          expect(@place).to be_a Place
        end
      end
    end    
  end
