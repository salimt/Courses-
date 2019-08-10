require_relative "../config/environment"
require 'rails_helper'

feature "Module 1 Summative Setup Tests" do 

    context "rq01" do
      it "Satisfies the structure of a rails application" do
        expect(File.exists?("Gemfile")).to be(true)
        expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
        expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
      end
    end  
end