require_relative "../config/environment"
require 'rails_helper'
require 'mongo'
require 'utils'
Mongo::Logger.logger.level = ::Logger::INFO


feature "Module 1 Summative - Model Connection Tests" do 

    !let(:data_file) { "race_results.json" }

    context "rq01" do
        it "Racer class created" do
          expect(Utils.class_exists?("Racer"))
        end

        it "Racer implements a class method called mongo_client" do 
            expect(Racer).to respond_to(:mongo_client)
            db = Racer.mongo_client
            expect(db).to be_a(Mongo::Client)
        end

        it "Racer implements a class method called collection to access collection racers" do 
            expect(Racer).to respond_to(:collection)
            col = Racer.collection            
            expect(col).to_not be_nil
            expect(col).to be_a(Mongo::Collection)
            expect(col.name).to eq("racers")
        end
    end

    context "rq02" do 
        before :all do  
            @db = Racer.mongo_client
            @db[:testspec].drop
        end

        after :all do 
            @db[:testspec].drop
        end

        it "There is a file called race_results.json with test data" do 
            expect(File).to exist(data_file)
            hash = JSON.parse(File.read(data_file))
            expect(hash).to_not be_nil
            @db[:testspec].insert_many(hash)
            expect(@db[:testspec].count).to eq(1000)
        end
    end
end