require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'
require 'place_utils'

feature "Module #2 Relationship Tests" do
    include Place_utils

    before :all do
        $continue = true
    end

    before :each do
      Place.collection.delete_many
      Place.load_all(File.open("./db/places.json"))
      #clear grid fs db
      Photo.mongo_client.database.fs.find.each { |p| 
        Photo.mongo_client.database.fs.find(:_id=>p[:_id]).delete_one
      }
      #reload db with application images
      (1..6).each { |n|
        p = Photo.new 
        f = File.open("./db/image#{n}.jpg",'rb')
        p.contents = f
        id = p.save
      }    

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
      @photo = Photo.new
      Place.create_indexes
    end

    it "Photo Model has an intance method called find_nearest_place_id" do
      expect(@photo).to respond_to(:find_nearest_place_id)
    end
      
    it "find_nearest_place_id method takes one arguments for max distance in meters" do
      expect((@photo.method(:find_nearest_place_id).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(@photo.method(:find_nearest_place_id).parameters.flatten).to include(:req)
      expect(@photo.method(:find_nearest_place_id).parameters.flatten).to_not include(:opt)
    end    

    it "find_nearest_place_id returns expected type and result" do
      max_d = 1069.4 * 1000
      # find nearest using "brute force" for test comparison
      sample_photo = Photo.all.to_a.sample
      sample_photo_loc = sample_photo.location
      bf_place = nil
      c_distance = 99999999.0
      Place.all.each { |p|
        d = Geo_utils.PointDistance(sample_photo_loc, p.location)
        if (d < max_d && d < c_distance) then
          bf_place = p
          c_distance = d
        end
      }
      # result will either be nil (no nearest within distance) or a single id
      result = sample_photo.find_nearest_place_id(max_d)
      expect(result.nil? || result.is_a?(BSON::ObjectId)).to be true
      expect(result.nil? || result.to_s == bf_place.id).to be true
    end    
  end

  context "rq02" do
    before :all do
      @photo = Photo.new
    end  

    it "Photo instance method save, saves object if not already persitsed" do 
      # Test to see if it persists new object
      f = File.open('./db/image1.jpg','rb')
      test = EXIFR::JPEG.new(f).gps
      f.rewind
      @photo.contents = f
      expect(@photo.persisted?).to be false
      @id=@photo.save      
      expect(@id).to_not be_nil
      expect(@photo.persisted?).to be true
      expect(@photo.location.latitude).to eq test.latitude
      expect(@photo.location.longitude).to eq test.longitude
      metaData = Photo.mongo_client.database.fs.find(:_id=>BSON::ObjectId.from_string(@photo.id)).first
      expect(metaData["contentType"]).to eq "image/jpeg"
      expect(metaData[:metadata][:location][:coordinates]).to_not be nil
      expect(metaData[:metadata][:location][:coordinates][0]).to eq test.longitude
      expect(metaData[:metadata][:location][:coordinates][1]).to eq test.latitude
    end

    it "Photo instance method save, updates object if already persisted" do      
      # Change an already persisted object and call save
      record = Photo.all.sample
      id = record.id
      expect(record.persisted?).to be true
      record.location.latitude = 0.0
      record.location.longitude = 0.0
      record.save
      new_record = Photo.find(id)
      expect(new_record.location.latitude).to eq 0.0
      expect(new_record.location.longitude).to eq 0.0
    end
  end

  context "rq03" do
    before :all do
      @photo = Photo.new
    end  

    it "There exists a many to one relationship between Place and Photo" do
      photo = Photo.all.sample
      photo_id = photo.id
      expect(photo.place).to be_nil
      place_id = photo.find_nearest_place_id(100*1069.34)
      photo.place = place_id
      photo.save

      # check database stored version now
      db_photo = Photo.find photo_id.to_s
      place_obj = Place.find place_id.to_s
      # db photo place getter should return place object
      expect(db_photo.place).to be_a Place
      expect(db_photo.place.id).to eq place_obj.id
      expect(db_photo.place.formatted_address).to eq place_obj.formatted_address
      expect(db_photo.place.location.latitude).to eq place_obj.location.latitude
      expect(db_photo.place.location.longitude).to eq place_obj.location.longitude
      expect(db_photo.place.address_components.count).to eq place_obj.address_components.count
      db_photo.place.address_components.each { |ac|
         expect(contains_address_component?(place_obj.address_components, ac)).to be true
      } 
      # check to see that BSON object includes place under metadata
      bson_photo = Photo.mongo_client.database.fs.find(:_id=>BSON::ObjectId(photo_id)).first
      expect(bson_photo[:metadata][:place]).to eq place_id
    end
  end

  context "rq04" do

    it "Photo Model has a class method called find_photos_for_place" do
      expect(Photo).to respond_to(:find_photos_for_place)
    end
      
    it "find_photos_for_place method takes one arguments for the id of the place of interest" do
      expect((Photo.method(:find_photos_for_place).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Photo.method(:find_photos_for_place).parameters.flatten).to include(:req)
      expect(Photo.method(:find_photos_for_place).parameters.flatten).to_not include(:opt)
    end    

    it "find_photos_for_place returns expected type and result" do
      # Set constant for test place and get its id
      place = Place.all.sample
      place_id = place.id
      alternate = true
      p_list = []
      Photo.all.each { |photo| 
        if alternate then
          photo.place = place
          photo.save
          p_list.push(photo.id)
        end
        alternate = !alternate
      }
      # Now test method, it should return 3 of the six photos
      result = Photo.find_photos_for_place(BSON::ObjectId(place_id))
      expect(result).to be_a Mongo::Collection::View
      expect(result.count).to eq p_list.count
      result.each { |r|
        expect(r).to be_a BSON::Document
        expect(p_list).to include(r[:_id].to_s)
      }
      # Now test method for just string id
      result = Photo.find_photos_for_place(place_id)
      expect(result).to be_a Mongo::Collection::View
      expect(result.count).to eq p_list.count
      result.each { |r|
        expect(r).to be_a BSON::Document
        expect(p_list).to include(r[:_id].to_s)
      }
    end    
  end

  context "rq05" do
    before :all do
      @place = Place.all.first
    end

    it "Place Model has an intance method called photos" do
      expect(@place).to respond_to(:photos)
    end
      
    it "photos method takes one arguments for the id of the place of interest" do
      expect((@place.method(:photos).parameters.flatten - [:opt, :req]).count).to eq 2
      expect(@place.method(:photos).parameters.flatten).to include(:opt)
      expect(@place.method(:photos).parameters.flatten).to_not include(:req)
    end    

    it "photos method without parameters returns expected type and result" do
      # Set constant for test place and get its id
      place = Place.all.sample
      place_id = place.id
      p_list = []
      Photo.all.each { |photo| 
        photo.place = place
        photo.save
        p_list.push(photo.id)
      }
      # Now test method, it should return 3 of the six photos
      result = place.photos
      expect(result).to be_a Array
      expect(result.count).to eq p_list.count
      result.each { |r|
        expect(r).to be_a Photo
        expect(p_list).to include(r.id)
        rplace = r.place
        expect(rplace.id).to eq (place.id)
        expect(rplace.formatted_address).to eq (place.formatted_address)
        expect(rplace.location.latitude).to eq (place.location.latitude)
        expect(rplace.location.longitude).to eq (place.location.longitude)   
        expect(rplace.address_components.count).to eq (place.address_components.count)
        rplace.address_components.each{ |ac|
          expect(contains_address_component?(place.address_components, ac)).to be true
        }     
      }
    end    
  end
end
