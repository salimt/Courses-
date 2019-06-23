require_relative "../module3_1_assignment"
require 'rspec'
require 'rspec/its'
require 'webmock/rspec'

describe Recipe do
  # "it" in the tests below is Recipe class
  subject { Recipe }

  # It should have a method called "for"
  it { is_expected.to respond_to(:for) } 

  specify "Environment variable FOOD2FORK_KEY is set" do
    expect(ENV["FOOD2FORK_KEY"]).to_not be_nil
  end

  # It should have "key" in its "default_params" (the developer key)
  its(:default_params) { is_expected.to include :key }

  # key should be set with the value of the environment variable FOOD2FORK_KEY
  specify "default_params[:key] equals Environment variable FOOD2FORK_KEY" do 
    expect(subject.default_params[:key]).to eq ENV["FOOD2FORK_KEY"]
  end

  # It should have "base_uri" set properly
  its(:base_uri) { is_expected.to include "http://food2fork.com/api" }

  # Specific search for "chocolate"
  context "Chocolate Search" do
    before :each do
      query = Recipe.default_params.merge({"q" => "chocolate"})
      stub_request(:get, Recipe.base_uri + "/search").
         with(query: query).
         to_return(body: File.read('chocolate_recipes.json'), status: 200, headers: {'Content-Type' => 'application/json'})
    end

    subject{ Recipe.for("chocolate") }

    it { is_expected.to be_an Array }

    # Chocolate Search should give back 30 results
    its(:size) { is_expected.to eq 30 }

    # Each element should contain title, f2f_url, social_rank and image_url
    its(:sample) { is_expected.to have_key "title"}
    its(:sample) { is_expected.to have_key "f2f_url"}
    its(:sample) { is_expected.to have_key "social_rank"}
    its(:sample) { is_expected.to have_key "image_url"}
  end

end
