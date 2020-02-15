class ImagesController < ApplicationController
	before_action :set_menu_item, only: [:show]

	def show
		if @menu_item && @menu_item.image_base64_encoded
			send_data Base64.decode64(@menu_item.image_base64_encoded),
				type:'image/jpeg', disposition: 'inline'
		else
			render file: 'public/404', status: 404, formats: [:html]
		end
	end

	private
		def set_menu_item
		  @menu_item = MenuItem.find_by short_name: params[:short_name]
		end

end
