(function() {
"use strict";
/**
 * Public restaurant application. Includes the common module and ui-router.
 */
angular.module('public', ['ui.router', 'common']).service('sharedProperties', function () {
	var firstname = "";
	var lastname = "";
	var email = "";
	var phone = "";
	var favedish = "";
	var imageUrl = "";
	var description = "";

    return {
        getProperty: function () {
        	if(firstname!="" && lastname!="" && favedish !=""){
        		return [firstname, lastname, email, phone, favedish, imageUrl, description];
        	} return false; 
        },
        setData(fn, ln, em, ph, fdish, img, desc) {
	        firstname = fn;
	        lastname = ln;
	        email = em;
	        phone = ph;
	        favedish = fdish;
	        imageUrl = img;
	        description = desc;
    }
    };
});

})();
