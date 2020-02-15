(function () {
'use strict';

angular.module('public')
.controller('MyInfoController', MyInfoController);


MyInfoController.$inject = ['$scope', 'sharedProperties'];
function MyInfoController($scope, sharedProperties) {
  var myInfo = this;
  myInfo.confirmRegister = sharedProperties.getProperty();
  myInfo.firstname = sharedProperties.getProperty()[0];
  myInfo.lastname = sharedProperties.getProperty()[1];
  myInfo.email = sharedProperties.getProperty()[2];
  myInfo.phone = sharedProperties.getProperty()[3];
  myInfo.favedish = sharedProperties.getProperty()[4];
  myInfo.imageUrl = sharedProperties.getProperty()[5];
  myInfo.description = sharedProperties.getProperty()[6];


  console.log(sharedProperties.getProperty());

}

})();