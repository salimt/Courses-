# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Setup and Collection Tests" do
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

    before :all do
      Racer.collection.delete_many
    end

    it "must have top level structure of a rails application" do
      expect(File.exists?("Gemfile")).to be(true)
      expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
      expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
    end

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
      expect(Racer.count).to eq 0
    end
  end
end
