require 'json'

class Place
  include ActiveModel::Model
  attr_accessor :id, :formatted_address, :location, :address_components

  # initialize from both a Mongo and Web hash
  def initialize(params={})
	  @id=params[:_id].nil? ? params[:id] : params[:_id].to_s
	  @address_components = params[:address_components].nil? ? nil : params[:address_components].collect {|address| AddressComponent.new(address)}
	  @formatted_address=params[:formatted_address]
	  @location=Point.new(params[:geometry][:geolocation])
  end

  # convenience method for access to client in console
  def self.mongo_client
  	Mongoid::Clients.default
  end

  # convenience method for access to places collection
  def self.collection
  	self.mongo_client['places']
  end

  def persisted?
	!@id.nil?
  end

  # loads the documents into the db
  def self.load_all(file)
  	collection.insert_many(JSON.parse(file.read));
  end

  #find docs by the their short_name
  def self.find_by_short_name(name)
  	collection.find({ 'address_components.short_name' => name })
  end

  def self.to_places(input)
  	places = input.collect {|place| Place.new(place)}
  	return places
  end

  # locate a specific document. Use initialize(hash) on the result to 
  # get in class instance form
  def self.find(id)
  	id=BSON::ObjectId(id) if id.is_a?(String)
  	doc=collection.find(_id: id).first
    return doc.nil? ? nil : Place.new(doc)
  end 

  # implement a find that returns a collection of document as hashes. 
  # Use initialize(hash) to express individual documents as a class 
  # instance. 
  #   * offset - number of documents to ignore from start
  #   * limit - number of documents to include
  def self.all(offset=0, limit=nil)
	result=collection.find().skip(offset)
    result=result.limit(limit) if !limit.nil?
    return to_places(result)
  end

  # remove the document associated with this instance form the DB
  def destroy
    self.class.collection.find(_id: BSON::ObjectId.from_string(@id)).delete_one   
  end 

  #return address compenents that meets the given parameters
  def self.get_address_components(sort={_id: -1}, offset=0, limit=nil)
  	conditions = [{:$unwind=> "$address_components"}, 
  		          {:$project=>{ :address_components=>1,:formatted_address=>1, "geometry.geolocation":1 }}, 
  			      {:$sort=> sort}, {:$skip=> offset}]
  	
  	conditions << {:$limit=> limit} if limit
  	return collection.find.aggregate(conditions)
  end

  #returns distinct long country names in array format
  def self.get_country_names
  	collection.aggregate([{:$project=> {"address_components.long_name": 1, "address_components.types": 1}},
  						  {:$unwind=> "$address_components"},
  						  {:$match=> {"address_components.types"=> "country"}},
  						  {:$group=> {_id: "$address_components.long_name"}}]).to_a.map {|h| h[:_id]}
  end

  #returns ids that matches the country code in array format
  def self.find_ids_by_country_code(countryCode)
  	collection.aggregate([{:$match=> {"address_components.short_name"=> countryCode}},
  						  {:$project=> {_id: 1}}]).map {|doc| doc[:_id].to_s}
  end

  #create a 2dsphere index to your collection for the geometry.geolocation property
  def self.create_indexes
  	collection.indexes.create_one( {'geometry.geolocation': "2dsphere"})
  end

  #remove a 2dsphere index to your collection for the geometry.geolocation property
  def self.remove_indexes
	collection.indexes.drop_all
  end

  #return the points that are near the given point at maxDist
  def self.near(point, maxDist=0)
	collection.find( {'geometry.geolocation'=>
	                         { :$near=>
	                           { :$geometry=> point.to_hash ,
	                             :$maxDistance=> maxDist}}})
  end

  def near max_meters=0
  	self.class.to_places(self.class.near(@location, max_meters))
  end
 
  #return photos after number of offset and limit it by limit parameter
  def photos(offset=0, limit=nil)
    result=Photo.find_photos_for_place(@id).skip(offset)
    result=result.limit(limit) if !limit.nil?
    result=result.map {|photo| Photo.new(photo)}
  end

end

