require_relative "../assignment"
require 'rspec'
require 'mongo'

Mongo::Logger.logger.level = ::Logger::INFO

describe Solution do
  subject(:solution) { Solution.new }
  let!(:MONGO_URL) { 'mongodb://localhost:27017' }
  let!(:MONGO_DATABASE ) { 'test' }
  let!(:RACE_COLLECTION ) { 'race1' }

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

  before :each do
    client = Mongo::Client.new(ENV['MONGO_URL'] ||= MONGO_URL)
    db = client.use(ENV['MONGO_DATABASE'] ||= MONGO_DATABASE) 
    @race_col = db[ENV['RACE_COLLECTION'] ||= RACE_COLLECTION] 
  end

  # helper method that will load a file and return a parsed JSON document as a hash
  def load_hash(file_path) 
    file=File.read(file_path)
    JSON.parse(file)
  end

  # test for presence of instance method clear_collection
  context "rq01" do
    it "Solution implements an instance method called clear_collection" do
      expect(solution).to respond_to(:clear_collection)
    end
    it "clear_collection deletes all documents and returns a Mongo result object" do
      expect(@race_col.find).to_not be_nil
      if @race_col.count==0
        @race_col.insert_one({:name=>"test data"})
      end
      expect(@race_col.find.count).to be > 0
      mongo_result = solution.clear_collection
      expect(mongo_result).to be_a(Mongo::Operation::Result)
      expect(@race_col.find.count).to eq(0)
    end
  end 

  # test for presence of instance method load_collection
  context "rq02" do
    it "Solution implements an instance method called load_collection" do
      expect(solution).to respond_to(:load_collection)
    end
    it "load_collection inserts all json documents in a file and returns a Mongo result object" do
      @race_col.delete_many({})
      expect(@race_col.find.count).to eq(0)
      mongo_result = solution.load_collection('race_results.json')
      expect(mongo_result).to be_a(Mongo::BulkWrite::Result)
      expect(@race_col.find.count).to be > 0
    end
  end 

  # test for presence of instance method insert
  context "rq03" do
    it "Solution implements an instance method called insert" do
      expect(solution).to respond_to(:insert)
    end
    it "insert accepts a hash and inserts it into collects" do
      total = @race_col.find.count
      hash = {first_name: "Rich", last_name: "Smith", gender: "M", group: "30 to 39", secs: 2500, number: 1000}
      mongo_result = solution.insert(hash)
      expect(@race_col.find.count).to eq(total + 1)
      expect(mongo_result).to be_a(Mongo::Operation::Result)
    end
  end 
  
end
