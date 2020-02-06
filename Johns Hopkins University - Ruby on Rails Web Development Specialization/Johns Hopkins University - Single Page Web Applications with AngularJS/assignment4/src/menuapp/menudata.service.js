(function () {
'use strict';

angular.module('data')
.service('MenuDataService', MenuDataService)
.constant('ApiBasePath', "https://davids-restaurant.herokuapp.com");


MenuDataService.$inject = ['ApiBasePath', '$http', '$q', '$timeout']
function MenuDataService(ApiBasePath, $http, $q, $timeout) {
  var service = this;

  var categoryShortName = "";
  console.log(categoryShortName);
  // List of shopping items
  var items = [];

  // Simulates call to server
  // Returns a promise, NOT items array directly
  service.getItems = function () {
    var deferred = $q.defer();

    // Wait 2 seconds before returning
    $timeout(function () {
      // deferred.reject(items);
      deferred.resolve(service.getAllCategories());
    }, 800);

    return deferred.promise;
  };

  // this method should return a promise 
  // which is a result of using the $http service, using the following REST API endpoint
  service.getAllCategories = function () {
    return $http({
        method: "GET",
        url: (ApiBasePath + "/categories.json"),
      }).then(function (successResult) {
          // process result and only keep items that match
          var foundItems = [];

          for (var i = successResult.data.length - 1; i >= 0; i--) {
            foundItems.push(successResult.data[i]);
          };

          // return processed items
          return foundItems;
      });
  };

  // this method should return a promise 
  // which is a result of using the $http service, using the following REST API endpoint
  service.getItemsForCategory = function (categoryShortName) {
    // console.log("categoryShortName: "+categoryShortName);
    return $http({
        method: "GET",
        url: (ApiBasePath + "/menu_items.json?category=" + categoryShortName.toUpperCase()),
      }).then(function (successResult) {
          // process result and only keep items that match
          var foundItems = [];
          for (var i = successResult.data.menu_items.length - 1; i >= 0; i--) {
                foundItems.push(successResult.data.menu_items[i]);
          };
          // return processed items
          return foundItems;
      });
  };


}

})();
