(function () {
'use strict';

angular.module('MenuApp')
.component('items', {
  templateUrl: 'src/menuapp/templates/dishes.template.html',
  bindings: {
    items: '<'
  }
});

})();
