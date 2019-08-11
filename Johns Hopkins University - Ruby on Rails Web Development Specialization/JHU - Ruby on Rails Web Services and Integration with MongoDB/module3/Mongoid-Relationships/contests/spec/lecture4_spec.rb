# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Lecture 4 1:1 Linked Tests" do
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
    it "MedialRecord class created" do
      expect(class_exists?("MedicalRecord"))
    end

    it "MedicalRecord class has field for conditions that is an Array" do
      expect(MedicalRecord).to have_field(:conditions).of_type(Array)
      expect(MedicalRecord.new).to respond_to(:conditions)
      c_array = Array.new
      (1..5).each { |i| c_array.push("c_#{i}") }
      mr = MedicalRecord.new(:conditions=>c_array)
      expect(mr.conditions).to_not be_nil
      expect(mr.conditions.count).to be c_array.count
      mr.conditions.each { |i| expect(c_array).to include i }
    end

    it "MedicalRecord documents are stored in the medical collection" do
      expect(MedicalRecord.collection.name).to eql ("medical")    
    end
  end

  context "rq02" do

    before :all do
      MedicalRecord.delete_all
      Racer.delete_all
      @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(2000, 11, 12))
    end

    it "MedicalRecord has a 1:1 linked uni-directional relationship to Racer" do
      expect(MedicalRecord).to belong_to(:racer).of_type(Racer)    
    end

    it "MedicalRecord object can be linked to Racer" do
      m = MedicalRecord.create(:conditions=>["A", "B"])
      m.racer = @racer
      expect(m.save).to be true
      expect(MedicalRecord.find_by(:_id=>m.id).attributes[:racer_id]).to eql @racer.id
      expect(MedicalRecord.find_by(:_id=>m.id).racer_id).to eql @racer.id
      expect(MedicalRecord.find_by(:_id=>m.id).racer.id).to eql @racer.id
    end  
  end

  context "rq03" do

    before :all do
      MedicalRecord.delete_all
      Racer.delete_all
      @racer = Racer.create(:fn=>"cat", :ln=>"inhat", :dob=>Date.new(2000, 11, 12))
    end

    it "Racer has the inverse 1:1 linked relationship to Medical" do
      expect(Racer).to have_one(:medical_record).of_type(MedicalRecord)    
    end

    it "MedicalRecord object can be linked to Racer" do
      c_array = ["A", "B"]
      m = MedicalRecord.create(:conditions=>c_array)
      m.racer = @racer
      expect(m.save).to be true
      expect(m.racer.medical_record.conditions).to eql c_array
      @racer.medical_record.destroy
      nracer = Racer.find_by()
      expect(nracer.medical_record).to be_nil
      n_array = ["C", "D"]
      nracer.create_medical_record(:conditions=>n_array)
      expect(mr2 = MedicalRecord.first.attributes).to_not be_nil
      expect(mr2[:racer_id]).to eql nracer.id
      expect(mr2[:conditions]).to eql n_array
    end 
  end
end