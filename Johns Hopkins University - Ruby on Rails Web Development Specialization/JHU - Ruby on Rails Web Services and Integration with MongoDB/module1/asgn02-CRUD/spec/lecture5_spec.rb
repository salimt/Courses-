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

  before :all do
    client = Mongo::Client.new(ENV['MONGO_URL'] ||= MONGO_URL)
    db = client.use(ENV['MONGO_DATABASE'] ||= MONGO_DATABASE) 
    @race_col = db[ENV['RACE_COLLECTION'] ||= RACE_COLLECTION] 
  end

  # test for presence of instance method clear_collection
  context "rq01" do
    it "Solution implements an instance method called update_racer" do
      expect(solution).to respond_to(:update_racer)
    end
    it "update_racer accepts a hash or racer properties" do
      expect((Solution.instance_method(:update_racer).parameters.flatten - [:opt, :req]).count).to eq(1)
    end
    it "update_racer finds a racer associated with given _id property and updates fields with given hash" do
      hval = @race_col.find(number:300).first
      hval[:first_name] = 'Donald'
      hval[:last_name] = 'Duck'
      hval[:group] = '30 to 39'
      hval[:gender] = 'M'
      hval[:secs] = 3000

      item = solution.update_racer(hval)
      expect(item).to be_a(Mongo::Operation::Result)
      expect(item.first[:n]).to eq(1)

      # confirm object in mongo has updated fields
      expect(@race_col.find(_id: hval[:_id]).first).to eq(hval)
    end
  end 

  context "rq02" do
    it "Solution implements an instance method called add_time" do
      expect(solution).to respond_to(:add_time)
    end
    it "add_time accepts racer number and amount of time in seconds" do
      expect((Solution.instance_method(:add_time).parameters.flatten - [:opt, :req]).count).to eq(2)
    end
    it "add_time finds a racer by number and increments time without retrieving document" do
      delta = 100
      orig = @race_col.find(number:300).first
      orig_time = orig[:secs]
      item = solution.add_time(300, delta)
      expect(item).to be_a(Mongo::Operation::Result)
      expect(item.first.keys).to_not include(:_id, :first_name, :last_name, :gender, :group, :secs)
      expect(@race_col.find(number:300).first[:secs]).to eq(orig_time + delta)
    end
  end   
end
