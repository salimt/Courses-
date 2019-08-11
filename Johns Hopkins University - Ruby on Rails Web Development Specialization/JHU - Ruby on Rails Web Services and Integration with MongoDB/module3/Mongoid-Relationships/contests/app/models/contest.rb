class Contest
  include Mongoid::Document
  include Mongoid::Timestamps::Updated
  field :name, type: String
  field :date, type: Date

  belongs_to :venue
  embeds_many :entrants, class_name: 'Entrant'
  has_and_belongs_to_many :judges

end
