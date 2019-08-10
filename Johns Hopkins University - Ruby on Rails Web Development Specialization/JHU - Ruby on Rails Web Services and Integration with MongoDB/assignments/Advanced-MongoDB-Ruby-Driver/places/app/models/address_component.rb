class AddressComponent
  include Mongoid::Document

  attr_accessor :long_name, :short_name, :types

  # initialize from both a Mongo and Web hash
  def initialize(params={})
	  @long_name=params[:long_name]
	  @short_name=params[:short_name]
	  @types=params[:types]
  end
end
