class Point
  include Mongoid::Document

  attr_accessor :longitude, :latitude
  #produce a GeoJSON Point hash
  def to_hash
  	{:type=>"Point", :coordinates=>[ @longitude, @latitude ]}
  end

  # initialize from both a Mongo and Web hash
  def initialize(params={})
    if params[:type]
      @longitude=params[:coordinates][0]
      @latitude=params[:coordinates][1]
    else 
      @longitude=params[:lng]
      @latitude=params[:lat]
    end
  end

end
