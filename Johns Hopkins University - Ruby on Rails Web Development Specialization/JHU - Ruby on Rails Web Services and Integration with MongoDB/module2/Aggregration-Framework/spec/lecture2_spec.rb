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
    it "Solution implements an instance method called racer_names" do
      expect(solution).to respond_to(:racer_names)
    end

    it "Instance method racer_names takes no parameters" do 
      expect(Solution.instance_method(:racer_names).parameters.flatten.count).to eq 0 
    end

    it "racer_names accesses all racers" do
      all_racers = racers.find()
      method_racers = solution.racer_names
      expect(all_racers.count).to eq(method_racers.count)
      method_racers.each do |r|
        expect(r[:first_name]).to_not be_nil
        expect(r[:last_name]).to_not be_nil
        expect(r[:_id]).to be_nil
        expect(r[:number]).to be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
      end
    end
  end
  
  context "rq02" do
    it "Solution implements an instance method called id_number_map" do
      expect(solution).to respond_to(:id_number_map)
    end

    it "Instance method id_number_map takes no parameters" do 
      expect(Solution.instance_method(:id_number_map).parameters.flatten.count).to eq 0 
    end

    it "id_number_map accesses all racers" do
      all_racers = racers.find()
      method_racers = solution.id_number_map
      expect(all_racers.count).to eq(method_racers.count)
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to be_nil
        expect(r[:_id]).to_not be_nil
        expect((racers.find(_id: r[:_id]).first)[:number]).to eq r[:number]
        expect(r[:number]).to_not be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
      end
    end
  end

context "rq03" do
    it "Solution implements an instance method called concat_names" do
      expect(solution).to respond_to(:concat_names)
    end

    it "Instance method concat_names takes no parameters" do 
      expect(Solution.instance_method(:concat_names).parameters.flatten.count).to eq 0 
    end

    it "concat_names accesses all racers" do
      all_racers = racers.find()
      method_racers = solution.concat_names
      expect(all_racers.count).to eq(method_racers.count)
      expect(method_racers).to be_a Mongo::Collection::View::Aggregation
      method_racers.each do |r|
        expect(r[:name]).to_not be_nil
        expect(r[:first_name]).to be_nil
        expect(r[:last_name]).to be_nil
        expect(r[:_id]).to be_nil
        expect(r[:number]).to_not be_nil
        expect(r[:gender]).to be_nil
        expect(r[:group]).to be_nil
        expect(r[:secs]).to be_nil
        expect(r[:name]).to include((racers.find(number: r[:number]).first)[:first_name])
        expect(r[:name]).to include((racers.find(number: r[:number]).first)[:last_name])
      end
    end
  end
end