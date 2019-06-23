require 'rspec'
require 'rspec/its'
require_relative '../module2_lesson3_formative.rb'

describe "lesson3" do

  subject { Person }

  context "check results" do
    p1 = Person.new("Ivana", "Trump")
    p2 = Person.new("Eric", "Trump")
    p3 = Person.new("Melania", "Trump")
    p4 = Person.new("Marla", "Maples")

    it "unexpected search result" do
      expect(Person.search("Trump").size).to be == 3
    end 
  end

  context "check instance properties" do
    subject(:john) { Person.new("Chris", "Christie") }

    it "missing first_name" do
      is_expected.to respond_to(:first_name) 
    end 

    it "missing last_name" do
      is_expected.to respond_to(:last_name) 
    end 

  end

  context "check class properties" do

    it "missing search" do
      is_expected.to respond_to(:search) 
    end 
  end
end
