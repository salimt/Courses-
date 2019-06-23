Rails.application.routes.draw do
  get 'countries/index'
  root 'countries#index'
end
