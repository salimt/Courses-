require_relative '../config/environment'
require 'rails_helper'
require 'utils'

Mongo::Logger.logger.level = ::Logger::INFO


feature "Module 1 Summative ActiveRecord Tests" do 

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

    let!(:data_file) { "race_results.json" }

    before :each do 
        @db = Racer.mongo_client
        @db[:racers].drop
        @db[:racers].create
        hash = JSON.parse(File.read("race_results.json"))
        @db[:racers].insert_many(hash)
        @id = (Racer.all({number: 254}).first)[:_id]
        @racer = Racer.find(@id.to_s)        
    end

    context "rq01" do 
      it "Racer has the ActiveModel::Model mixin" do 
        expect(Racer).to include(ActiveModel::Model)
      end
    end

    context "rq02" do

      it "Racer implements an instance method called persisted?" do 
        expect(@racer).to respond_to(:persisted?)
      end

      it "instance method persisted? takes no parameters" do 
        expect(@racer.method(:persisted?).parameters.flatten.count).to eq 0
      end

      it "persisted? returns true if persisted and false if not" do
        expect(@racer.persisted?).to be true
        new_racer = Racer.new({first_name:"Jim", last_name:"Palmer", group:"70 to 79", gender:"M", number:1001, sec:2500})
        expect(new_racer.persisted?).to be false
        new_racer.save 
        expect(new_racer.persisted?).to be true
      end
    end

    context "rq03" do 
      it "Racer has an instance method for created_at" do 
        expect(@racer).to respond_to(:created_at)
      end

      it "created_at takes no parameters" do 
        expect(@racer.method(:created_at).parameters.flatten.count).to eq 0
      end

      it "Racer has an instance method for updated_at" do 
        expect(@racer).to respond_to(:updated_at)
      end

      it "updated_at takes no parameters" do 
        expect(@racer.method(:updated_at).parameters.flatten.count).to eq 0
      end
    end
end
