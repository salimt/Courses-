#@author salimt
#Implement all parts of this assignment within (this) module2_assignment2.rb file

class LineAnalyzer
  #* highest_wf_count - a number with maximum number of occurrences for a single word (calculated)
  #* highest_wf_words - an array of words with the maximum number of occurrences (calculated)
  #* content          - the string analyzed (provided)
  #* line_number      - the line number analyzed (provided)
  attr_reader :highest_wf_words, :highest_wf_count, :content, :line_number

  #* initialize() - taking a line of text (content) and a line number
  def initialize (content, line_number) # CONSTRUCTOR 
    @content = content
    @line_number = line_number
    self.calculate_word_frequency(content)
  end 

  #* calculate_word_frequency() - calculates result
  def calculate_word_frequency(content)
    word_frequency = Hash.new(0) 
    content.split.each {|word| word_frequency[word.downcase]+=1}
    @highest_wf_count = word_frequency.values.max
    @highest_wf_words = word_frequency.select {|key, value| value == @highest_wf_count}.keys
  end

end


class Solution

  #* analyzers - an array of LineAnalyzer objects for each line in the file
  #* highest_count_across_lines - a number with the maximum value for highest_wf_words attribute in the analyzers array.
  #* highest_count_words_across_lines - a filtered array of LineAnalyzer objects with the highest_wf_words attribute 
  #  equal to the highest_count_across_lines determined previously.
    attr_reader :analyzers, :highest_count_across_lines, :highest_count_words_across_lines

    def initialize () # CONSTRUCTOR 
      @analyzers = []
    end 

  
  # Read the ‘test.txt’ file in lines
  # Create an array of LineAnalyzers for each line in the file
  def analyze_file()
    if File.exist? 'test.txt'
      File.foreach('test.txt').each_with_index { |line, index| @analyzers.push LineAnalyzer.new(line, index) }
    end
  end

  # calculate the maximum value for highest_wf_count contained by the LineAnalyzer objects in the analyzers
  # array and store this result in the highest_count_across_lines attribute.
  # identify the LineAnalyzer object(s) in the analyzers array that have the highest_wf_count equal to
  # the highest_count_across_lines attribute value found in the previous step and store them in highest_
  # count_words_across_lines attribute.
  def calculate_line_with_highest_frequency()
    temp = 0; @analyzers.each {|a| if a.highest_wf_count > temp then temp = a.highest_wf_count end}
    @highest_count_across_lines = temp

    @highest_count_words_across_lines = @analyzers.select {|a| @highest_count_across_lines == a.highest_wf_count}
  end

  # print the result
  def print_highest_word_frequency_across_lines()
    puts "The following words have the highest word frequency per line:"
    @highest_count_words_across_lines.each {|word| puts "#{word.highest_wf_words} (appears in line #{word.highest_wf_count})"}
  end

end