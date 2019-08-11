# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Lecture 6 Validations and Constraints" do
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
    it "Racer class has validation for first_name and last_name" do
      expect(Racer).to validate_presence_of(:first_name)
      expect(Racer).to validate_presence_of(:last_name)
    end

    it "Creating a Racer without either a first or last name is not permitted" do
      r = Racer.new
      expect(r.save).to be false
      expect(r.errors.messages[:first_name][0]).to_not be_nil
      expect(r.errors.messages[:last_name][0]).to_not be_nil
      r = Racer.new(:first_name=>"cat")
      expect(r.save).to be false
      expect(r.errors.messages[:first_name]).to be_nil
      expect(r.errors.messages[:last_name][0]).to_not be_nil   
      r = Racer.new(:last_name=>"inhat")
      expect(r.save).to be false
      expect(r.errors.messages[:first_name][0]).to_not be_nil
      expect(r.errors.messages[:last_name]).to be_nil     
    end
  end

  context "rq02" do
    it "Entrant class has validation for that it belongs_to a Racer" do
      expect(Entrant).to validate_associated(:racer)
    end

    it "Entrant cannot be associated with an invalid Racer" do
      expect(Entrant.create(:racer=>Racer.new).validate).to be false
      expect(Entrant.create(:racer=>Racer.new).errors.messages[:racer]).to_not be_nil
      expect(Entrant.create(:racer=>Racer.new).racer.errors.messages[:first_name]).to_not be_nil
      expect(Entrant.create(:racer=>Racer.new).racer.errors.messages[:last_name]).to_not be_nil      
    end
  end

  context "rq03" do
    it "MedicalRecord class has validation for the presence of Racer" do
      expect(MedicalRecord).to validate_presence_of(:racer)
    end
 
    it "MedicalRecord is not valid without a Racer" do
      expect(MedicalRecord.create.validate).to be false
      expect(MedicalRecord.create.errors.messages).to_not be be_nil
      expect(MedicalRecord.create.errors.messages[:racer]).to_not be_nil
    end
  end

  context "rq04" do
    before :all do
      venue = Venue.create(:name=>"Flat Field")
      venue.contests.create(:name=>"FF10K")
    end

    it "Venue has a dependent foreign link to Contest" do
      expect(Venue).to have_many(:contests).with_dependent(:restrict)
    end

    it "Venue cannot be deleted if there is a reference to a Contest" do
      expect(venue = Venue.find_by(:name=>"Flat Field")).to_not be_nil
      expect{venue.destroy}.to raise_error(Mongoid::Errors::DeleteRestriction)
    end

    it "Venue can be deleted after relationship to Contest has been removed" do
      expect(venue = Venue.find_by(:name=>"Flat Field")).to_not be_nil
      expect(contest = venue.contests.first).to_not be_nil
      expect(venue.contests.delete contest).to_not be_nil
      expect(venue.destroy).to be true
    end
  end  

  context "rq05" do
    before :all do
      racer=Racer.create(:fn=>"Sally",:ln=>"Walden")
      racer.create_medical_record(:conditions=>["messy"])
    end

    it "Racer will cascade deletes to MedicalRecord" do
      expect(Racer).to have_one(:medical_record).with_dependent(:destroy)
    end

    it "MedicalRecord has a foreign key to Racer" do
      expect(r = Racer.find_by(:fn=>"Sally")).to_not be_nil
      expect(m = r.medical_record).to_not be_nil
      expect(m.racer_id).to eql r.id
    end

    it "Deleting a Racer also deletes associated MedicalRecord" do
      expect(r = Racer.find_by(:fn=>"Sally")).to_not be_nil
      expect(m = r.medical_record).to_not be_nil
      expect(MedicalRecord.where(:_id=>m.id.to_s).first).to_not be_nil
      expect(r.delete).to be true
      expect(MedicalRecord.where(:_id=>m.id.to_s).first).to be_nil
    end
  end
end