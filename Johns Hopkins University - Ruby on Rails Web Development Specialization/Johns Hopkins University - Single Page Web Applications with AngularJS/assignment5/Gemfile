source 'https://rubygems.org'
ruby "2.6.3"

gem 'rails', '~> 4.2.11'
gem 'sassc-rails'
gem 'uglifier', '>= 1.3.0'
gem 'coffee-rails', '~> 4.1.0'
gem 'jquery-rails'
gem 'turbolinks'
gem 'jbuilder', '~> 2.0'
gem 'sdoc', '~> 0.4.0', group: :doc

gem 'sqlite3', '1.3.13', group: :development

group :development, :test do
  gem 'web-console', '~> 2.0'
  gem 'spring'
end

group :production do
  gem 'pg', '~> 0.20' # the latest pg gem does not play nicely with Heroku...
  gem 'rails_12factor'
end

gem 'rack-cors', require: 'rack/cors'
gem 'rails_api_auth'
gem 'nokogiri', '>= 1.10.4'
