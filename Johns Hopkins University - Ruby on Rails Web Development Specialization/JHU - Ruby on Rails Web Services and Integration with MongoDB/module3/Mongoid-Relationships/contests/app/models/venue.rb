class Venue
  include Mongoid::Document
  field :name, type: String

  embeds_one :address, class_name: 'Address' , as: :addressable
  has_many :contests, dependent: :restrict
end
