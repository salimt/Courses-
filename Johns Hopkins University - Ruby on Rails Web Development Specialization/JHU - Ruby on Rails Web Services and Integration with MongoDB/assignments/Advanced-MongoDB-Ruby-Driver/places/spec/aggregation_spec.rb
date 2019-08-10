require_relative '../config/environment'
require 'rails_helper'
require 'securerandom'
require 'place_utils'

feature "Module #2 Aggregation Framework Tests" do

    include Place_utils

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
      it "Place Model has a class method called get_address_components" do
        expect(Place).to respond_to(:get_address_components)
      end
      
      it "get_address_components method takes 3 optional parameters for sort, offset and limit" do 
        expect((Place.method(:get_address_components).parameters.flatten - [:opt, :req]).count).to eq 3
        expect(Place.method(:get_address_components).parameters.flatten).to include(:opt) 
        expect(Place.method(:get_address_components).parameters.flatten).to_not include(:req) 
      end

      it "get_address_components returns expected type and results" do
        raw_ac_count = 0
        Place.collection.find.each { |p0|
          raw_ac_count += (Place.new(p0)).address_components.count
        }
        p_result = Place.get_address_components
        expect(p_result).to_not be_nil
        expect(p_result).to be_a Mongo::Collection::View::Aggregation
        expect(p_result.count).to eq raw_ac_count
        p_result.each do |p|
          expect(p).to be_a BSON::Document
          expect(p[:_id]).to_not be be_nil
          expect(p[:address_components]).to_not be_nil
          expect(p[:address_components].count).to be > 0
          expect(p[:formatted_address]).to_not be_nil
          expect(p[:geometry][:location]).to be_nil
          expect(p[:geometry][:geolocation]).to_not be_nil
        end
      end

      it "get_address_components uses limit, offset and sort parameters" do
        sort = {_id: -1}
        offset = 5
        limit = 10
        base_result = Place.get_address_components(sort, 0, limit)
        off_result = Place.get_address_components(sort, offset, limit)
        expect(base_result.count).to eq limit
        expect(off_result.count).to eq limit
        expect(off_result.to_a[0][:_id]).to eq (base_result.to_a[offset][:_id])
        # test order
        current_Id = BSON::ObjectId('FFFFFFFFFFFFFFFFFFFFFFFF')
        base_result.each { |b| 
          expect(b[:_id]).to be <= current_Id
          current_Id = b[:_id]
        }
      end    
    end

    context "rq02" do 
      it "Place Model has a class method called get_country_names" do
        expect(Place).to respond_to(:get_country_names)
      end
      
      it "get_country_names method takes no arguments" do 
        expect((Place.method(:get_country_names).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "get_country_names returns expected type and result" do
        # manually get all distinct long_names
        raw_ac_count = []
        Place.collection.find.each { |p0|
          tp = Place.new(p0)
          tp.address_components.each { |ac|
            if (ac.types.include?("country") && !raw_ac_count.include?(ac.long_name))
              raw_ac_count.push(ac.long_name)
            end
          }
        }
        # add record with randomly seeded long_name
        test_name = SecureRandom.base64
        new_rec = Place.collection.find.to_a.sample
        new_rec[:_id] = nil
        new_rec[:address_components][0][:long_name] = test_name
        new_rec[:address_components][0][:types] = ["country"]
        Place.collection.insert_one(new_rec)
        raw_ac_count.push(test_name)
        test_result = Place.get_country_names
        expect(test_result).to_not be_nil
        expect(test_result).to be_a Array
        expect(test_result.count).to eq raw_ac_count.count
        test_result.each { |r| 
          expect(raw_ac_count).to include(r)
        }
      end    
    end

    context "rq03" do 
      it "Place Model has a class method called find_ids_by_country_code" do
        expect(Place).to respond_to(:find_ids_by_country_code)
      end
      
      it "find_ids_by_country_code method takes a single country_code argument" do 
        expect((Place.method(:find_ids_by_country_code).parameters.flatten - [:opt, :req]).count).to eq 1
        expect(Place.method(:find_ids_by_country_code).parameters.flatten).to include(:req) 
        expect(Place.method(:find_ids_by_country_code).parameters.flatten).to_not include(:opt)         
      end

      it "find_ids_by_country_code returns expected type and result" do
        country_code = "US"
        # manually get Ids with country_code
        test_arr = []
        Place.collection.find.each { |p0|
          tp = Place.new(p0)
          tp.address_components.each { |ac|
            if (ac.types.include?("country") && ac.short_name == country_code)
              test_arr.push(tp.id)
              break
            end
          }
        }
        results = Place.find_ids_by_country_code(country_code)
        expect(results).to_not be_nil
        expect(results).to be_a Array
        results.each { |r|
          expect(r).to be_a String
          rac = Place.collection.find(_id: BSON::ObjectId.from_string(r)).first[:address_components]
          expect(address_component_match?(rac, :short_name, country_code)).to be true
          expect(test_arr).to include(r)
        }
        expect(test_arr.count).to eq results.count
      end
    end    
  end
