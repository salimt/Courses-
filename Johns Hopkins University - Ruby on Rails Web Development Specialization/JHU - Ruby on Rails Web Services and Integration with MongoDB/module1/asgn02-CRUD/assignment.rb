require 'mongo'
require 'json'
require 'pp'
require 'byebug'
Mongo::Logger.logger.level = ::Logger::INFO
#Mongo::Logger.logger.level = ::Logger::DEBUG

class Solution
  MONGO_URL='mongodb://localhost:27017'
  MONGO_DATABASE='test'
  RACE_COLLECTION='race1'

  # helper function to obtain connection to server and set connection to use specific DB
  # set environment variables MONGO_URL and MONGO_DATABASE to alternate values if not
  # using the default.
  def self.mongo_client
    url=ENV['MONGO_URL'] ||= MONGO_URL
    database=ENV['MONGO_DATABASE'] ||= MONGO_DATABASE 
    db = Mongo::Client.new(url)
    @@db=db.use(database)
  end

  # helper method to obtain collection used to make race results. set environment
  # variable RACE_COLLECTION to alternate value if not using the default.
  def self.collection
    collection=ENV['RACE_COLLECTION'] ||= RACE_COLLECTION
    return mongo_client[collection]
  end
  
  # helper method that will load a file and return a parsed JSON document as a hash
  def self.load_hash(file_path) 
    file=File.read(file_path)
    JSON.parse(file)
  end

  # initialization method to get reference to the collection for instance methods to use
  def initialize
    @coll=self.class.collection
  end

  #
  # Lecture 1: Create
  #

  def clear_collection
    @coll.delete_many
  end

  def load_collection(file_path) 
  	@coll.insert_many(self.class.load_hash(file_path))

  end

  def insert(race_result)
   @coll.insert_one(race_result)
  end

  #
  # Lecture 2: Find By Prototype
  #

  def all(prototype={})
    @all = @coll.find(prototype)
  end

  def find_by_name(fname, lname)
  	@coll.find(first_name: fname, last_name: lname).projection(first_name:1, last_name:1, number:1, _id:0)  
  end

  #
  # Lecture 3: Paging
  #

  def find_group_results(group, offset, limit) 
    @coll.find(group: group).projection(group:0, _id:0).sort(secs: 1).skip(offset).limit(limit)
  end

  #
  # Lecture 4: Find By Criteria
  #

  def find_between(min, max) 
  	@coll.find(secs: {:$gt=>min, :$lt=>max} )
  end

  def find_by_letter(letter, offset, limit) 
    @coll.find({last_name: /^#{letter}/}).sort(last_name: 1).skip(offset).limit(limit)
  end

  #
  # Lecture 5: Updates
  #
  
  def update_racer(racer)
    @coll.find(_id: racer[:_id]).replace_one(racer)
  end

  def add_time(number, secs)
  	@coll.find({number: number}).update_one(:$inc => {:secs => secs})
  end

end

s=Solution.new
race1=Solution.collection
