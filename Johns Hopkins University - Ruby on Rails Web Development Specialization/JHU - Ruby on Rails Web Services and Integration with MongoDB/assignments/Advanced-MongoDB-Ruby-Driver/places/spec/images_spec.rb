require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'

feature "Module #2 Image Loading Tests", :type => :routing do
    include Capybara::DSL


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

  # check that TodoItem exists with fields and migration/db exists
  context "rq01" do
    before :all do    
      load "#{Rails.root}/db/seeds.rb"  
      @test_photo_id = Photo.all.sample.id
    end

    context "Expected controller and paths exist" do
      it "must have expected controller and routes" do 
        expect(Dir.entries("./app/controllers")).to include("photos_controller.rb")
        expect(:get => photos_show_path(@test_photo_id)).to route_to(:controller => "photos", :action => "show", :id => "#{@test_photo_id}")
      end
    end
  
    scenario "Query to URL returns the selected image" do 
      visit(photos_show_path(@test_photo_id))
      expect(page.status_code).to eq(200)
      expect(page.response_headers['Content-Type']).to eq('image/jpeg')
      expect((page.response_headers['Content-Length']).to_i).to be > 0
    end
  end
end
