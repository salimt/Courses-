require_relative "../config/environment"
require 'rails_helper'
require 'utils'

feature "Module 1 Summative Mongoid Setup Tests" do 

	let!(:config_file) { "config/mongoid.yml" }

    context "rq02" do
    	it "has a mongoid configuration file" do
    		expect(File).to exist( config_file )
    	end	

    	it "mongoid configuration file has updated db server definition" do
    		h = Utils.checkMongoidConfigFile(".") 
    		expect(h["development"]).to_not be_nil
    		expect(h["development"]).to match(/database: +raceday_development/)
    		expect(h["development"]).to match(/hosts: +- +localhost:27017/)    		
    		expect(h["test"]).to_not be_nil
    		expect(h["test"]).to match(/database: +raceday_test/)    	
    		expect(h["test"]).to match(/hosts: +- +localhost:27017/)    			
    	end  
    end

    context "rq03" do 
    	it "has config/applicaiton.rb setup for mongoid in rails" do
    		expect(File).to exist("config/application.rb")
    		expect(Utils.fileHasText("config/application.rb", "Mongoid.load! +( +'./config/mongoid.yml' +)"))
    		expect(Utils.fileHasText("config/application.rb", "config.generators +{ +|g| +g.orm +:mongoid +}"))    		
    	end
    end

    context "rq04" do 
    	it "should be able to establish a connection with Mongoid::Clients.default" do 
    		expect(Mongoid::Clients.default).to_not be_nil
    	end
    end
end