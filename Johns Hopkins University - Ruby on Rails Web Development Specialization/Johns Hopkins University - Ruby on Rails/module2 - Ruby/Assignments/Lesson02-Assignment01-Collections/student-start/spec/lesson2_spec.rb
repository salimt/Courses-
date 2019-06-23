require 'rspec'
require 'rspec/its'

describe "lesson2" do

  context "output array" do
    result = `ruby module2_lesson2_formative.rb`
    lines = result.split("\n")
    numbers = []
    
    # process the last line of output
    values = lines[lines.count-1].split(",").each { |v|
        number = /([0-9]+)/.match(v).to_s.to_i
        numbers << number
    }

    it "is expected to contain 3 lines" do
      expect(lines.size).to be == 3
    end

    it "should not contain any number under 5000" do
      numbers.each { |n|
        expect(n).to be >= 5000
      }
    end

    it "is expected to be sorted in descending order" do
      expect(numbers).to eq numbers.sort.reverse
    end
    
    it "should not contain numbers not divisible by 3" do
      numbers.each do |n|
        expect(n % 3).to be == 0
      end
    end
  end

  context "implementation" do
    src_code = File.open("module2_lesson2_formative.rb", "r").read

    it "should contain select" do
      expect(src_code).to include("select")
    end

    it "should contain reject" do
      expect(src_code).to include("reject")
    end

    it "should contain sort" do
      expect(src_code).to include("sort")
    end

    it "should contain reverse" do
      expect(src_code).to include("reverse")
    end
  end
end
