class LegResult
  include Mongoid::Document
  field :secs, as: :secs, type: Float

  embedded_in :entrant,  class_name: 'LegResult'
  embeds_one :event, class_name: 'Event'
  validates_presence_of :event

  def calc_ave
  	#subclasses will calc event-specific ave
  end

  after_initialize do |doc|
	  calc_ave
  end

  def secs= value
	self[:secs]=value
  end

end
