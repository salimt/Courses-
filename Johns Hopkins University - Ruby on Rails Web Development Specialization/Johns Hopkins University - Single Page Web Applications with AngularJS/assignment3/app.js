(function () {
'use strict';

angular.module('NarrowItDownApp', [])
.controller('NarrowItDownController', NarrowItDownController)
.service('MenuSearchService', MenuSearchService)
.constant('ApiBasePath', "https://davids-restaurant.herokuapp.com")
.directive('foundItems', FoundItemsDirective);

function FoundItemsDirective() {
  var ddo = {
    templateUrl: 'foundItems.html',
    scope: {
      items: '<',
      onRemove: '&'
    },
    controller: NarrowItDownController,
    controllerAs: 'narrow',
    bindToController: true
  };

  return ddo;
}

NarrowItDownController.$inject = ['MenuSearchService'];
function NarrowItDownController(MenuSearchService) {
  var narrow = this;
  narrow.searchTerm = "";
  narrow.items = [];

  narrow.logDishes = function () {
    var promise = MenuSearchService.getMatchedMenuItems(narrow.searchTerm);
    promise.then(function (response) {
      narrow.items = response;
    })
    .catch(function (error) {
      console.log(error);
    })
  };

  narrow.removeItem = function (itemIndex) {
    console.log("'this' is: ", this);
    this.lastRemoved = "Last item removed was " + this.items[itemIndex].name;
    narrow.items.splice(itemIndex, 1);
  };

}

MenuSearchService.$inject = ['$http', 'ApiBasePath'];
function MenuSearchService($http, ApiBasePath) {
  var service = this;
  var term = "";

  service.getMatchedMenuItems = function (searchTerm) {
    console.log("searchTerm2: "+searchTerm);
    return $http({
        method: "GET",
        url: (ApiBasePath + "/menu_items.json"),
      }).then(function (successResult) {
          // process result and only keep items that match
          var foundItems = [];

          for (var i = successResult.data.menu_items.length - 1; i >= 0; i--) {
              var desc = successResult.data.menu_items[i]["description"];
              console.log("searchTerm: "+term);
              if(desc.includes(searchTerm)){
                foundItems.push(successResult.data.menu_items[i]);
              }
          };
          console.log(foundItems);
          // return processed items
          return foundItems;
      });
  };
}

})();
