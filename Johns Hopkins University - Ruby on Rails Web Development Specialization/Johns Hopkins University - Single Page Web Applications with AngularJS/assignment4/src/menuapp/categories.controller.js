(function () {
'use strict';

angular.module('data')
.controller('MainCategoryListController', MainCategoryListController);


MainCategoryListController.$inject = ['MenuDataService', 'items'];
function MainCategoryListController(MenuDataService, items) {
  var categorylist = this;
  categorylist.items = items;
}

})();



// (function () {
// 'use strict';

// angular.module('data')
// .controller('CategoriesController', CategoriesController);


// // MainShoppingListController.$inject = ['ShoppingListService'];
// // function MainShoppingListController(ShoppingListService) {
// CategoriesController.$inject = ['items'];
// function CategoriesController(items) {
//   var categories = this;
//   categories.items = items;

//   // mainList.$onInit = function () {
//   //   ShoppingListService.getItems()
//   //   .then(function (result) {
//   //     mainList.items = result;
//   //   });
//   // };
// }

// })();
