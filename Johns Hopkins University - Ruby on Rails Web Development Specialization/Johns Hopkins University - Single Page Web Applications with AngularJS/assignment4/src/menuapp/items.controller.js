(function () {
'use strict';

angular.module('data')
.controller('ItemsController', ItemsController);


ItemsController.$inject = ['$stateParams', 'items', 'MenuDataService'];
function ItemsController($stateParams, items, MenuDataService) {
  var categoryDetail = this;
  categoryDetail.items = [];

  var promise = MenuDataService.getItemsForCategory($stateParams.categoryShortName);
  promise.then(function (response) {
    categoryDetail.items = response;
  })
  .catch(function (error) {
    console.log(error);
  });

}

})();