File.foreach( 'test.txt' ) do |line|
  puts line
  p line
  p line.chomp # chops off newline character
  p line.split # array of words in line
end
