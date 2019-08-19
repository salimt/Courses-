module Api
  class ResultsController < ApplicationController

    rescue_from Mongoid::Errors::DocumentNotFound do |exception|
      @msg = "woops: cannot find race[#{params[:id]}]"
      render :status=>:not_found,
             :template=>"api/error_msg",
             :locals=>{ :msg=> @msg}
    end


    def index
      if !request.accept || request.accept == "*/*"
        render plain: "/api/races/#{params[:race_id]}/results"
      else
        @race = Race.find(params[:race_id])
        @entrants = @race.entrants
        fresh_when last_modified: @entrants.max(:updated_at)
      end
    end


    def show
      if !request.accept || request.accept == "*/*"
        render plain: "/api/races/#{params[:race_id]}/results/#{params[:id]}"
      else
        @result = Race.find(params[:race_id]).entrants.where(id: params[:id]).first
        render :show, status: :ok
      end
    end

    def update
      entrant = Race.find(params[:race_id]).entrants.where(id: params[:id]).first
      result=params[:result]
      if result
        if result[:swim]
          entrant.swim=entrant.race.race.swim
          entrant.swim_secs = result[:swim].to_f
        end
        if result[:t1]
          entrant.t1=entrant.race.race.t1
          entrant.t1_secs = result[:t1].to_f
        end
        if result[:t2]
          entrant.t2=entrant.race.race.t2
          entrant.t2_secs = result[:t2].to_f
        end
        if result[:bike]
          entrant.bike=entrant.race.race.bike
          entrant.bike_secs = result[:bike].to_f
        end
        if result[:run]
          entrant.run=entrant.race.race.run
          entrant.run_secs = result[:run].to_f
        end
      end
      entrant.save
      render :nothing=>true, :status=>:ok
    end


  end
end
