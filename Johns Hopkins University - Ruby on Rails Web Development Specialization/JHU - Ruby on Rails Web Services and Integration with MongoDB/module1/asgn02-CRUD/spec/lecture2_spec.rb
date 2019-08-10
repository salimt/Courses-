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

  # test for presence of instance method all
  context "rq01" do
    it "Solution implements an instance method called all" do
      expect(solution).to respond_to(:all)
    end
    it "Instance method all takes optional prototype hash" do 
      expect(Solution.instance_method(:all).parameters[0]).to include(:opt, :prototype)
    end
    it "method all takes returns all records filtered by hash" do
      hash_val = { gender: "F", group: "14 and under" }
      db_count = @race_col.find(hash_val).count
      db_first = @race_col.find(hash_val).first
      expect(solution.all(hash_val).count).to eq(db_count)
      meth_first = solution.all(hash_val).first
      expect(meth_first[:_id]).to eq(db_first[:_id])
      expect(meth_first[:first_name]).to eq(db_first[:first_name])
      expect(meth_first[:last_name]).to eq(db_first[:last_name])
      expect(meth_first[:gender]).to eq(db_first[:gender])
      expect(meth_first[:group]).to eq(db_first[:group])
      expect(meth_first[:secs]).to eq(db_first[:secs])
      expect(meth_first[:number]).to eq(db_first[:number])
    end
  end

  # test for presence of instance method find_by_name
  context "rq02" do
    it "Solution implements an instance method called find_by_name" do
      expect(solution).to respond_to(:find_by_name)
    end
    it "Instance method find_by_name takes two parameters for first_name and last_name" do 
      expect((Solution.instance_method(:find_by_name).parameters.flatten - [:opt, :req]).count).to eq(2)
    end
    it "method all takes returns first names and last names of records filtered by first_name and last_name" do
      hash_val = { first_name: "REENA", last_name: "TUCKER" }
      db_first = @race_col.find(hash_val).first
      meth_first = solution.find_by_name("REENA", "TUCKER").first
      expect(meth_first[:_id]).to be_nil
      expect(meth_first[:first_name]).to eq(db_first[:first_name])
      expect(meth_first[:last_name]).to eq(db_first[:last_name])
      expect(meth_first[:gender]).to be_nil
      expect(meth_first[:group]).to be_nil
      expect(meth_first[:secs]).to be_nil
      expect(meth_first[:number]).to eq(db_first[:number])
    end
  end  
end