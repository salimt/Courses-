# lecture1_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3, Lecture 3 Setup and Collection Tests" do
	include Test_utils

    before :all do
        $continue = true
        if (Racer.collection.find(:_id=>111).count > 0) then
            Racer.find(111).delete
        end
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
    it "Racer class updates the updated_at field prior to upsert" do
        r0 = Racer.create(:_id=>111, :fn=>"TEST", :ln=>"CASE")
        update_time = r0.updated_at
        r1 = Racer.new(:_id=>111, :fn=>"UPDATED", :ln=>"TEST")
        expect(r1.upsert).to be true
        new_update_time = r1.updated_at
        expect(new_update_time).to_not be(update_time)
        Racer.find(111).destroy
    end

    it "Racer collection has nothing but records with firstname of Sally" do        
        expect(Racer.where(:first_name.ne=>"Sally").count).to eql 0
        expect(Racer.where(:first_name=>"Sally").count).to eql Racer.collection.find.count
    end
  end
end