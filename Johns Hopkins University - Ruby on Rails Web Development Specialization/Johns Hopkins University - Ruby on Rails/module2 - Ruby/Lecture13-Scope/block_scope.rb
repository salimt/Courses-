class BankAccount 
  attr_accessor :id, :amount 
  def initialize(id, amount) 
    @id = id 
    @amount = amount 
  end 
end 

acct1 = BankAccount.new(123, 200) 
acct2 = BankAccount.new(321, 100) 
acct3 = BankAccount.new(421, -100) 
accts = [acct1, acct2, acct3] 

total_sum = 0 
accts.each do |eachAcct| 
  total_sum += eachAcct.amount 
end 

puts total_sum # => 200


