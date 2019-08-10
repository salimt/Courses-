require_relative '../config/environment'
require 'rails_helper'
require 'place_utils'

describe "Module #2 Standard Queries Tests" do

   before(:all) do
       $continue = true
   end

    before(:each) do
      Place.collection.delete_many
      Place.load_all(File.open("./db/places.json"))
    end

    around(:each) do |example|
        if $continue
            $continue = false 
            example.run 
            $continue = true unless example.exception
        else
            example.skip
        end
    end

    after(:all) do 
      Place.collection.delete_many
    end

    include Place_utils

    context "rq01" do 
      it "Place Model has a class method called find_by_short_name" do
        expect(Place).to respond_to(:find_by_short_name)
      end
      
      it "find_by_short_name method takes a string parameter" do 
        expect((Place.method(:find_by_short_name).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Place.method(:find_by_short_name).parameters.flatten).to include(:req) 
        expect(Place.method(:find_by_short_name).parameters.flatten).to_not include(:opt) 
      end

      it "find_by_short_name returns expected type and results" do
        p_result = []
        Place.collection.find.each do |p|
          if (address_component_match?(p[:address_components], :short_name, "DE"))
            p_result.push(p)
          end
        end
        view = Place.find_by_short_name("DE")
        expect(view).to_not be_nil
        expect(view).to be_a Mongo::Collection::View
        expect(view.count).to eq p_result.count
      end
    end
    
    context "rq02" do 
      it "Place Model has a class method called to_places" do
        expect(Place).to respond_to(:to_places)
      end
      
      it "to_places method takes a Mongo::Collection::View parameter" do 
        expect((Place.method(:to_places).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Place.method(:to_places).parameters.flatten).to include(:req) 
        expect(Place.method(:to_places).parameters.flatten).to_not include(:opt) 
      end

      it "to_places returns expected type and results" do
        p_result = []
        Place.collection.find.each do |p|
          if (address_component_match?(p[:address_components], :short_name, "DE"))
            p_result.push(p)
          end
        end
        result = Place.to_places(Place.find_by_short_name("DE"))
        expect(result).to_not be_nil
        expect(result).to be_a Array
        expect(result.count).to eq p_result.count
        result.each { |r| 
          expect(r).to be_a Place
          cmp_val = Place.new(Place.collection.find(:_id=>BSON::ObjectId.from_string(r.id)).first)
          expect(r.formatted_address).to eq cmp_val.formatted_address
          expect(r.location.latitude).to eq cmp_val.location.latitude
          expect(r.location.longitude).to eq cmp_val.location.longitude
          expect(r.address_components.count).to eq cmp_val.address_components.count
          r.address_components.each { |ac| 
            expect(contains_address_component?(cmp_val.address_components, ac)).to be true
          }
        }
      end
    end

    context "rq03" do 
      it "Place Model has a class method called find" do
        expect(Place).to respond_to(:find)
      end
      
      it "find method takes a string parameter for object id to find" do 
        expect((Place.method(:find).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Place.method(:find).parameters.flatten).to include(:req) 
        expect(Place.method(:find).parameters.flatten).to_not include(:opt) 
      end

      it "find returns expected type and results" do
        result = Place.new(Place.find_by_short_name("DE").first)
        test_result = Place.find(result.id)
        expect(test_result).to be_a Place
        expect(test_result.formatted_address).to eq result.formatted_address
        expect(test_result.location.latitude).to eq result.location.latitude
        expect(test_result.location.longitude).to eq result.location.longitude
        expect(test_result.address_components.count).to eq result.address_components.count
        test_result.address_components.each { |ac| 
          expect(contains_address_component?(result.address_components, ac)).to be true
        }
      end
    end

    context "rq04" do 
      it "Place Model has a class method called all" do
        expect(Place).to respond_to(:all)
      end
      
      it "all method takes two optional arguments for offset and limit" do 
        expect((Place.method(:all).parameters.flatten - [:opt, :req]).count).to eq 2
        expect(Place.method(:all).parameters.flatten).to include(:opt) 
        expect(Place.method(:all).parameters.flatten).to_not include(:req) 
      end

      it "all with default parameters returns a collection of all Place objects" do
        result = Place.collection.find
        test_result = Place.all
        expect(test_result).to be_a Array
        expect(result.count).to eq test_result.count
      end

      it "all with offset and limit parameters returns appropriate collection of all Place objects" do
        result = Place.collection.find
        all_result = Place.all
        test_result = Place.all(5, 10)
        expect(test_result.count).to eq 10
        expect(test_result[0].id).to eq all_result[5].id
      end
    end
   
    context "rq05" do
      before :all do
      	Place.collection.delete_many
      	Place.load_all(File.open("./db/places.json"))
        @place = Place.new(Place.collection.find.to_a.sample)
      end

      it "Place Model has an instance method called destroy" do
        expect(@place).to respond_to(:destroy)
      end
      
      it "destroy method takes no arguments" do 
        expect((@place.method(:destroy).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "destroy removes document for object from collection" do
        result = Place.all
        count = result.count
        p = result.sample
        expect(p).to_not be_nil
        l_id = p.id
        p.destroy
        expect(Place.find(l_id)).to be_nil
        expect(Place.all.count).to eq(count - 1)
      end
    end  

  end
