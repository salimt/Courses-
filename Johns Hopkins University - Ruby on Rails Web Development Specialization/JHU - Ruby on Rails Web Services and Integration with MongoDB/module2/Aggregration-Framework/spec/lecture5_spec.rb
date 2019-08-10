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
    it "Solution implements an instance method called avg_family_time" do
      expect(solution).to respond_to(:avg_family_time)
    end

    it "Instance method avg_family_time takes a single parameter for last_name" do 
      expect((Solution.instance_method(:avg_family_time).parameters.flatten - [:req]).count).to eq 1
      expect(Solution.instance_method(:avg_family_time).parameters.flatten).to include(:req)
      expect(Solution.instance_method(:avg_family_time).parameters.flatten).to_not include(:opt)
    end

    it "avg_family_time accesses all racers and groups output" do
      all_racers = racers.find()
      raw_avg = 0
      raw_number = 0
      raw_array = []
      all_racers.each do |ar|
        if ar[:last_name] == "JONES"
          expect(ar).to_not be_nil
          expect(ar[:secs]).to be >= 0
          raw_avg += ar[:secs].to_f
          raw_number += 1
          raw_array.push(ar[:number])
        end
      end
      raw_avg = raw_avg/raw_number

      method_racers = solution.avg_family_time("JONES")
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to be_nil
        expect(r[:number]).to be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
        expect(r[:_id]).to_not be_nil
        expect(r[:_id]).to eq "JONES"
        expect(r[:avg_time]).to_not be_nil
        expect(r[:avg_time]).to eq raw_avg
        expect(r[:numbers]).to_not be_nil
        expect(r[:numbers].count).to eq raw_array.count
        expect(r[:numbers].sort).to eq raw_array.sort
      end
    end
  end
  
  context "rq02" do
    it "Solution implements an instance method called number_goal" do
      expect(solution).to respond_to(:number_goal)
    end

    it "Instance method number_goal takes a single parameter for last_name" do 
      expect((Solution.instance_method(:number_goal).parameters.flatten - [:req]).count).to eq 1
      expect(Solution.instance_method(:number_goal).parameters.flatten).to include(:req)
      expect(Solution.instance_method(:number_goal).parameters.flatten).to_not include(:opt)
    end

    it "number_goal accesses all racers and groups output" do
      all_racers = racers.find()
      raw_avg = 0
      raw_number = 0
      raw_array = []
      all_racers.each do |ar|
        if ar[:last_name] == "JONES"
          expect(ar).to_not be_nil
          expect(ar[:secs]).to be >= 0
          raw_avg += ar[:secs].to_f
          raw_number += 1
          raw_array.push(ar[:number])
        end
      end
      raw_avg = raw_avg/raw_number

      method_racers = solution.number_goal("JONES")
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to_not be_nil
        expect(r[:last_name]).to eq "JONES"
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
        expect(r[:_id]).to be_nil
        expect(r[:avg_time]).to_not be_nil
        expect(r[:avg_time]).to eq raw_avg
        expect(r[:numbers]).to be_nil
        expect(r[:number]).to_not be_nil
      end
    end
  end
end
