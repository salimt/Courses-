class AddImageBase64EncodedToMenuItems < ActiveRecord::Migration
  def change
    add_column :menu_items, :image_base64_encoded, :text
  end
end
