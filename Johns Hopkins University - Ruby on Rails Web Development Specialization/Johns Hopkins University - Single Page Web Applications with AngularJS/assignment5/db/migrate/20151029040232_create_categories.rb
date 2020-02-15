class CreateCategories < ActiveRecord::Migration
  def change
    create_table :categories do |t|
      t.string :short_name
      t.string :name
      t.text :special_instructions

      t.timestamps null: false
    end
  end
end
