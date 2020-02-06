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
  })



}

})();



// (function () {
// 'use strict';

// angular.module('data')
// .controller('ItemsController', ItemsController);

// ItemsController.$inject = ['MenuDataService'];
// function ItemsController(MenuDataService) {
//   var narrow = this;
//   narrow.searchTerm = "";
//   narrow.items = [];

//   console.log("Search: " + MenuDataService.categoryShortName);
//   console.log("narr Search: " + narrow.searchTerm);

//   narrow.logDishes = function () {
//     var promise = MenuDataService.getItemsForCategory(narrow.searchTerm);
//     promise.then(function (response) {
//       narrow.items = response;
//     })
//     .catch(function (error) {
//       console.log(error);
//     })
//   };

//   narrow.removeItem = function (itemIndex) {
//     console.log("'this' is: ", this);
//     this.lastRemoved = "Last item removed was " + this.items[itemIndex].name;
//     narrow.items.splice(itemIndex, 1);
//   };
// }

// })();
