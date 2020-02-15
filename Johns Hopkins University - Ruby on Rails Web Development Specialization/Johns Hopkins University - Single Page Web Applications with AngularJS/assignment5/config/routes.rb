Rails.application.routes.draw do
  resources :menu_items, param: :short_name, except: [:edit, :new], constraints: { format: :json }
  resources :categories, param: :short_name, except: [:edit, :new], constraints: { format: :json }
  resources :images, param: :short_name, only: [:show]
end
