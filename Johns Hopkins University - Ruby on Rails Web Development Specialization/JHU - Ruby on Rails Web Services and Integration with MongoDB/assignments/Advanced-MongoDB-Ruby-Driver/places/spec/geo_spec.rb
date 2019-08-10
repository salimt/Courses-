require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'

feature "Module #2 Geolocation Tests" do

    before :all do
        $continue = true
    end

    before :each do
      Place.collection.delete_many
      Place.load_all(File.open("./db/places.json"))
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
      it "Place Model has a class method called create_indexes" do
        expect(Place).to respond_to(:create_indexes)
      end
      
      it "create_indexes method takes no parameters" do 
        expect((Place.method(:create_indexes).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "create_indexes add a 2dsphere index to geolocation.geometry" do
        Place.create_indexes
        expect(Place.collection.indexes.map{|r| r[:name]}).to include("geometry.geolocation_2dsphere")
      end

      it "Place Model has a class method called remove_indexes" do
        expect(Place).to respond_to(:remove_indexes)
      end
      
      it "remove_indexes method takes no parameters" do 
        expect((Place.method(:remove_indexes).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "remove_indexes removes the 2dsphere index to geolocation.geometry" do
        expect(Place.collection.indexes.map{|r| r[:name]}).to include("geometry.geolocation_2dsphere")
        Place.remove_indexes
        expect(Place.collection.indexes.map{|r| r[:name]}).to_not include("geometry.geolocation_2dsphere")
      end
    end

    context "rq02" do 
      it "Place Model has a class method called near" do
        expect(Place).to respond_to(:near)
      end
      
      it "near method takes a required Point and optional max_meters parameter arguments" do
        expect((Place.method(:near).parameters.flatten - [:opt, :req]).count).to eq 2
        expect(Place.method(:near).parameters.flatten).to include(:opt)
        expect(Place.method(:near).parameters.flatten).to include(:req)      
      end

      it "near returns expected type and result" do
        Place.create_indexes
        ref_point = {:type=>"Point", :coordinates=>[-76.61666667, 39.33333333]}
        ref_distance = 1069.4 * 1000
        ref_list = []
        Place.collection.find.each { |p| 
          if (Geo_utils.distance(p[:geometry][:geolocation], ref_point) <= ref_distance)
            ref_list.push(p)
          end
        }
        list_near = Place.near(Point.new(ref_point), ref_distance)
        expect(list_near).to be_a Mongo::Collection::View
        expect(ref_list.count).to eq list_near.count
        list_near.each { |l| 
          expect(l).to be_a BSON::Document
          expect(ref_list).to include(l)
        }  
      end    
    end

    context "rq03" do 
      before :all do
        @place = Place.new(Place.collection.find.first)
      end

      it "Place Model has a instance method called near" do
        expect(@place).to respond_to(:near)
      end
      
      it "near method takes an optional max_meters parameter arguments" do
        expect((@place.method(:near).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(@place.method(:near).parameters.flatten).to include(:opt)
        expect(@place.method(:near).parameters.flatten).to_not include(:req)      
      end

      it "near returns expected type and result" do
        Place.create_indexes
        ref_point = {:type=>"Point", :coordinates=>[-76.61666667, 39.33333333]}
        ref_distance = 1069.4 * 1000
        ref_list = []
        Place.collection.find.each { |p| 
          if (Geo_utils.distance(p[:geometry][:geolocation], ref_point) <= ref_distance)
            ref_list.push(p)
          end
        }
        # Create a place around reference point
        p1 = Place.new(:_id=>BSON::ObjectId('56c0f75ae301d066a2009999'), 
                       :formatted_address=>"JHU, Baltimore, MD", 
                       :geometry=>{:geolocation=>ref_point})
        list_near = p1.near(ref_distance)
        expect(list_near).to be_a Array
        expect(ref_list.count).to eq list_near.count
        id_list = ref_list.map{|e| e[:_id]}
        list_near.each { |l| 
          expect(l).to be_a Place
          expect(id_list).to include(BSON::ObjectId.from_string(l.id))
        }  
      end    
    end    
  end
