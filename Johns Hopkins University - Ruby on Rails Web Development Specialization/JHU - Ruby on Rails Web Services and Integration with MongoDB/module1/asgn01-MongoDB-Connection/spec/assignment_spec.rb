require_relative "../assignment"
require 'rspec'
require 'mongo'

Mongo::Logger.logger.level = ::Logger::INFO

describe Solution do
  subject(:solution) { Solution.new }

  let!(:db_url) { Mongo::Client.new('mongodb://localhost:27017') }
  let!(:connection) { db_url.use('test') }

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

  #helper method to determine if Ruby class exists as a class
  def class_exists?(class_name)
    eval("defined?(#{class_name}) && #{class_name}.is_a?(Class)") == true
  end

  #helper method to determine if Ruby file imports a specific gem
  def includes_gem?(file_name, gem_name)
    puts "TEST = #{file_name}"
    File.foreach(file_name) do |line|
      text = line.strip.split(' ')
      if (text.first.start_with?('require') && text.include?("'#{gem_name}'"))       
        return true
      end
    end
    return false
  end

  # test for presence of test database and zips collection and data
  context "rq01" do
    it "Database 'test' should exist with collection 'zips' with data" do
      expect(connection[:zips].find.count).to be > 0
    end
  end 

  # test for presence of assignment.rb requiring mongo gem and defining a class Solution
  context "rq02" do
    it "all work should be in assignment.rb file" do
      expect(File.exists?("assignment.rb")).to be true
    end
    it "assignment.rb file imports the mongo gem" do
      expect(includes_gem?("assignment.rb", "mongo")).to be true
    end
    it "Class Solution should exist in assignment" do
      expect((class_exists?("Solution"))).to be true
    end
  end 

  # test for class method in solution called mongo_client that creates a connection to server
  # using the test db and returns that client
  context "rq03" do
    it "Solution implements a class method called mongo_client" do
      expect(Solution).to respond_to(:mongo_client)
    end

    db = Solution.mongo_client
    it "returns a mongo client connected to test collection" do
      expect(db).to_not be_nil
      expect(db).to be_a(Mongo::Client)
      expect(db.database.name).to eq('test')
    end

    it "mongo_client method returns a connection to test db" do 
      expect(Solution.mongo_client[:zips].find.count).to be > 0
    end
  end   

  # test for class method in Solution called collection that returns zips collection
  context "rq04" do
    it "Solution implements aa class method called collection" do
      expect(Solution).to respond_to(:collection)
    end

    test_data = Solution.collection
    it "collection method returns a Mongo Collection to zips" do
      expect(test_data).to_not be_nil
      expect(test_data).to be_a(Mongo::Collection)
      expect(test_data.name).to eq("zips")
    end

    it "collection method returns the entire zip collection" do 
      base_data = connection[:zips]
      expect(test_data).to eq(base_data)
    end
  end

  # test for instance method in Solution called sample that returns one document from collection
  context "rq05" do   
    it "Solution implements an instance method called sample" do
      expect(solution).to respond_to(:sample)
    end

    it "sample method returns a single item from collection" do
      doc = solution.sample
      expect(doc).to_not be_nil
      expect(doc[:_id]).to_not be_nil
      expect(doc[:city]).to_not be_nil
      expect(doc[:state]).to_not be_nil
      expect(doc[:pop]).to_not be_nil
      expect(doc[:loc]).to_not be_nil
      db_data = db_url[:zips].find(_id: doc[:_id])
      expect(db_data).to_not be_nil
    end
  end
end