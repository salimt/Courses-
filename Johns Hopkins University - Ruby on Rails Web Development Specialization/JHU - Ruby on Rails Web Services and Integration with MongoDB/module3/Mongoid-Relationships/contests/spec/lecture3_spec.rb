# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Lecture 3 1:M Embedded Tests" do
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

  context "rq01" do

    it "Contest embeds_many Entrants in a 1:M unidirectional relationship" do
      expect(Contest).to embed_many(:entrants).of_type(Entrant)    
    end

    it "Entrants is accessible from Contest" do
      expect(Contest.new).to respond_to(:entrants)
      expect(Contest.new.entrants).to be_a Array
      expect(Contest.new.entrants.count).to eql 0
    end
  end

  context "rq02" do

    it "Entrant is embedded in Contest in an M:1 bidirectional relationship" do
      expect(Entrant).to be_embedded_in(:contest).of_type(Contest)    
      expect(Entrant.new.embedded?).to be true
    end

    it "Contest is accessible from Entrant" do
      expect(Entrant.new).to respond_to(:contest)
      expect(Entrant.new.contest).to be_nil
    end  
  end

  context "rq03" do
    # This is linked to the changing state of test rq06 in lecture2_spec
    it "There is no longer a relationship (has_many) from Racer to Entrant" do
      expect(Racer.relations["races"]).to be_nil
    end
  end

  context "rq04" do
    before :all do
      Racer.delete_all
      Contest.delete_all
      Entrant.delete_all
      contest = Contest.create(:name=>"Boston 5K", :date=>Date.new(2015, 8, 1))
      ["one","two"].each {|lname| Racer.create(:fn=>"thing",:ln=>lname) }
      Racer.create(:fn=>"cat", :ln=>"inhat")
    end

    it "There is now an array of embedded entrants within each contest" do
      contest=Contest.first
      contest.entrants.create(:id=>1, :group=>"youth", :racer=>Racer.find_by(:ln=>"one"))
      contest.entrants.create(:id=>2, :group=>"youth", :racer=>Racer.find_by(:ln=>"two"))
      contest.entrants.create(:id=>0, :group=>"masters", :racer=>Racer.find_by(:fn=>"cat"))
      expect(e_list = Contest.first.attributes["entrants"]).to_not be_nil
      expect(e_list).to be_a Array
      expect(e_list.count).to be 3
      test = e_list.select{ |a| a[:_id] == 1}
      expect(test.first[:group]).to eql "youth"
    end
  end   

  context "rq05" do 
    before :all do
      Racer.delete_all
      Contest.delete_all
      Entrant.delete_all
    end    

      it "Racer Model has an instance method called races" do
        expect(Racer.new).to respond_to(:races)
      end
      
      it "races method takes no parameters" do 
        expect((Racer.new.method(:races).parameters.flatten - [:opt, :req]).count).to eq 0
      end

      it "races method returns a list of Entrants for racer in various Contests" do
        # create 5 contests that racer is in (hence five entrants)
        racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(2000,11,11))
        entrant_array = Array.new
        (1..5).each { |i| 
          c = Contest.create(:name=>"Contest #{i}", :date=>Date.new(2015, i, 1))
          entrant_array.push(c.entrants.create(:id=>i, :group=>"youth", :racer=>(Racer.find_by(:fn=>"cat"))))
        }
        # get entrants from Racer using races method
        result = racer.races
        expect(result).to_not be_nil
        expect(result).to be_a Array
        expect(result.count).to be entrant_array.count
        result.each { |r| 
          expect(r).to be_a Entrant
          expect(entrant_array).to include(r)
        }
      end
  end
end