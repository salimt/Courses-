(function () {
'use strict';

angular.module('data', ['ui.router']).run(['$rootScope', '$state', function($rootScope, $state) 
	{ $rootScope.$state = $state; }]);

})();
