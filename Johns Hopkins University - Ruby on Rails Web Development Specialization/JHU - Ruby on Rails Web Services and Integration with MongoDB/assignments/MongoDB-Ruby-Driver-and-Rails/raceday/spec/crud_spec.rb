require_relative "../config/environment"
require 'rails_helper'
require 'mongo'
require 'utils'
Mongo::Logger.logger.level = ::Logger::INFO


feature "Module 1 Summative - CRUD Model Tests" do 

    !let(:data_file) { "race_results.json" }

    before :each do 
        @db = Racer.mongo_client
        @db[:racers].drop
        hash = JSON.parse(File.read(data_file))
        @db[:racers].insert_many(hash)
        @num_records = Racer.collection.count
    end

    context "rq01" do
        it "Racer implements a class method called all" do 
            expect(Racer).to respond_to(:all)
        end

        it "class method all takes 4 optional parameters and no required parameters" do 
            expect((Racer.method(:all).parameters.flatten - [:opt]).count).to eq 4
            expect(Racer.method(:all).parameters.flatten).to_not include (:req)
        end

        it "class method all default parameters return all records sorted by number ascending" do 
            results = Racer.all
            expect(results.count).to eq(@num_records)
            num = -1
            results.each do |r|
                expect(r[:number]).to be > num
                num = r[:number]
            end
        end

        it "class method all returns filtered records according to user parameters" do 
            result = Racer.all.to_a
            filter_count = 0
            result.each do |ar|
                if ar[:gender] == "F" && ar[:group] == "50 to 59"
                    filter_count += 1
                end
            end
            result = Racer.all({group:"50 to 59", gender:"F"}, {last_name:-1},0, nil).to_a
            expect(result.count).to eq(filter_count)

            result = Racer.all({group:"50 to 59", gender:"F"}, {last_name:-1},0,10).to_a
            cur_lname = "ZZZZZZZZZZ"
            count = 0
            result.each do |r|
                expect(r[:group]).to eq("50 to 59")
                expect(r[:gender]).to eq("F")
                expect(r[:last_name]).to be < cur_lname
                cur_lname = r[:last_name]
                count += 1
            end
            expect(count).to eq(10)
            result2 = Racer.all({group:"50 to 59", gender:"F"}, {last_name:-1},3,10).to_a
            expect(result2.first).to eq(result[3])
        end
    end

    context "rq02" do 
        it "Racer has accessors for id, number, first_name, last_name, gender, group and secs" do  
            racer = Racer.new
            expect(racer).to respond_to(:id)
            expect(racer).to respond_to(:number)
            expect(racer).to respond_to(:first_name)
            expect(racer).to respond_to(:last_name)
            expect(racer).to respond_to(:gender)
            expect(racer).to respond_to(:group)
            expect(racer).to respond_to(:secs)
        end
    end

    context "rq03" do  
        it "Racer has an initializer of the class using racers document keys" do 
            dbRacer = Racer.all.find.first
            racer_obj = Racer.new(dbRacer)
            expect(racer_obj.id).to_not be_nil
            expect(racer_obj.id).to eq(dbRacer[:_id].to_s)
            expect(racer_obj.number).to_not be_nil
            expect(racer_obj.number).to eq(dbRacer[:number])
            expect(racer_obj.first_name).to_not be_nil
            expect(racer_obj.first_name).to eq(dbRacer[:first_name])            
            expect(racer_obj.last_name).to_not be_nil
            expect(racer_obj.last_name).to eq(dbRacer[:last_name])
            expect(racer_obj.gender).to_not be_nil
            expect(racer_obj.gender).to eq(dbRacer[:gender])
            expect(racer_obj.group).to_not be_nil
            expect(racer_obj.group).to eq(dbRacer[:group])            
            expect(racer_obj.secs).to_not be_nil
            expect(racer_obj.secs).to eq(dbRacer[:secs])            
        end
    end

    context "rq04" do 
        it "Racer implements a class method called find" do 
            expect(Racer).to respond_to(:find)
        end

        it "class method find takes a single id parameter" do 
            expect((Racer.method(:find).parameters.flatten - [:opt, :req]).count).to eq 1
            expect(Racer.method(:find).parameters.flatten).to_not include (:opt)
        end

        it "find method accepts either a BSON::ObjectId or String" do 
            data_record = Racer.collection.find(number: 300).first
            idval = data_record[:_id]
            expect(acc_record = Racer.find(idval)).to_not be_nil
            expect(acc_record).to be_a(Racer)
            expect(acc_record2 = Racer.find(idval.to_s)).to_not be_nil
            expect(acc_record2).to be_a(Racer)
        end

        it "find method returns racer document represented by specified id" do 
            data_record = Racer.collection.find(number: 300).first
            idval = data_record[:_id]
            acc_record = Racer.find(idval)
            expect(acc_record).to be_a(Racer)
            expect(acc_record.id).to eq(data_record[:_id].to_s)
            expect(acc_record.number).to eq(data_record[:number])
            expect(acc_record.first_name).to eq(data_record[:first_name])
            expect(acc_record.last_name).to eq(data_record[:last_name])
            expect(acc_record.gender).to eq(data_record[:gender])
            expect(acc_record.group).to eq(data_record[:group])
            expect(acc_record.secs).to eq(data_record[:secs])
            bad_id = "563daabbe301fffffff00999"
            expect(Racer.find(bad_id)).to be_nil
        end
    end

    context "rq05" do 
        let (:racer) { Racer.new({ number: 2000, first_name: "Bill", last_name: "Gates", gender: "M", group: "50 to 59", secs: 5000 }) }

        it "Racer implements an instance method called save" do 
            expect(racer).to respond_to(:save)
        end

        it "Method save takes no parameters" do 
            expect(racer.method(:save).parameters.flatten.count).to eq 0
        end

        it "save method inserts racer into database and returns with id" do
            racer.save()
            expect(racer.id).to_not be_nil
            db_result = Racer.collection.find(_id: BSON::ObjectId.from_string(racer.id)).first
            expect(racer.first_name).to eq db_result[:first_name]
            expect(racer.last_name).to eq db_result[:last_name]
            expect(racer.number).to eq db_result[:number]
            expect(racer.gender).to eq db_result[:gender]
            expect(racer.group).to eq db_result[:group]
            expect(racer.secs).to eq db_result[:secs]
        end 
    end

    context "rq06" do 
        before :each do
            db_rec = Racer.collection.find(number: 300).first
            @id = db_rec[:_id].to_s
            @racer = Racer.find(@id)
        end

        it "Racer implements an instance method called update" do 
            expect(@racer).to respond_to(:update)
        end

        it "Method update takes a single hash as a parameters" do 
            expect((@racer.method(:update).parameters.flatten - [:opt, :req]).count).to eq 1
            expect(@racer.method(:update).parameters.flatten).to_not include(:opt)
        end

        it "update method updates object with supplied hash and overwrites other parameters" do 
            old_racer = @racer
            # first test racer for validity
            @racer.update(first_name:"thing", last_name:"one", group:"15 to 19", number: old_racer.number, secs: old_racer.secs)
            expect(@racer.first_name).to eq "thing"
            expect(@racer.last_name).to eq "one"
            expect(@racer.group).to eq "15 to 19"
            expect(@racer.id).to eq(@id)
            expect(@racer.gender).to be_nil
            expect(@racer.secs).to eq old_racer.secs
            expect(@racer.number).to eq old_racer.number
            # now that racer is valid, make sure database is expected to be the same
            db_test_rec = Racer.collection.find(_id: BSON::ObjectId.from_string(@id)).first
            expect(db_test_rec).to_not be_nil
            expect(db_test_rec[:first_name]).to eq(@racer.first_name)
            expect(db_test_rec[:last_name]).to eq(@racer.last_name)
            expect(db_test_rec[:gender]).to eq(@racer.gender)
            expect(db_test_rec[:number]).to eq(@racer.number)
            expect(db_test_rec[:group]).to eq(@racer.group)
            expect(db_test_rec[:secs]).to eq(@racer.secs)
        end
    end

    context "rq07" do 
        before :each do
            db_rec = Racer.collection.find(number: 350).first
            @id = db_rec[:_id].to_s
            @racer = Racer.find(@id)
        end

        it "Racer implements an instance method called destroy" do 
            expect(@racer).to respond_to(:destroy)
        end

        it "Method destroy takes no parameters" do 
            expect((@racer.method(:destroy).parameters.flatten).count).to eq 0
        end        

        it "destroy method deletes a document from the database" do 
            expect(@racer).to_not be_nil
            expect(@racer.first_name).to_not be_nil
            @racer.destroy
            next_racer = Racer.find(@id)
            expect(next_racer).to be_nil           
        end
    end
end
