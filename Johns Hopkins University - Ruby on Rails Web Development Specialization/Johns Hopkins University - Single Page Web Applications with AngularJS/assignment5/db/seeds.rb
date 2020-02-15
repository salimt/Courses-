require 'csv'
require "base64"


Category.destroy_all

CSV.foreach("#{Rails.root}/db/categories.csv", headers: true) do |row|
  Category.create! short_name: row["short_name"],
                   name: row["name"],
                   special_instructions: row["special_instructions"] || ""
end

# Menu Item images
files = Dir["#{Rails.root}/db/menu_items_images/**/*.jpg"].map { |file| [File.basename(file, ".jpg"), file]}
file_map = Hash[*files.flatten(1)]

CSV.foreach("#{Rails.root}/db/menu_items.csv", headers: true) do |row|
  category = Category.find_by! short_name: row["category_id"]
  menu_item_short_name = row["short_name"]

  # Read and base64 encode the image
  filepath = file_map[menu_item_short_name] || ""
  image_base64_encoded = nil
  if File.exists? filepath
    image_data = File.binread(filepath)
    image_base64_encoded = Base64.encode64(image_data)
  end

  MenuItem.create! short_name: menu_item_short_name,
                   name: row["name"],
                   description: row["description"] || "",
                   price_small: row["price_small"],
                   price_large: row["price_large"],
                   small_portion_name: row["small_portion_name"],
                   large_portion_name: row["large_portion_name"],
                   category: category,
                   image_base64_encoded: image_base64_encoded
end