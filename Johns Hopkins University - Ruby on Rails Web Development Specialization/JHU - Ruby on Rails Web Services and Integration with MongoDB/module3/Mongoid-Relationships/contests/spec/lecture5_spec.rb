# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Lecture 5 M:M Linked Tests" do
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
    it "Judge class created" do
      expect(class_exists?("Judge"))
    end

    it "Judge class has String fields for first name and last name" do
      expect(Judge).to have_field(:first_name).of_type(String)
      expect(Judge.new).to respond_to(:first_name)
      expect(Judge).to have_field(:last_name).of_type(String)
      expect(Judge.new).to respond_to(:last_name)    
      first = "cat"
      last = "inhat"
      judge = Judge.new(:first_name=>first, :last_name=>last)
      expect(judge.first_name).to eql first
      expect(judge.last_name).to eql last  
    end
  end

  context "rq02" do

    before :all do
      Contest.delete_all
      Judge.delete_all
      (1..10).each { |n| 
        Contest.create(:name=>"Contest #{n}", :date=>Date.new(2015, n, 1))
      }
    end

    it "Judge has an M:M linked uni-directional relationship to Contest" do
      expect(Judge).to have_and_belong_to_many(:contests).of_type(Contest)    
    end

    it "Contests can be associated with Judges" do
      judge = Judge.create(:first_name=>"Judy")
      Contest.all.each { |c|
        judge.contests.push(c)
      }
      db_judge = Judge.first.attributes
      expect(db_judge[:contest_ids]).to be_a Array      
      expect(db_judge[:contest_ids].count).to be 10
      db_judge[:contest_ids].each { |c|
        expect(c).to be_a BSON::ObjectId
      }
    end  
  end

  context "rq03" do

    before :all do
      Contest.delete_all
      Judge.delete_all
      (1..1).each { |n| 
        Contest.create(:name=>"Contest #{n}", :date=>Date.new(2015, n, 1))
        Judge.create(:first_name=>"Judge #{n}")
      }
    end

    it "Contest has a reverse M:M linked relationship to Judge" do
      expect(Contest).to have_and_belong_to_many(:judges).of_type(Judge)    
    end

    it "Contests can be associated with Judges" do
      judge = Judge.first
      contest = Contest.first
      contest.judges << judge
      expect(Contest.first.attributes[:judge_ids][0]).to eql judge.id
      expect(Judge.first.attributes[:contest_ids]).to_not be_nil
      expect(Judge.first.attributes[:contest_ids][0]).to_not be_nil
      expect(Judge.first.attributes[:contest_ids][0]).to eql contest.id
    end  
  end
end