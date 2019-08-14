# racer_results.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'
require 'pp'

describe "Module #3 Summative: Implement Racers / Results Cross-Collection" do
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
    race = Race.where(:name=>Test_utils::RACE_FIELDS[:name]).first
    @racer = Racer.where(:"info.fn"=>Test_utils::RACER_FIELDS[:fname], :"info.ln"=>Test_utils::RACER_FIELDS[:lname]).first
    race2 = Race.create(:name=>"Oakland 10K", :date=>Date.current, :loc=>race.loc)
    entrant = Entrant.new(:bib=>1, :secs=>1100.23) { |r|
      r.build_race(race2.attributes.symbolize_keys.slice(:_id, :n, :date))
      r.build_racer(@racer.info.attributes)
      r.save
    }
  end

  context "rq01" do
    it "Entrant has a bidrectional 1:1 embedded polymorphic relationship with RacerInfo" do
      expect(Entrant).to embed_one(:racer).of_type(RacerInfo)     
      expect(RacerInfo).to be_embedded_in(:parent)    
    end 

    it "Entrant document has associated RaceRef embedded within" do
      expect(@racer).to_not be_nil
      expect(entrant = Entrant.find_by(:bib=>1)).to_not be_nil
      expect(entrant.create_racer(@racer.info.attributes)).to_not be_nil
      expect(edoc = Entrant.find(entrant.id).attributes).to_not be_nil
      # document should have racer information embedded in entrant
      expect(edoc["racer"]).to_not be_nil
      expect(edoc["racer"]["fn"]).to eql Test_utils::RACER_FIELDS[:fname]
      expect(edoc["racer"]["ln"]).to eql Test_utils::RACER_FIELDS[:lname]
      expect(edoc["racer"]["g"]).to eql Test_utils::RACER_FIELDS[:gender]
      expect(edoc["racer"]["yr"]).to eql Test_utils::RACER_FIELDS[:byear]
      expect(edoc["racer"]["res"]["city"]).to eql Test_utils::RACER_FIELDS[:city]
      expect(edoc["racer"]["res"]["state"]).to eql Test_utils::RACER_FIELDS[:state]
      expect(edoc["racer"]["res"]["loc"]["coordinates"][0]).to eql Test_utils::RACER_FIELDS[:lon]
      expect(edoc["racer"]["res"]["loc"]["coordinates"][1]).to eql Test_utils::RACER_FIELDS[:lat]
    end

    it "Entrant is invalid if RacerInfo is invalid" do
      expect(entrant = Entrant.new).to_not be_nil
      expect(racer = entrant.build_racer).to_not be_nil
      expect(entrant.validate).to be false
      expect(entrant.errors).to_not be_nil
      expect(entrant.errors.messages).to_not be_nil
      expect(entrant.errors.messages[:racer].count).to eql 1
      expect(racer.errors).to_not be_nil
      expect(racer.errors.messages).to_not be_nil
      expect(racer.errors.messages[:first_name].count).to eql 1
      expect(racer.errors.messages[:last_name].count).to eql 1
      expect(racer.errors.messages[:gender].count).to eql 2
      expect(racer.errors.messages[:birth_year].count).to eql 2
    end
  end

  context "rq02" do
    it "Racer has a 1:M linked relationship with Entrant with foreign key in Entrant.RacerInfo" do
      expect(Racer).to have_many(:races).of_type(Entrant).with_foreign_key(:"racer.racer_id")   
      expect(Racer).to have_many(:races).with_dependent(:nullify).ordered_by(:"race.date".desc)      
    end

    it "Entrants can be retrieved from Racers via the Races they compete in" do
      expect(@racer).to_not be_nil
      expect(entrant = Entrant.find_by(:bib=>1)).to_not be_nil
      expect(entrant.create_racer(@racer.info.attributes)).to_not be_nil      
      expect(elist = @racer.races.to_a).to_not be_nil
      expect(elist.count).to eql 1
      expect(elist[0]).to be_a Entrant
      expect(elist[0].id).to eql entrant.id
    end

    it "When Racer is deleted, RacerInfo foreign key to Entrants are deleted" do
      expect(@racer).to_not be_nil
      r_id = @racer.id
      expect(entrant = Entrant.first).to_not be_nil
      e_id = (entrant.id)
      expect(entrant.create_racer(@racer.info.attributes)).to_not be_nil
      #foreign key exists after association
      expect(fk = entrant.racer.racer_id).to_not be_nil
      expect(@racer.delete).to be true
      expect{Racer.find(r_id)}.to raise_error(Mongoid::Errors::DocumentNotFound)
      expect(entrant = Entrant.find(e_id)).to_not be_nil
      #foreign key to RacerInfo should be nil
      expect(fk = entrant.racer.racer_id).to be_nil
    end
  end
end