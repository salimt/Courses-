class RemoveDescriptionFromTodoItem < ActiveRecord::Migration
  def change
    remove_column :todo_items, :description, :string
  end
end
