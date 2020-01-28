(function () {
'use strict';

angular.module('LunchCheck', [])
.controller('LunchCheckController', LunchCheckController);

LunchCheckController.$inject = ['$scope'];
function LunchCheckController($scope) {
  $scope.lunchMenu = "";
  $scope.msg = "";

  $scope.itemCounter = function () {
    var color = "green";
    var n = $scope.lunchMenu.split(',').filter(function (el) {
      return el.length>0;
    }).length;
    console.log(n);
    if ( $scope.lunchMenu == "" ) {
      $scope.msg = "Please enter data first";
      color = "red";
    } else if (n > 3) {
      $scope.msg = "Too Much!";
    } else {
      $scope.msg = "Enjoy!";
    } 
    document.getElementById('viewMsg').style.color = color;
    document.getElementById('lunch-menu').style.borderColor = color;
  };
}

})();
