class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  before_action :ensure_login
  helper_method :logged_in?, :current_user

  protected
    def ensure_login
      # Always go to login page unless session contains
      # user_id
      redirect_to login_path unless session[:user_id]
    end

    def logged_in?
      session[:user_id] # nil is false
    end

    def current_user
      @current_user ||= User.find(session[:user_id])
    end
end
