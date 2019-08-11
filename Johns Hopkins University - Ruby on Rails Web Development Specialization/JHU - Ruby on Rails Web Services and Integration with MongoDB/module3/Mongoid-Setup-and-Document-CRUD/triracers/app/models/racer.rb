class Racer
  include Mongoid::Document
  include Mongoid::Timestamps
  store_in collection: "racer1"

  field :fn, as: :first_name, type: String
  field :ln, as: :last_name, type: String
  field :dob, as: :date_of_birth, type: Date
  field :gender, type: String

  before_upsert do |doc|
    doc.set_updated_at
  end
end
