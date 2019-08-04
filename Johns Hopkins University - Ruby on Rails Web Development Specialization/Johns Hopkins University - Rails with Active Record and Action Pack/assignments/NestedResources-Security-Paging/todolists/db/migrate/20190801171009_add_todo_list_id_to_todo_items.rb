class AddTodoListIdToTodoItems < ActiveRecord::Migration
  def change
    add_column :todo_items, :todo_list_id, :integer
  end
end
