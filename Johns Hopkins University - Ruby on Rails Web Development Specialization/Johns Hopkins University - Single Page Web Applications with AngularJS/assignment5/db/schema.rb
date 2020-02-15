# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20160731205241) do

  create_table "categories", force: :cascade do |t|
    t.string   "short_name"
    t.string   "name"
    t.text     "special_instructions"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
  end

  create_table "logins", force: :cascade do |t|
    t.string   "identification",          null: false
    t.string   "password_digest"
    t.string   "oauth2_token",            null: false
    t.string   "uid"
    t.string   "single_use_oauth2_token"
    t.integer  "user_id"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "provider"
  end

  create_table "menu_items", force: :cascade do |t|
    t.string   "short_name"
    t.string   "name"
    t.text     "description"
    t.float    "price_small"
    t.float    "price_large"
    t.string   "small_portion_name"
    t.string   "large_portion_name"
    t.integer  "category_id"
    t.datetime "created_at",           null: false
    t.datetime "updated_at",           null: false
    t.text     "image_base64_encoded"
  end

  add_index "menu_items", ["category_id"], name: "index_menu_items_on_category_id"

end
