json.extract! race, :id, :name, :date, :city, :state, :swim_distance, :swim_units, :bike_distance, :bike_units, :run_distance, :run_units, :created_at, :updated_at
json.url race_url(race, format: :json)
