# lecture2_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'
require 'pp'

describe "Module #3 Lecture 2 1:M Linked Tests" do
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

    it "Contest class created" do
      expect(class_exists?("Contest"))
    end

    it "Contest class has fields name and date" do
      expect(Contest.new).to respond_to(:name)
      expect(Contest.new).to respond_to(:date)
    end

    it "Contest class values are accessible through fields" do
      contest = Contest.new(:name=>"Summer Marathon", :date=>Date.new(2015, 8, 1))
      expect(contest.name).to eql "Summer Marathon"
      expect(contest.date).to eql Date.new(2015, 8, 1)  
    end
      
    it "Contest includes Mongoid::Timestamps::Updated mixin" do
      contest = Contest.new(:name=>"Summer Marathon", :date=>Date.new(2015, 8, 1))
      expect((class << contest; self; end).included_modules).to include(Mongoid::Timestamps::Updated)
      expect(contest).to respond_to(:updated_at)
      expect(contest.updated_at).to be_nil
    end
  end

  context "rq02" do

      before :all do
        Venue.delete_all
        Contest.delete_all
        v = Venue.create(:name=>"Boston")
        @v_id = v.id
        c = Contest.create(:name=>"Boston 5K", :date=>Date.new(2015, 5, 30))
        c.venue = v
        c.save
      end

      it "Has belongs_to relationship from Contest to Venue" do
        expect(Contest).to belong_to(:venue).of_type(Venue)
      end

      it "Contest must have a M:1 linked unidirectional to venue" do
        contest = Contest.find_by(:name=>"Boston 5K")
        expect(contest).to respond_to(:venue_id)
        expect(contest).to respond_to(:venue)
        expect(contest.venue_id).to_not be_nil
        expect(contest.venue).to_not be_nil
        expect(contest.venue_id).to eql @v_id
        expect(contest.venue.id).to eql @v_id
      end    
  end

  context "rq03" do
      
      before :all do
        Venue.delete_all
        Contest.delete_all
        v = Venue.create(:name=>"Boston")
        @v_id = v.id
        (0..9).each { |n|
          c = Contest.create(:name=>"Contest #{n}", :date=>Date.new(2015, 5, 30))
          c.venue = v
          c.save
        }
      end   

      it "Venue has has_many relationship back to Contest" do
        expect(Venue).to have_many(:contests).of_type(Contest)
      end

      it "Venue can navigate back to Contest" do
        v = Venue.find_by(:name=>"Boston")
        expect(v).to respond_to(:contests)
        expect(v.contests).to_not be_nil
        expect(v.contests.count).to eql 10
        test_list = v.contests.map{ |c| c.name }
        expect(test_list).to include("Contest 5")
        c = Contest.find_by(:name=>"Contest 5")
        v.contests.delete(c)
        c_up = Contest.find_by(:name=>"Contest 5")
        expect(c_up.venue_id).to be_nil
        expect(c_up.venue).to be_nil
        test_list1 = v.contests.map{ |x| x.name }
        expect(test_list1).to_not include("Contest 5")
      end

  end

  context "rq04" do
     it "Entrant class created" do
      expect(class_exists?("Entrant"))
    end

    it "Entrant class has fields for _id, name, group and secs" do
      expect(Entrant.new).to respond_to(:_id)
      expect(Entrant.new).to respond_to(:name)
      expect(Entrant.new).to respond_to(:group)
      expect(Entrant.new).to respond_to(:secs)
    end

    it "Entrant class values are accessible through fields" do
      entrant = Entrant.new(:_id=>1, :name=>"test entrant", :group=>"test group", :secs=>100.5)
      expect(entrant.id).to be 1
      expect(entrant.name).to eql "test entrant"
      expect(entrant.group).to eql "test group"
      expect(entrant.secs).to eq 100.5 
    end
  end   

  context "rq05" do 

    before :each do
      Racer.delete_all
      Entrant.delete_all
      begin
        Contest.delete_all
      rescue NameError
      end
      #@racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(1990, 10, 10))
    end

    it "Entrant has M:1 linked unidirectional relationship to racer" do
      expect(Entrant).to belong_to(:racer).of_type(Racer)
    end

    it "Can navigate from Entrant to Racer" do
      racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(1990, 10, 10)) 
      puts "Call Entrant.new"     
      entrant = Entrant.new(:id=>1, :racer=>racer, :group=>"Masters")

      begin
        contest = Contest.create(:name=>"contest", :date=>Date.new(2010,1,1))
      rescue NameError
        # do nothing
      end
         
      expect(entrant.name).to be_nil
      expect(entrant.id).to be 1
      expect(entrant.group).to eql "Masters"
      expect(entrant.racer_id).to eql racer.id
      begin
        contest.entrants << entrant
      rescue NameError
      end
      entrant.save

      e_db = nil
      begin
        contest = Contest.where('entrants._id' => 1).first
        e_db = contest.entrants.select{|e| e._id == 1}.first
      rescue NameError
        e_db = Entrant.find_by(:id=>1)
      end
      expect(e_db.racer_id).to eql racer.id
      expect(e_db.name).to eql ("#{racer.last_name}, #{racer.first_name}")
    end
  end

  context "rq06" do
    before :all do
      Racer.delete_all
      Entrant.delete_all
      @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(1990, 10, 10))
    end

    # in rq06 test for has_many as directed in README, but in subsequent
    # steps relationship becomes embedded, so we change up tests so they 
    # pass under new direction
    it "There is a relationship (has_many or embedded) from Racer to Entrant" do
      if Racer.relations["races"] != nil
        expect(Racer.relations["races"].macro).to be :has_many
        expect(Racer.relations["races"].class_name).to eql "Entrant"
        # Test that Entrants can be obtained from Racer through races
        (2..3).each {|index| Entrant.create(:id=>index, :racer=>@racer)}
        @racer.races.map { |r| 
          expect(r).to be_a Entrant
          expect(r.racer_id).to eql @racer.id
          expect(r.racer.id).to eql @racer.id
        } 
      elsif Racer.instance_methods.include? :races
        # fill in later for embeds
      else
        expect(Racer.relations["races"] != nil || Racer.instance_methods.include?(:races)).to be true
      end
    end
  end
end
