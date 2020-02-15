json.array!(@categories) do |category|
  json.extract! category, :id, :short_name, :name, :special_instructions
  json.url category_url(category, format: :json)
end
