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

  # verify that assignment.rb is set and environment is ready
  context "rq01" do
    it "Solution class has methods to access database and collections" do
      expect(Solution).to respond_to(:collection)
      expect(Solution.collection).to_not be_nil
      expect(Solution.collection).to be_a(Mongo::Collection)
    end
  end  

  context "rq02" do 
    it "Collection has expected data based on specific queries" do 
      result = racers.find.aggregate([ {:$group=>{:_id=>0, :count=>{:$sum=>1}}}]).first
      expect(result[:_id]).to be 0
      expect(result[:count]).to be > 0
      result = racers.find.aggregate([ {:$group=>{:_id=>'$group', :count=>{:$sum=>1}}}])
      expect(result).to_not be_nil
      expect(result.count).to be > 0
      result.each do |r| 
        expect(r[:_id].length).to be > 0
        expect(r[:count]).to be > 0
      end
    end
  end
end