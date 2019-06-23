Rails.application.routes.draw do
  get 'books/index'
  root 'books#index'
end
