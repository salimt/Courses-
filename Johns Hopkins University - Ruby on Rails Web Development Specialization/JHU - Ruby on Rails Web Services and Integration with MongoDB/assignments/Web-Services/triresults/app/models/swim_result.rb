class SwimResult < LegResult
  field :pace_100, as: :pace_100, type: Float
  def calc_ave
	if event && secs
		meters = event.meters
		self.pace_100=meters.nil? ? nil : secs/(meters/100)
	end
  end

  def secs= value
	self[:secs]=value
	calc_ave
  end
end