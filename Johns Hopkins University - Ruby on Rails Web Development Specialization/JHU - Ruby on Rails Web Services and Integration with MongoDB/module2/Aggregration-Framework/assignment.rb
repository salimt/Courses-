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

  # drop the current contents of the collection and reload from data file
  def self.reset(file_path) 
    self.collection.delete_many({})
    hash=self.load_hash(file_path)
    self.collection.insert_many(hash)
  end

  #
  # Lecture 1: Introduction
  #

    # use irb shell

  #
  # Lecture 2: $project
  #

  def racer_names
    @coll.find.aggregate([{:$project=>{ :_id=>0, :first_name=>1, :last_name=>1}}])
  end

  def id_number_map 
    @coll.find.aggregate([{:$project=>{ :number=>1 }}])
  end

  def concat_names
    @coll.find.aggregate([{:$project=>{ :_id=>0, :number=>1, name: { :$concat=>[ "$first_name", ", ", "$last_name" ] } }}])
  end

  #
  # Lecture 3: $group
    #place solution here
  #

  def group_times
  	@coll.find.aggregate([
  		{ :$group=> {
  				:_id=> { :age=> "$group", :gender=> "$gender" },
           		runners: { :$sum=> 1 },
                fastest_time: { :$min=> "$secs" }
            }
        }
    ])
  end

  def group_last_names
  	@coll.find.aggregate([
  		{ :$group=> {
  				:_id=> { :age=> "$group", :gender=> "$gender" },
                last_names: {:$push=>"$last_name"}
            }
        }
    ])
  end

  def group_last_names_set
  	@coll.find.aggregate([
  		{ :$group=> {
  				:_id=> { :age=> "$group", :gender=> "$gender" },
                last_names: {:$addToSet=>"$last_name"}
            }
        }
    ])
  end

  #
  # Lecture 4: $match
  #
  def groups_faster_than criteria_time
  	@coll.find.aggregate([
  		{:$match=>{:secs=>criteria_time}},
  		{ :$group=> {
  				:_id=> { :age=> "$group", :gender=> "$gender" },
           		runners: { :$sum=> 1 },
                fastest_time: { :$min=> "$secs" }
            }
        }
    ])
  end

  def age_groups_faster_than age_group, criteria_time
  	@coll.find.aggregate([
  		{:$match=>{:secs=>criteria_time, :group=>age_group}},
  		{ :$group=> {
  				:_id=> { :age=> "$group", :gender=> "$gender" },
           		runners: { :$sum=> 1 },
                fastest_time: { :$min=> "$secs" }
            }
        }
    ])
  end


  #
  # Lecture 5: $unwind
  #
  def avg_family_time last_name
  	@coll.find.aggregate([
  		{:$match=>{:last_name=>last_name}},
  		{ :$group=> {
  				:_id=> last_name,
           		avg_time: { :$avg=> "$secs" },
                numbers: { :$push=> "$number" }
            }
        }
    ])
  end
  
  def number_goal last_name
  	@coll.find.aggregate([
  		{:$match=>{:last_name=>last_name}},
  		{ :$group=> {
  				:_id=>"$last_name",
           		avg_time: { :$avg=> "$secs" },
                number: { :$push=> "$number" },
            }
        },
        {:$unwind=>'$numbers'},
        {:$project=>{_id:0, :number=>"$numbers", avg_time:1, last_name:"$_id"}}])
  end

end

file_path= "../student-start/race_results.json"
puts "cannot find bootstrap at #{file_path}" if !File.exists?(file_path)
Solution.reset(file_path)
s=Solution.new
