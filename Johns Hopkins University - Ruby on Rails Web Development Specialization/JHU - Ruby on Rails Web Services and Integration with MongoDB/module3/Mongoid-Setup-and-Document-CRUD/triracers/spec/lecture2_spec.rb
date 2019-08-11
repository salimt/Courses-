# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3, Lecture 2 Setup and Collection Tests" do
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
      let(:r) { Racer.new(:fn => "cat", :ln => "hat", :dob => Date.new(1957,3,12), :gender => "F") }

      it "Racer class created" do
        expect(class_exists?("Racer"))
      end
      it "Racer class has methods for mongo_client and collection" do
        expect(Racer).to respond_to(:mongo_client)
        expect(Racer.mongo_client).to_not be_nil
        expect(Racer.mongo_client).to be_a Mongo::Client
        expect(Racer).to respond_to(:collection)
        expect(Racer.collection).to_not be_nil
        expect(Racer.collection).to be_a Mongo::Collection
        expect(Racer.collection.name).to eq("racer1")
      end

      it "Racer includes Mongoid::Document mixin" do
        expect((class << r; self; end).included_modules).to include(Mongoid::Document)
      end
      
      it "Racer includes Mongoid::Timestamps mixin" do
        expect((class << r; self; end).included_modules).to include(Mongoid::Timestamps)
        expect(r).to respond_to(:created_at)
        expect(r).to respond_to(:updated_at)
        expect(r.created_at).to be_nil
        expect(r.updated_at).to be_nil
      end

      it "Racer class implements expected attributes" do 
        expect(r).to respond_to(:first_name)
        expect(r).to respond_to(:last_name)
        expect(r).to respond_to(:dob)
        expect(r).to respond_to(:gender)
        expect(r.fn).to eq "cat"
        expect(r.first_name).to eq "cat"
        expect(r.ln).to eq "hat"
        expect(r.last_name).to eq "hat"
        expect(r.dob.to_s).to include("1957-03-12")
        expect(r.date_of_birth.to_s).to include("1957-03-12")
        expect(r.gender).to eq "F"
        r.first_name = "Thing"
        r.last_name = "One"        
        expect(r.changed?).to be true
      end

      it "Racer class can be persisted to database" do
        expect(Racer.collection.find(:_id=>r.id).first).to be_nil
        expect(r.save).to be true
        expect(r.persisted?).to be true
        expect(r.changed?).to be false
        db_rec = Racer.collection.find(:_id=>r.id).first
        expect(db_rec[:fn]).to eq r.first_name
        expect(db_rec[:ln]).to eq r.last_name
        expect(db_rec[:dob]).to eq r.date_of_birth
        expect(db_rec[:gender]).to eq r.gender
      end

      it "Racer collection has the record input from the Rails console" do
        expect(Racer.collection.find(:dob=>Date.new(1957,1,1), :fn=>"Sally", :gender=>"F", :ln=>nil).first).to_not be_nil
      end 
    end
  end