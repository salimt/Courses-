require 'mongo'
require 'json'
require 'pp'
Mongo::Logger.logger.level = ::Logger::INFO
#Mongo::Logger.logger.level = ::Logger::DEBUG

class Racer
  MONGO_URL='mongodb://localhost:27017'
  MONGO_DATABASE='test'
  RACE_COLLECTION='race2'
  RACER_COLLECTION='racer2'

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
  def self.races_collection
    collection
  end
  def self.racers_collection
    collection=ENV['RACER_COLLECTION'] ||= RACER_COLLECTION
    return mongo_client[collection]
  end
  
  # helper method that will load a file and return a parsed JSON document as a hash
  def self.load_hash(file_path) 
    file=File.read(file_path)
    JSON.parse(file)
  end

  # drop the current contents of the collection and reload from data file
  def self.reset(file_path=nil) 
    dir_name = File.dirname(File.expand_path(__FILE__))
    file_path ||= "#{dir_name}/race2_results.json"
    if !File.exists?(file_path)
      puts "cannot find bootstrap at #{file_path}"
      return 0
    else
      collection.delete_many({})
      racers_collection.delete_many({})
      hash=load_hash(file_path)
      r=collection.insert_many(hash)
      return r.inserted_count
    end
  end
end

#Racer.reset
#Racer.reset ./student-start/race2_results.json"
