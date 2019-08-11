class Entrant
  include Mongoid::Document
  field :_id, type: Integer
  field :name, type: String
  field :group, type: String
  field :secs, type: Float

  belongs_to :racer, validate: true
  embedded_in :contest, polymorphic: true 
  validates_presence_of :racer


  #cache the racer's name before creating
  before_create do |doc|
    r=doc.racer
    if r
      doc.name="#{r.last_name}, #{r.first_name}"
    end
  end

end
