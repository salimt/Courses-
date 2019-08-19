class RunResult < LegResult
  field :mmile, as: :minute_mile, type: Float
  def calc_ave
	if event && secs
		miles = event.miles
		self.mmile=miles.nil? ? nil : (secs/60)/miles
	end
  end

  def secs= value
	self[:secs]=value
	calc_ave
  end
end