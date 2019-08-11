class Address
  include Mongoid::Document
  field :street, type: String
  field :city, type: String
  field :state, type: String
  field :country, type: String
  field :geolocation, type: Point

  embedded_in :addressable, polymorphic: true 

end
