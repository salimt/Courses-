(function () {
"use strict";

angular.module('public')
.controller('SignUpController', SignUpController)
.constant('ApiBasePath', "https://salimt-course5.herokuapp.com");

SignUpController.$inject = ['$http', 'ApiBasePath', 'sharedProperties']
function SignUpController($http, ApiBasePath, sharedProperties) {
  var reg = this;
  reg.firstname, reg.lastname, reg.email, reg.phone, reg.favedish = "";

  var dish, img, desc = "";
  reg.confirmDish = null;

  reg.submit = function () {
  	reg.completed = false;
  	if(reg.confirmDish===true){
  		sharedProperties.setData(reg.firstname, reg.lastname, reg.email, reg.phone, dish, img, desc);
		reg.completed = true;
  	}
  };

  // this method should return a promise 
  // which is a result of using the $http service, using the following REST API endpoint
  reg.getFaveDish = function () {
  	if(reg.favedish && reg.favedish.length > 1){
	  	var shortName = reg.favedish.toUpperCase().replace(/\d/g, "")
	  	var num = reg.favedish.replace(/\D/g,'');

	  	console.log("short: " + shortName + " num: " + num);
	    return $http({
	      method: "GET",
	      url: (ApiBasePath + "/menu_items.json"),
	      params: {
	        category: shortName
	      }
	    }).then(function (successResult) {
	  		var itemFound = "";
	    	if(num<successResult.data.menu_items.length){
	    		var dishSearch = successResult.data.menu_items[num-1];
	    		console.log(dishSearch);
		    	if(dishSearch.short_name){
		    		itemFound = dishSearch.name;

		    		dish = itemFound;
		    		img = dishSearch.image_present===true? (shortName+num+".jpg") : "";
		    		desc = dishSearch.description;
		    		reg.confirmDish = (itemFound!="");
		    		return itemFound;
		    	}
	    	} 
    	    dish,img,desc = "";
    	    reg.confirmDish = (itemFound!="");
 
            // return processed items
            return itemFound;
	          
	      });
  	}
  };
}
})();
