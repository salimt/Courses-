require 'test_helper'

class RacesControllerTest < ActionController::TestCase
  setup do
    @race = races(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:races)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create race" do
    assert_difference('Race.count') do
      post :create, race: { bike_distance: @race.bike_distance, bike_units: @race.bike_units, city: @race.city, date: @race.date, name: @race.name, run_distance: @race.run_distance, run_units: @race.run_units, state: @race.state, swim_distance: @race.swim_distance, swim_units: @race.swim_units }
    end

    assert_redirected_to race_path(assigns(:race))
  end

  test "should show race" do
    get :show, id: @race
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @race
    assert_response :success
  end

  test "should update race" do
    patch :update, id: @race, race: { bike_distance: @race.bike_distance, bike_units: @race.bike_units, city: @race.city, date: @race.date, name: @race.name, run_distance: @race.run_distance, run_units: @race.run_units, state: @race.state, swim_distance: @race.swim_distance, swim_units: @race.swim_units }
    assert_redirected_to race_path(assigns(:race))
  end

  test "should destroy race" do
    assert_difference('Race.count', -1) do
      delete :destroy, id: @race
    end

    assert_redirected_to races_path
  end
end
