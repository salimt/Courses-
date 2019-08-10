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
    it "Solution implements an instance method called group_times" do
      expect(solution).to respond_to(:group_times)
    end

    it "Instance method group_times takes no parameters" do 
      expect(Solution.instance_method(:group_times).parameters.flatten.count).to eq 0 
    end

    it "group_times accesses all racers and groups output" do
      all_racers = racers.find()
      method_racers = solution.group_times
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      total_count = 0
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
        total_count += r[:runners]
        if r[:_id][:gender]=="M" && r[:_id][:age]=="masters"
          expect(r[:runners]).to eq 59
          expect(r[:fastest_time]).to eq 1371
        end
      end
      expect(total_count).to eq all_racers.count
    end
  end
  
  context "rq02" do
    it "Solution implements an instance method called group_last_names" do
      expect(solution).to respond_to(:group_last_names)
    end

    it "Instance method group_last_names takes no parameters" do 
      expect(Solution.instance_method(:group_last_names).parameters.flatten.count).to eq 0 
    end

    it "group_times accesses all racers and groups output" do
      all_racers = racers.find()
      method_racers = solution.group_last_names
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      total_count = 0
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
        expect(r[:last_names]).to_not be_nil
        total_count += r[:last_names].count
        if r[:_id][:gender]=="M" && r[:_id][:age]=="masters"
          expect(r[:last_names].count).to eq 59
        end
      end
      expect(total_count).to eq all_racers.count
    end
  end

  context "rq03" do
    it "Solution implements an instance method called group_last_names_set" do
      expect(solution).to respond_to(:group_last_names_set)
    end

    it "Instance method group_last_names_set takes no parameters" do 
      expect(Solution.instance_method(:group_last_names_set).parameters.flatten.count).to eq 0 
    end

    it "group_times accesses all racers and groups output" do
      all_racers = racers.find()
      method_racers = solution.group_last_names_set
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      total_count = 0
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
        expect(r[:last_names]).to_not be_nil
        total_count += r[:last_names].count
        if r[:_id][:gender]=="M" && r[:_id][:age]=="masters"
          expect(r[:last_names].count).to eq 58
        end
      end
      expect(total_count).to be <= all_racers.count
    end
  end
end