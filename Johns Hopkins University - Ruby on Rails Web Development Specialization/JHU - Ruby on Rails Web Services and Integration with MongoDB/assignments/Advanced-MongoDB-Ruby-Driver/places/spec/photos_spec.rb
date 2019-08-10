require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'
require 'test_utils'

feature "Module #2 Photo Tests" do

    include Test_utils

    before :all do
        $continue = true
    end

    before :each do
      Place.collection.delete_many
      Place.load_all(File.open("./db/places.json"))
      Photo.mongo_client.database.fs.find.each { |p| 
        f = Photo.mongo_client.database.fs.find_one(:_id=>p[:_id])
        Photo.mongo_client.database.fs.delete_one(f)
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
      context "Photo Model:" do
        it "Photo class created" do
          expect(class_exists?("Photo"))
        end
        it "Photo class has a method for mongo_client" do
          expect(Place).to respond_to(:mongo_client)
          expect(Place.mongo_client).to_not be_nil
          expect(Place.mongo_client).to be_a Mongo::Client
        end
      end
    end    

    context "rq02" do 
      it "Photo class has expected attributes" do
        place = Place.all.first
        f=File.open('./db/image1.jpg','rb')
        photo = Photo.new
        expect(photo).to respond_to(:id)
        expect{photo.id = "test_id"}.to_not raise_error
        expect(photo.id).to be_a String
        expect(photo.id).to eq "test_id"
        expect(photo).to respond_to(:location)
        expect{photo.location = place.location}.to_not raise_error
        expect(photo.location).to be_a Point
        expect(photo.location).to_not be_nil
        expect(photo.location.latitude).to eq(place.location.latitude)
        expect(photo.location.longitude).to eq(place.location.longitude)
        expect(photo).to respond_to(:contents=)
        expect{photo.contents = f}.to_not raise_error
      end
    end

  context "rq03" do 
    it "Photo has an initialize method to store id and location info from hash" do
      hash =  {:_id=>BSON::ObjectId('5652d94de301d0c0ad000001'),
               :chunkSize=>261120, 
               :uploadDate=>"2015-11-23 09:15:57 UTC", 
               :contentType=>"image/jpeg", 
               :metadata=>{:location=>
                 {:type=>"Point", :coordinates=>[-116.30161960177952, 33.87546081542969]}
               }, 
               :length=>601685, 
               :md5=>"871666ee99b90e51c69af02f77f021aa"
               } 
      photo = Photo.new(hash)
      expect(photo).to_not be_nil
      expect(photo.id).to eq hash[:_id].to_s
      tl = Point.new(hash[:metadata][:location])
      expect(photo.location.latitude).to eq tl.latitude
      expect(photo.location.longitude).to eq tl.longitude
    end
  end

  context "rq05" do
    before :all do
      @photo = Photo.new
    end

    it "Photo Model has an intance method called persisted?" do
      expect(@photo).to respond_to(:persisted?)
    end
      
    it "persisted? method takes no arguments" do
      expect((@photo.method(:persisted?).parameters.flatten - [:opt, :req]).count).to eq 0    
    end

    it "Photo Model has an intance method called save" do
      expect(@photo).to respond_to(:save)
    end
      
    it "save method takes no arguments" do
      expect((@photo.method(:save).parameters.flatten - [:opt, :req]).count).to eq 0    
    end      

    it "save returns expected type and result" do
      f = File.open('./db/image1.jpg','rb')
      test = EXIFR::JPEG.new(f).gps
      f.rewind
      @photo.contents = f
      expect(@photo.persisted?).to be false
      id=@photo.save
      expect(id).to_not be_nil
      expect(@photo.persisted?).to be true
      expect(@photo.location.latitude).to eq test.latitude
      expect(@photo.location.longitude).to eq test.longitude
      metaData = Photo.mongo_client.database.fs.find(:_id=>BSON::ObjectId.from_string(@photo.id)).first
      expect(metaData["contentType"]).to eq "image/jpeg"
      expect(metaData[:metadata][:location][:coordinates]).to_not be nil
      expect(metaData[:metadata][:location][:coordinates][0]).to eq test.longitude
      expect(metaData[:metadata][:location][:coordinates][1]).to eq test.latitude
    end    
  end

  context "rq06" do
    before :each do
      # reset db
      dt = Photo.mongo_client.database.fs
      dt.find.each { |p|
        dt.files_collection.find(:_id=>p[:_id]).delete_one
      }      

      #refill db
      (1..6).each { |n|
        p = Photo.new 
        f = File.open("./db/image#{n}.jpg",'rb')
        f.rewind
        p.contents = f
        id = p.save
      }
    end

    it "Photo Model has a class method called all" do
      expect(Photo).to respond_to(:all)
    end
      
    it "all method takes optional arguments for offset and limit" do
      expect((Photo.method(:all).parameters.flatten - [:opt, :req]).count).to eq 2
      expect(Photo.method(:all).parameters.flatten).to include(:opt)
      expect(Photo.method(:all).parameters.flatten).to_not include(:req)    
    end    

    it "all with no parameters returns expected type and result" do
      result = Photo.all
      expect(result).to_not be_nil
      expect(result.count).to eq Photo.mongo_client.database.fs.find.count
      result.each { |r| 
        expect(r).to be_a Photo
        expect(Photo.mongo_client.database.fs.find_one(:_id=>BSON::ObjectId.from_string(r.id))).to_not be nil
      }
    end 

    it "all with given parameters returns expected type and result" do
      presult = Photo.all(1, 2).to_a
      result = Photo.all.to_a
      expect(presult).to_not be_nil
      expect(presult.to_a[0].id).to eq (result.to_a[1].id)
      expect(presult.count).to eq 2
      presult.each { |r| 
        expect(r).to be_a Photo
        expect(Photo.mongo_client.database.fs.find_one(:_id=>BSON::ObjectId.from_string(r.id))).to_not be nil
      }
    end        
  end

  context "rq07" do

    before :each do
      # reset db
      dt = Photo.mongo_client.database.fs
      dt.find.each { |p|
        dt.files_collection.find(:_id=>p[:_id]).delete_one
      }      

      #refill db
      (1..6).each { |n|
        p = Photo.new 
        f = File.open("./db/image#{n}.jpg",'rb')
        f.rewind
        p.contents = f
        id = p.save
      }
    end

    it "Photo Model has a class method called find" do
      expect(Photo).to respond_to(:find)
    end
      
    it "find method takes one arguments the id of the file to find" do
      expect((Photo.method(:find).parameters.flatten - [:opt, :req]).count).to eq 1
      expect(Photo.method(:find).parameters.flatten).to_not include(:opt)
      expect(Photo.method(:find).parameters.flatten).to include(:req)    
    end    

    it "find returns expected type and result" do
      raw_result = Photo.mongo_client.database.fs.find.to_a.sample
      id = raw_result[:_id]
      test_result = Photo.find(id.to_s)
      expect(test_result).to_not be_nil
      expect(test_result).to be_a Photo
      expect(test_result.location.latitude).to eq raw_result[:metadata][:location][:coordinates][1]
      expect(test_result.location.longitude).to eq raw_result[:metadata][:location][:coordinates][0]
    end 
  end  

  context "rq08" do
    before :all do
      @photo = Photo.new
      # reset db
      dt = Photo.mongo_client.database.fs
      dt.find.each { |p|
        dt.files_collection.find(:_id=>p[:_id]).delete_one
      } 
    end

    it "Photo Model has an intance method called contents" do
      expect(@photo).to respond_to(:contents)
    end
      
    it "contents method takes no arguments" do
      expect((@photo.method(:contents).parameters.flatten - [:opt, :req]).count).to eq 0    
    end     

    it "contents returns expected type and result" do
      #refill db
      photo = Photo.new 
      tf = File.open("./db/image1.jpg",'rb')
      tf.rewind
      photo.contents = tf
      id = photo.save      
      tid = Photo.mongo_client.database.fs.find.first[:_id]
      p = Photo.find(tid.to_s)
      nf = File.open("./log/output.jpg", "wb") { |file| file.write(p.contents) }
      f = File.open('./db/image1.jpg','rb')
      expect(File.size('./log/output.jpg')).to eq File.size('./db/image1.jpg')
      expect(FileUtils.identical?('./log/output.jpg', './db/image1.jpg')).to be true
      File.delete('./log/output.jpg')
    end    
  end

  context "rq09" do
    before :all do
      @photo = Photo.new
      # reset db
      dt = Photo.mongo_client.database.fs
      dt.find.each { |p|
        dt.files_collection.find(:_id=>p[:_id]).delete_one
      } 
    end

    it "Photo Model has an intance method called destroy" do
      expect(@photo).to respond_to(:destroy)
    end
      
    it "destroy method takes no arguments" do
      expect((@photo.method(:destroy).parameters.flatten - [:opt, :req]).count).to eq 0    
    end     

    it "destroy deletes file and its contents from GridFS" do
      files_stored = Photo.mongo_client.database.fs.find.count
      @photo.contents = File.open("./db/image1.jpg",'rb')
      @photo.save
      expect(Photo.find(@photo.id)).to_not be_nil
      expect(Photo.mongo_client.database.fs.find.count).to eq (files_stored + 1)
      @photo.destroy
      expect(Photo.find(@photo.id)).to be_nil
      expect(Photo.mongo_client.database.fs.find.count).to eq (files_stored)      
    end    
  end  
end
