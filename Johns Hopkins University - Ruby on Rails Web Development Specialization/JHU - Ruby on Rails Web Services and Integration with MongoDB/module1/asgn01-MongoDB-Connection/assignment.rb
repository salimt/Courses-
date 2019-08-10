require 'mongo'
require 'pp'
require 'byebug'
require 'uri'
#Mongo::Logger.logger.level = ::Logger::INFO
#Mongo::Logger.logger.level = ::Logger::DEBUG

class Solution
  @@db = nil
  
  def self.mongo_client
    @@db = Mongo::Client.new('mongodb://localhost:27017/test')
  end

  # implement an instance method that returns a reference to the Mongo Collection object
  def self.collection
    self.mongo_client if not @@db
    @@db[:zips]
  end

  def sample
    self.class.collection.find.first
  end
end


#byebug
db=Solution.mongo_client
#p db
zips=Solution.collection
#p zips
s=Solution.new
pp s.sample