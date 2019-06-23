require 'httparty'
require 'openssl'
OpenSSL::SSL::VERIFY_PEER = OpenSSL::SSL::VERIFY_NONE


class Recipe
	
	include HTTParty
	base_uri 'http://food2fork.com/api'
	ENV["FOOD2FORK_KEY"] = 'enter your key'
	default_params key: ENV["FOOD2FORK_KEY"]
	format :json

    def self.for(keyword)

    	get("/search", query: {q: keyword})["recipes"]

    end


end



# Or wrap things up in your own class
# class StackExchange
#   include HTTParty
#   base_uri 'api.stackexchange.com'

#   def initialize(service)
#     @options = { query: { title: title } }
#   end

#   def questions
#     self.class.get("/2.2/questions", @options)
#   end

#   def users
#     self.class.get("/2.2/users", @options)
#   end
# end

#stack_exchange = StackExchange.new("stackoverflow", 1)
#puts stack_exchange.questions
#puts stack_exchange.users
