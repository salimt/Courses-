require_relative '../config/environment'
require 'rails_helper'
require 'geo_utils'

feature "Module #2 Show Place and Photo Tests", :type => :routing do
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

  #helper method to determine if two files are the same
  def files_same?(file1, file2) 
    if (File.size(file1) != File.size(file2)) then
      return false
    end
    f1 = IO.readlines(file1)
    f2 = IO.readlines(file2)
    if ((f1 - f2).size == 0) then
      return true
    else
      return false
    end
  end

  # check that TodoItem exists with fields and migration/db exists
  context "rq01" do
    before :all do    
      load "#{Rails.root}/db/seeds.rb"  
      file = File.open('./db/image1.jpg','rb')

      # make sure all places have photos linked for tests
      Place.all.each { |p| 
        if (p.photos.empty?) then
          photo = Photo.new 
          f = File.open("./db/image1.jpg",'rb')
          photo.contents = f
          id = photo.save
          photo.place = BSON::ObjectId(p.id)
          photo.save
        end
      }
      @test_place_id = Place.all.sample.id
    end

    context "Expected controller and paths exist" do
      it "must have expected controller and routes" do 
        expect(Dir.entries("./app/controllers")).to include("places_controller.rb")
        expect(Dir.entries("./app/views/places")).to include("index.html.erb")
        expect(Dir.entries("./app/views/places")).to include("edit.html.erb")
        expect(Dir.entries("./app/views/places")).to include("show.html.erb")
        expect(Dir.entries("./app/views/places")).to include("new.html.erb")
        expect(Dir.entries("./app/views/places")).to include("_form.html.erb")
        expect(:get => places_path).to route_to(:controller => "places", :action => "index")
        expect(:get => place_path(@test_place_id)).to route_to(:controller => "places", :action => "show", :id => "#{@test_place_id}")
      end
    end
  
    scenario "Query to URL returns the selected image" do 
      visit(places_path)
      expect(page.status_code).to eq(200)      
      expect(page).to have_content("Listing Places")
      expect(page).to have_link('Show')
      page.click_link("Show", :match => :first)
      expect(page.status_code).to eq(200)
      link_uri = URI.parse(current_url).request_uri
      cur_place_id = (link_uri.split("/"))[2]      
      expect(page).to have_content("Formatted address")
      photo_id = Photo.find_photos_for_place(cur_place_id).first[:_id]
      expect(page.find('img')['src']).to eq("/photos/#{photo_id}/show/").or eq("/photos/#{photo_id}/show")
    end
  end
end
