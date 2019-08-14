# railscheck_spec.rb
require_relative '../config/environment'
require 'rails_helper'
require 'test_utils'

describe "Module #3 Summative: Rails Check" do
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

  context "rq00" do
    it "must have top level structure of a rails application" do
      expect(File.exists?("Gemfile")).to be(true)
      expect(Dir.entries(".")).to include("app", "bin", "config", "db", "lib", "public", "log", "test", "vendor")
      expect(Dir.entries("./app")).to include("assets", "controllers", "helpers", "mailers", "models", "views")        
    end
  end
end