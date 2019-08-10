require_relative "../assignment"
require 'rspec'
require 'mongo'

Mongo::Logger.logger.level = ::Logger::INFO

describe Solution do
  subject(:solution) { Solution.new }
  let!(:MONGO_URL) { 'mongodb://localhost:27017' }
  let!(:MONGO_DATABASE ) { 'test' }
  let!(:RACE_COLLECTION ) { 'race1' }

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

  before :all do
    client = Mongo::Client.new(ENV['MONGO_URL'] ||= MONGO_URL)
    db = client.use(ENV['MONGO_DATABASE'] ||= MONGO_DATABASE) 
    @race_col = db[ENV['RACE_COLLECTION'] ||= RACE_COLLECTION] 
  end


  # test for presence of instance method clear_collection
  context "rq01" do
    it "Solution implements instance method find_between" do
      expect(solution).to respond_to(:find_between)
    end
    it "find_between accepts a min and max value" do
      expect((Solution.instance_method(:find_between).parameters.flatten - [:opt, :req]).count).to eq(2)
    end
    it "find_between finds all race results with a time between input parameter values (exclusive)" do
      min = 1250
      max = 1300
      counter = 0
      base_list = @race_col.find
      base_list.each do |val|
        if val[:secs] != nil && val[:secs] > min && val[:secs] < max 
          counter = counter + 1
        end
      end
      sol_results = solution.find_between(min, max)
      expect(sol_results.count).to eq(counter);
      sol_results.each do |r|
        expect(r[:secs]).to be > min
        expect(r[:secs]).to be < max
      end
    end
  end 

  context "rq02" do
    it "Solution implements instance method find_by_letter" do
      expect(solution).to respond_to(:find_by_letter)
    end
    it "find_between accepts parameters for letter, offset and limit" do
      expect((Solution.instance_method(:find_by_letter).parameters.flatten - [:opt, :req]).count).to eq(3)
    end
    it "finds all race results (ascending by last name) with last name that starts with letter" do
      counter = 0
      letter = 'M'
      base_list = @race_col.find
      base_list.each do |r|
        if (r[:last_name] != nil && r[:last_name].upcase.start_with?(letter))
          counter = counter + 1
        end
      end
      sol_results = solution.find_by_letter(letter, 0, 1000)
      expect(sol_results).to be_a(Mongo::Collection::View)
      sol_results = sol_results.to_a
      expect(sol_results.count).to be <= 1000
      expect(sol_results.count).to eq(counter)
      cur_name = letter
      sol_results.each { |r| 
        expect(r[:last_name].upcase).to start_with(letter)
        expect(r[:last_name].upcase).to be >= cur_name
        cur_name = r[:last_name].upcase
      }
      # test limit
      lim_results = solution.find_by_letter(letter, 0, [counter-10, 1].max).to_a
      expect(lim_results.count).to eq([counter-10, 1].max)
      # test offset
      offset = 10
      off_results = solution.find_by_letter(letter, offset, 1000).to_a
      expect(sol_results.count - offset).to eq(off_results.count)
      expect(sol_results[offset]).to eq(off_results[0])
    end
  end
end
