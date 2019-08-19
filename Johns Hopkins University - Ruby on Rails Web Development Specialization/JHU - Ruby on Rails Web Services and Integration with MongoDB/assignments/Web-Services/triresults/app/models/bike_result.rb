class BikeResult < LegResult
  field :mph, as: :mph, type: Float
  def calc_ave
	if event && secs
		miles = event.miles
		self.mph= miles.nil? ? nil : miles*3600/secs
	end
  end

  def secs= value
	self[:secs]=value
	calc_ave
  end
end