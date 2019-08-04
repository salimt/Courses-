json.extract! todo_list, :id, :list_name, :list_due_date, :created_at, :updated_at
json.url todo_list_url(todo_list, format: :json)
