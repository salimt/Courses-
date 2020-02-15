class MenuItemsController < ApplicationController
  include RailsApiAuth::Authentication

  force_ssl if: :ssl_configured?, only: [:update, :create, :destroy]

  before_action :authenticate!, only: [:update, :create, :destroy]
  before_action :set_menu_item, only: [:show, :update, :destroy]

  def index
    if params[:category].present?
      @category = Category.find_by short_name: params[:category]
      @menu_items = []
      @menu_items = @category.menu_items unless @category.nil?
    else
      # Avoid N+1 queries...
      @menu_items = MenuItem.includes(:category).all
    end
  end

  def show
  end

  def create
    @menu_item = MenuItem.new(menu_item_params)

    if @menu_item.save
      render :show, status: :created, location: @menu_item
    else
      render json: @menu_item.errors, status: :unprocessable_entity
    end
  end

  def update
    if @menu_item.update(menu_item_params)
      render :show, status: :ok, location: @menu_item
    else
      render json: @menu_item.errors, status: :unprocessable_entity
    end
  end

  def destroy
    @menu_item.destroy
    head :no_content
  end

  private
    def set_menu_item
      @menu_item = MenuItem.find_by short_name: params[:short_name]
    end

    def menu_item_params
       params.require(:menu_item).permit(:short_name, :name, :description,
                                         :price_small, :price_large, :small_portion_name,
                                         :large_portion_name, :category_id, :image_base64_encoded)
    end

end
