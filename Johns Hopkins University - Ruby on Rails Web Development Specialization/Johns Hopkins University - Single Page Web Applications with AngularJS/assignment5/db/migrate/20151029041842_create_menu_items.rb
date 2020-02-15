class CreateMenuItems < ActiveRecord::Migration
  def change
    create_table :menu_items do |t|
      t.string :short_name
      t.string :name
      t.text :description
      t.float :price_small
      t.float :price_large
      t.string :small_portion_name
      t.string :large_portion_name
      t.references :category, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
