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
    @limit = 50
    @offset = 10
    @group = "20 to 20"
    @sol_results = solution.find_group_results(@group, @offset, @limit)
  end

  # helper method that will load a file and return a parsed JSON document as a hash
  def load_hash(file_path) 
    file=File.read(file_path)
    JSON.parse(file)
  end

  # test for presence of instance method clear_collection
  context "Solution has instance_method find_group_results:" do
    it "Solution implements method" do
      expect(solution).to respond_to(:find_group_results)
    end
    it "accepts 3 parameters for group name, offset and limit values" do
      expect((Solution.instance_method(:find_group_results).parameters.flatten - [:opt, :req]).count).to eq(3)
    end
    it "returns a Mongo results object with items only for a specified group" do
      expect(@sol_results).to be_a(Mongo::Collection::View)
      @sol_results.each do |r|
        val = @race_col.find(number: r[:number]).first
        expect(val[:group]).to eq(@group)
      end
    end
    it "forms a projection, sorts by ascending times and supports limit" do
      current_time = 0
      counter = 0
      @sol_results.each do |r|
        expect(r[:group]).to be_nil
        expect(r[:_id]).to be_nil
        expect(r[:number]).to_not be_nil
        expect(r[:first_name]).to_not be_nil
        expect(r[:last_name]).to_not be_nil
        expect(r[:gender]).to_not be_nil
        expect(r[:secs]).to_not be_nil
        expect(r[:secs]).to be >= current_time
        current_time = r[:secs]
        counter = counter + 1        
      end
      expect(counter).to eq(@limit)
    end 
    it "supports offsets in gathering results" do 
      # get all results and then count off offset, items should be same
      test_values = solution.find_group_results(@group, 0, @limit).to_a
      expect(test_values[@offset]).to eq(@sol_results.first)
    end
  end 
end