if File.exist? 'test.txt'

  File.foreach( 'test.txt' ) do |line|
    puts line.chomp
  end

end

