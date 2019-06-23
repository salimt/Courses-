require 'rspec'
require 'capybara'
require 'capybara/dsl'
require 'capybara/poltergeist'

RSpec.configure do |config|
  config.include Capybara::DSL
end