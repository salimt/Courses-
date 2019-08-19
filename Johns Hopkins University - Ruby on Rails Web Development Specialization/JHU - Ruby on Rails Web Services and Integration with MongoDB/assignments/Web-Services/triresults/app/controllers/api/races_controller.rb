module Api
  class RacesController < ApplicationController
    protect_from_forgery with: :null_session

    rescue_from ActionView::MissingTemplate do |exception|
      Rails.logger.debug exception
      @msg = "woops: we do not support that content-type[#{request.accept}]"
      render plain: @msg, status: 415
    end

    rescue_from Mongoid::Errors::DocumentNotFound do |exception|
      @msg = "woops: cannot find race[#{params[:id]}]"
      render :status=>:not_found,
             :template=>"api/error_msg",
             :locals=>{ :msg=> @msg}
    end

    def index
      if !request.accept || request.accept == "*/*"
        render plain: "#{api_races_path}, offset=[#{params[:offset]}], limit=[#{params[:limit]}]"
      end
    end

    def show
      if !request.accept || request.accept == "*/*"
        render plain: api_race_path(params[:id])
      else
        @race = Race.find(params[:id])
        render "show", status: :ok
      end
    end


    def create
      if !request.accept || request.accept == "*/*"
        render plain: "#{params[:race][:name]}", status: :ok
        
      else
        race = Race.create!(race_params)
        if race.save
          render plain: "#{params[:race][:name]}", status: :created
        else
          render plain: :nothing, status: :unprocessable_entity
        end
      end
    end

    def update
      Rails.logger.debug("method=#{request.method}")
      race=Race.find(params[:id])
      if race.update(race_params)
        render json: race, status: :ok
      else
        render json: race, status: :unprocessable_entity
      end
    end

    def destroy
      Race.where(:id=>params[:id]).destroy_all
      render :nothing => true, :status => 204
    end


    private
      # Never trust parameters from the scary internet, only allow the white list through.
      def race_params
        params.require(:race).permit(:name, :date)
      end
  end
end
