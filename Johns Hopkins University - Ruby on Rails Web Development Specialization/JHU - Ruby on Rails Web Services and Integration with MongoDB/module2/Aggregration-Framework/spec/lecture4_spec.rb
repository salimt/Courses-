require_relative "../assignment"
require 'rspec'
require 'mongo'

Mongo::Logger.logger.level = ::Logger::INFO

describe Solution do
  subject(:solution) { Solution.new }
  let!(:racers) { Solution.collection }

  before :all do
    $continue = true
    Solution.reset("race_results.json")    
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

  context "rq01" do
    it "Solution implements an instance method called groups_faster_than" do
      expect(solution).to respond_to(:groups_faster_than)
    end

    it "Instance method groups_faster_than takes a required single time parameter" do 
      expect((Solution.instance_method(:groups_faster_than).parameters.flatten - [:opt, :req]).count).to eq 1 
      expect(Solution.instance_method(:groups_faster_than).parameters.flatten).to include(:req)
      expect(Solution.instance_method(:groups_faster_than).parameters.flatten).to_not include(:opt)
    end

    it "groups_faster_than accesses all racers and groups output" do
      all_racers = racers.find()
      method_racers = solution.groups_faster_than(1280)
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to be_nil
        expect(r[:number]).to be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
        expect(r[:_id]).to_not be_nil
        expect(r[:_id][:age]).to_not be_nil
        expect(r[:_id][:gender]).to_not be_nil
        expect(r[:runners]).to_not be_nil
        expect(r[:fastest_time]).to_not be_nil
        expect(r[:fastest_time]).to be <= 1280
      end
    end
  end
  
  context "rq02" do
    it "Solution implements an instance method called age_groups_faster_than" do
      expect(solution).to respond_to(:age_groups_faster_than)
    end

    it "Instance method age_groups_faster_than takes a required single time parameter" do 
      expect((Solution.instance_method(:age_groups_faster_than).parameters.flatten - [:opt, :req]).count).to eq 2 
      expect(Solution.instance_method(:age_groups_faster_than).parameters.flatten).to include(:req)
      expect(Solution.instance_method(:age_groups_faster_than).parameters.flatten).to_not include(:opt)
    end

    it "groups_faster_than accesses all racers and groups output" do
      all_racers = racers.find()
      method_racers = solution.age_groups_faster_than("masters", 1280)
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to be_nil
        expect(r[:number]).to be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
        expect(r[:_id]).to_not be_nil
        expect(r[:_id][:age]).to_not be_nil
        expect(r[:_id][:age]).to eq("masters")
        expect(r[:_id][:gender]).to_not be_nil
        expect(r[:runners]).to_not be_nil
        expect(r[:fastest_time]).to_not be_nil
        expect(r[:fastest_time]).to be <= 1280
        expect(r[:runners]).to eq 58
        expect(r[:fastest_time]).to eq 1264
      end
    end
  end
end