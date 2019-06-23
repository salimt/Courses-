# class Recipe < ActiveRecord::Base

# require 'httparty'
# class Recipe
# 	include HTTParty
# 	base_uri 'http://food2fork.com/api'
# 	ENV["FOOD2FORK_KEY"] = 'enter your key'
# 	default_params key: ENV["FOOD2FORK_KEY"]
# 	format :json

#     def self.for(keyword)
#     	get("/search", query: {q: keyword})["recipes"]
#     end

# end

# end

require 'httparty'

class Recipe
	include HTTParty
	default_options.update(verify: false)
	ENV["FOOD2FORK_KEY"] = '87a11451bcb03350180ea769efe8dc39'
	default_params key: ENV["FOOD2FORK_KEY"]
	hostport = ENV['FOOD2FORK_SERVER_AND_PORT'] || 'www.food2fork.com'
	base_uri "http://#{hostport}/api"
	format :json

    def self.for(keyword)
    	get("/search", query: {q: keyword})["recipes"]
    end

end

