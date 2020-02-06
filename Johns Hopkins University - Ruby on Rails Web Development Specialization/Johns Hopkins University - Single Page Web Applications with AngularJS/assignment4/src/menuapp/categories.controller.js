(function () {
'use strict';

angular.module('data')
.controller('MainCategoryListController', MainCategoryListController);

MainCategoryListController.$inject = ['MenuDataService', 'items', '$state'];
function MainCategoryListController(MenuDataService, items, $state) {
  var categorylist = this;
  categorylist.items = items;
  console.log($state.current.name );
}

})();
