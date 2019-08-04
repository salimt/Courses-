class TodoItemsController < ApplicationController
  before_action :set_todo_list
  before_action :set_todo_item, only: [:show, :edit, :update, :destroy]
  

  # GET /todo_lists/:todo_list_id/todo_items/:id
  # GET /todo_lists/:todo_list_id/todo_items/:id.json
  def show
  end

  # GET /todo_lists/:todo_list_id/todo_items/new
  def new
    @todo_item = @todo_list.todo_items.new
  end

  # GET /todo_lists/:todo_list_id/todo_items/:id/edit
  def edit
  end

  # POST /todo_lists/:todo_list_id/todo_items
  # POST /todo_lists/:todo_list_id/todo_items.json
  def create
    @todo_item = @todo_list.todo_items.new(todo_item_params)

    respond_to do |format|
      if @todo_item.save
        format.html { redirect_to @todo_list, notice: 'Todo item was successfully created.' }
        format.json { render :show, status: :created, location: @todo_item }
      else
        format.html { render :new }
        format.json { render json: @todo_item.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /todo_lists/:todo_list_id/todo_items/:id
  # PATCH/PUT /todo_lists/:todo_list_id/todo_items/:id.json
  def update
    respond_to do |format|
      if @todo_item.update(todo_item_params)
        format.html { redirect_to @todo_list, notice: 'Todo item was successfully updated.' }
        format.json { render :show, status: :ok, location: @todo_item }
      else
        format.html { render :edit }
        format.json { render json: @todo_item.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /todo_lists/:todo_list_id/todo_items/:id
  # DELETE /todo_lists/:todo_list_id/todo_items/:id.json
  def destroy
    @todo_item.destroy
    respond_to do |format|
      format.html { redirect_to @todo_list, notice: 'Todo item was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private

    # Use callbacks to share common setup or constraints between actions.
    def set_todo_list
      @todo_list = TodoList.find(params[:todo_list_id])
    end

    # Use callbacks to share common setup or constraints between actions.
    def set_todo_item
      @todo_item = @todo_list.todo_items.find(params[:id])     
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def todo_item_params
      params.require(:todo_item).permit(:title, :due_date, :description, :completed)
    end
end
