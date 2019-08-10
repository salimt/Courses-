class Photo
  include Mongoid::Document
  require 'exifr/jpeg'

  
  attr_accessor :id, :location, :place
  attr_writer :contents

  # initialize from both a Mongo and Web hash
  def initialize(params={})
	@id=params[:_id].nil? ? params[:id] : params[:_id].to_s
	@location = (params[:metadata] && params[:metadata][:location]) ? Point.new(params[:metadata] [:location]) : nil
	@place = (params[:metadata] && params[:metadata][:place]) ? params[:metadata][:place] : nil
  end

  # convenience method for access to client in console
  def self.mongo_client
  	Mongoid::Clients.default
  end

   # tell Rails whether this instance is persisted
  def persisted?
    !@id.nil?
  end
  def created_at
    nil
  end
  def updated_at
    nil
  end

  # create a new document using the current instance
  def save 
  	if !persisted?
	  	file=EXIFR::JPEG.new(@contents)
	  	@location=Point.new(:lng=>file.gps.longitude, :lat=>file.gps.latitude)
	  	@contents.rewind
	  	desc={:content_type=> "image/jpeg", :metadata=>{:location=>@location.to_hash, :place=>@place}}
  	    @id=self.class.mongo_client.database.fs.insert_one(Mongo::Grid::File.new(@contents.read, desc)).to_s
  	else
  		desc={:content_type=> "image/jpeg", :metadata=>{:location=>@location.to_hash, :place=>@place}}
  		mongo_client.database.fs.find(_id:BSON::ObjectId.from_string(@id)).update_one(desc)
  	end
  end

  # implement a find that returns a collection of document as hashes. 
  # Use initialize(hash) to express individual documents as a class 
  # instance. 
  #   * offset - number of documents to ignore from start
  #   * limit - number of documents to include
  def self.all(offset=0, limit=nil)
	result=mongo_client.database.fs.find().skip(offset)
    result=result.limit(limit) if !limit.nil?
    return result.find.map{|doc| Photo.new(doc)}
  end

  #returns Photo instances that are equal the given parameter
  def self.find(id)
	f=mongo_client.database.fs.find(_id:BSON::ObjectId.from_string(id)).first
    return f.nil? ? nil : Photo.new(f)
  	#Photo.new(mongo_client.database.fs.find(:_id=> BSON::ObjectId.from_string(id)).first)
  end

  #custom getter -return photo's length in bytes
  def contents
	buffer = ""
  	mongo_client.database.fs.find_one(@contents).chunks.each {|chunk| buffer << chunk.data.data}
  	return buffer
  end

  # remove the document associated with this instance form the DB
  def destroy
    self.class.mongo_client.database.fs.find(_id:BSON::ObjectId.from_string(@id)).delete_one
  end 

  #return the points that are near the given point at maxDist
  def find_nearest_place_id max_meters
	val = Place.near(@location, max_meters).projection(_id: 1).first
	val.nil? ? nil : val[:_id]
  end

  #custom getter -return Place's id
  def place
  	return @place.nil? ? nil : Place.find(@place.to_s)
  end

  def place=(place)
    @place=case place
      		when String
        		BSON::ObjectId.from_string(place)
      		when Place
        		BSON::ObjectId.from_string(place.id)
      		when BSON::ObjectId
        		place
      		else
        		nil
      		end
  end

  #return photos that are sharing the same place id
  def self.find_photos_for_place place_id
  	id = place_id.is_a?(String) ? BSON::ObjectId.from_string(place_id) : place_id
  	mongo_client.database.fs.find("metadata.place": id)
  end
end
