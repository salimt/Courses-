require_relative "../module2_assignment"
require 'rspec'

describe LineAnalyzer do
  subject(:lineAnalyzer) { LineAnalyzer.new("test", 1) }

  it "has accessor for highest_wf_count" do
    is_expected.to respond_to(:highest_wf_count) 
  end 
  it "has accessor for highest_wf_words" do
    is_expected.to respond_to(:highest_wf_words) 
  end
  it "has accessor for content" do
    is_expected.to respond_to(:content) 
  end
  it "has accessor for line_number" do
    is_expected.to respond_to(:line_number) 
  end
  it "has method calculate_word_frequency" do
    is_expected.to respond_to(:calculate_word_frequency) 
  end
  context "attributes and values" do
  it "has attributes content and line_number" do
    is_expected.to have_attributes(content: "test", line_number: 1) 
  end
  it "content attribute should have value \"test\"" do
    expect(lineAnalyzer.content).to eq("test")
  end
  it "line_number attribute should have value 1" do
    expect(lineAnalyzer.line_number).to eq(1)
  end
end

  it "calls calculate_word_frequency when created" do
    expect_any_instance_of(LineAnalyzer).to receive(:calculate_word_frequency)
    LineAnalyzer.new("", 1) 
  end

  context "#calculate_word_frequency" do
    subject(:lineAnalyzer) { LineAnalyzer.new("This is a really really really cool cool you you you", 2) }

    it "highest_wf_count value is 3" do
      expect(lineAnalyzer.highest_wf_count).to eq(3)
    end
    it "highest_wf_words will include \"really\" and \"you\"" do
      expect(lineAnalyzer.highest_wf_words).to include 'really', 'you'
    end
    it "content attribute will have value \"This is a really really really cool cool you you you\"" do
      expect(lineAnalyzer.content).to eq("This is a really really really cool cool you you you")
    end
    it "line_number attribute will have value 2" do
      expect(lineAnalyzer.line_number).to eq(2)
    end
  end
end