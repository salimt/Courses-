class ApplicationController < ActionController::Base

  protect_from_forgery with: :exception
  skip_before_action :verify_authenticity_token, if: :json_request?

  protected

    def ssl_configured?
      !Rails.env.development?
    end

  private

    def json_request?
      request.format.json?
    end
end
