require_relative '../config/environment'
require 'rails_helper'

Mongo::Logger.logger.level = ::Logger::INFO

feature "Module 4 Pagination Test", :type => :routing do
    include Capybara::DSL

    before :all do 
        @db = Racer.mongo_client
        @db[:racers].drop
        hash = JSON.parse(File.read("race_results.json"))
        @db[:racers].insert_many(hash)
        @num_records = Racer.collection.count
    end

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

    context "rq01" do 
        it "Racer implements a class method called paginate" do 
            expect(Racer).to respond_to(:paginate)
        end

        it "paginate method accepts a hash as an input parameter" do 
            expect((Racer.method(:paginate).parameters.flatten - [:opt, :req]).count).to eq 1
            expect(Racer.method(:paginate).parameters.flatten).to_not include (:opt)
        end

        it "paginate methods returns a Collection and supports default page and per_page value" do 
            page_result = Racer.paginate({})
            expect(page_result).to be_a(WillPaginate::Collection)
            expect(page_result.first).to be_a(Racer)
            expect(page_result.current_page).to eq 1
            expect(page_result.per_page).to eq 30
        end

        it "paginate method sorts results by number ascending and limits results by parameter" do 
            page_result = Racer.paginate(page:1, per_page:25)
            expect(page_result.count).to be <= 25
            expect(page_result.total_entries).to be > 25
            nval = -1
            page_result.each do |r|
                expect(r.number).to be >= nval
                nval = r.number
            end
        end
    end

    context "rq02" do 
        scenario "Racer list view returns results limited by page and number through pagination" do 
            visit("#{racers_path}?page=1&per_page=5")
            expect(page).to have_link('Show', count:5)
            expect(page).to have_link('Edit', count:5)
            expect(page).to have_link('Destroy', count:5)
            expect(page.all('//tbody/tr').count).to eq 5
        end
    end

    context "rq03" do
        scenario "List summary limited to 8 lists with pagination" do
            racers_per_page = 25
            local_page = Racer.paginate(page:1, per_page:racers_per_page)
            visit("#{racers_path}?page=1&per_page=#{racers_per_page}")
            (1..local_page.total_pages-1).each do |p|
                expect(page.all('//tbody/tr').count).to be <= racers_per_page
                expect(page).to have_link("#{p+1}", :href => "/racers?page=#{p+1}&per_page=#{racers_per_page}")
                click_link("#{p+1}", :href => "/racers?page=#{p+1}&per_page=#{racers_per_page}")
            end
        end
    end
end