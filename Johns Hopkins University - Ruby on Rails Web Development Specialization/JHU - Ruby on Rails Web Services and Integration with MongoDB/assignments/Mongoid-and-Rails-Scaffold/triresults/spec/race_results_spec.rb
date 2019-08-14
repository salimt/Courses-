# race_results.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'
require 'pp'

describe "Module #3 Summative: Implement Race / Results Cross-Collection" do
  include Test_utils

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
    setup_data_for_testing
  end

  context "rq01" do
    it "RaceRef class created" do
      expect(class_exists?("RaceRef"))
    end

    it "RaceRef class has fields for name, and date" do
      expect(RaceRef).to have_field(:n).with_alias(:name).of_type(String)
      expect(RaceRef.new).to respond_to(:n)    
      expect(RaceRef.new).to respond_to(:name)    
      expect(RaceRef).to have_field(:date).of_type(Date)
      expect(RaceRef.new).to respond_to(:date)
    end

    it "RaceRef class creates documents with all specified fields" do
      expect(race = Race.where(:name=>Test_utils::RACE_FIELDS[:name]).first).to_not be_nil
      expect(rr = RaceRef.new(race.attributes.symbolize_keys.slice(:_id, :n, :date))).to_not be_nil
      expect(rr.n).to eql Test_utils::RACE_FIELDS[:name]
      expect(rr.date).to eql Test_utils::RACE_FIELDS[:date]
      expect(rr.id).to eql race.id
    end
  end

  context "rq02" do
    it "Entrant has a 1:1 embedded relationship with RaceRef" do
      expect(Entrant).to embed_one(:race).of_type(RaceRef)     
      expect(RaceRef).to be_embedded_in(:entrant)    
    end 

    it "Entrant document has associated RaceRef embedded within" do
      expect(race = Race.where(:name=>Test_utils::RACE_FIELDS[:name]).first).to_not be_nil
      expect(entrant = Entrant.new).to_not be_nil
      expect(entrant.build_race(race.attributes.symbolize_keys.slice(:_id,:n,:date))).to_not be_nil
      expect(entrant.save).to be true
      expect(doc = entrant.attributes).to_not be_nil
      expect(doc["race"]).to_not be_nil
      expect(doc["race"]["_id"]).to eql race.id
      expect(doc["race"]["n"]).to eql Test_utils::RACE_FIELDS[:name]
      expect(doc["race"]["date"]).to eql race.attributes[:date]
    end
  end

  context "rq03" do
    before :each do
      setup_data_for_testing
      @race = Race.where(:name=>Test_utils::RACE_FIELDS[:name]).first
      @race2 = Race.create(:name=>"Oakland 10K", :date=>Date.current, :loc=>@race.loc)
      @entrant1 = Entrant.new(:bib=>1, :secs=>1100.23) {|r| \
        r.build_race(@race2.attributes.symbolize_keys.slice(:_id, :n, :date)); r.save }
      @entrant2 = Entrant.new(:bib=>2, :secs=>1200.23) {|r| \
        r.build_race(@race2.attributes.symbolize_keys.slice(:_id, :n, :date)); r.save }           
    end

    it "There is an M:1 relationship from Entrant to Race through RaceRef" do
      expect(RaceRef).to belong_to(:race).of_type(Race).with_foreign_key("_id")
      expect(Entrant.new).to respond_to(:the_race)
      expect((Entrant.new.method(:the_race).parameters.flatten - [:req, :opt]).count).to eq 0
      expect(Race).to have_many(:entrants).of_type(Entrant).with_foreign_key("race._id").with_dependent(:delete)
    end 

    it "Invoking race methods from Entrant will either reference local RaceRef or from a race document" do  
      expect(@entrant1.race.name).to eql "Oakland 10K"  # local reference from RaceRef
      expect(@entrant2.the_race.loc.city).to eql Test_utils::RACE_FIELDS[:city]
    end

    it "Queries from parent (race) collection can be made for entrants ordered by secs then bib ascending" do
      expect(bib_array = @race2.entrants.pluck(:bib)).to_not be_nil
      expect(bib_array.count).to eql 2
      expect(bib_array[0]).to be <= bib_array[1]
      expect(@race2.entrants.where(:bib=>1).first.secs).to eql 1100.23
      expect(@race2.entrants.where(:bib=>2).first.secs).to eql 1200.23
    end

    it "When a race is deleted, so are all the associated entrants" do
      expect(@race2.delete).to be true
      expect(Entrant.where(:id=>@entrant1.id).exists?).to be false
      expect(Entrant.where(:id=>@entrant2.id).exists?).to be false
    end
  end
end