require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'

feature "Module #2 Seed File Tests" do

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

    before :all do
      load "./db/seeds.rb" 
    end

    it "loads all data from ./db/places.json" do
      file = File.open('./db/places.json')
      json_data = JSON.parse(file.read)
      expect(json_data.count).to eq Place.all.count
      json_data.each{ |d|
        rec = Place.collection.find(d).first
        place = Place.find(rec[:_id])
        expect(place.formatted_address).to eq rec["formatted_address"]
        expect(place.location.latitude).to eq rec["geometry"]["geolocation"]["coordinates"][1]
        expect(place.location.longitude).to eq rec["geometry"]["geolocation"]["coordinates"][0]
      }
    end

    it "creates a 2dSphere index on geolocation.geometry in Place records" do
      expect(Place.collection.indexes.map{|r| r[:name]}).to include("geometry.geolocation_2dsphere")
    end

    it "loads all images from ./db" do
      expect(Dir.glob("./db/image*.jpg").count).to eq Photo.all.count
    end

    it "each photo loaded references nearest place within 1 mile" do
      Photo.all.each { |p| 
        expect(p.place).to_not be_nil
        expect(p.find_nearest_place_id(1069.4).to_s).to eq(p.place.id)
      }
    end
  end
end