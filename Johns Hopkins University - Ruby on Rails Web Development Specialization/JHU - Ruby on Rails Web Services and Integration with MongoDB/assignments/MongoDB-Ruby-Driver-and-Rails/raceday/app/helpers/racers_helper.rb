module RacersHelper
	def toRacer(value)
    	#change value to a Racer if not already a Racer
    	return value.is_a?(Racer) ? value : Racer.new(value)
  	end
end
