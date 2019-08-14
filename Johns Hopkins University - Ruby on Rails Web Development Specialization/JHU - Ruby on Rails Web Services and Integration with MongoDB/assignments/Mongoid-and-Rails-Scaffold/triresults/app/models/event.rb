class Event
  include Mongoid::Document

  field :o, as: :order, type: Integer
  field :n, as: :name, type: String
  field :d, as: :distance, type: Float
  field :u, as: :units, type: String

  embedded_in :parent,  class_name: 'Race', polymorphic: true, touch: true
  validates_presence_of :name, :order

  def meters
  	case u
  	when "miles" then return d*1609.344
  	when "kilometers" then return d*1000
  	when "yards" then return d*0.9144
  	when "meters" then return d
  	end
  end

  def miles
  	case u
  	when "meters" then return d*0.000621371
  	when "kilometers" then return d*0.621371
  	when "yards" then return d*0.000568182
  	when "miles" then return d
  	end
  end

end
