json.extract! @menu_item, :id, :short_name, :name, :description, :price_small, :price_large, :small_portion_name, :large_portion_name, :created_at, :updated_at
json.category_short_name(@menu_item.category.short_name)
json.image_present(!!@menu_item.image_base64_encoded)

