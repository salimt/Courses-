class AddColumnToTodolists < ActiveRecord::Migration
  def change
    add_column :todo_lists, :user_id, :integer
  end
end
