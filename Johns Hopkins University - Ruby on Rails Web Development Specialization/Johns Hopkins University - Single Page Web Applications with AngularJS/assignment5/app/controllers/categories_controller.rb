class CategoriesController < ApplicationController
  include RailsApiAuth::Authentication

  force_ssl if: :ssl_configured?, only: [:update, :create, :destroy]

  before_action :authenticate!, only: [:update, :create, :destroy]
  before_action :set_category, only: [:show, :update, :destroy]

  def index
    @categories = Category.all.order(:id)
  end

  def show
  end

  def create
    @category = Category.new(category_params)

    if @category.save
      render :show, status: :created, location: @category
    else
      render json: @category.errors, status: :unprocessable_entity
    end
  end

  def update
    if @category.update(category_params)
      render :show, status: :ok, location: @category
    else
      render json: @category.errors, status: :unprocessable_entity
    end
  end

  def destroy
    @category.destroy
    head :no_content
  end

  private
    def set_category
      @category = Category.find_by short_name: params[:short_name]
    end

    def category_params
      params.require(:category).permit(:short_name, :name, :special_instructions)
    end

end