class Category < ActiveRecord::Base
  has_many :menu_items, dependent: :destroy

  def to_param
  	short_name
  end
end
