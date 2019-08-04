require 'test_helper'

class TodoListsControllerTest < ActionController::TestCase
  setup do
    @todo_list = todo_lists(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:todo_lists)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create todo_list" do
    assert_difference('TodoList.count') do
      post :create, todo_list: { list_due_date: @todo_list.list_due_date, list_name: @todo_list.list_name }
    end

    assert_redirected_to todo_list_path(assigns(:todo_list))
  end

  test "should show todo_list" do
    get :show, id: @todo_list
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @todo_list
    assert_response :success
  end

  test "should update todo_list" do
    patch :update, id: @todo_list, todo_list: { list_due_date: @todo_list.list_due_date, list_name: @todo_list.list_name }
    assert_redirected_to todo_list_path(assigns(:todo_list))
  end

  test "should destroy todo_list" do
    assert_difference('TodoList.count', -1) do
      delete :destroy, id: @todo_list
    end

    assert_redirected_to todo_lists_path
  end
end
