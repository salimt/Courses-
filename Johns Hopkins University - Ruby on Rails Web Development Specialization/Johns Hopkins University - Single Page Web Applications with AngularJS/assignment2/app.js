(function () {
'use strict';

angular.module('ShoppingListCheckOff', [])
.controller('ToBuyController', ToBuyController)
.controller('AlreadyBoughtController', AlreadyBoughtController)
.controller('AddItemController', AddItemController)
.service('ShoppingListCheckOffService', ShoppingListCheckOffService)


ToBuyController.$inject = ['ShoppingListCheckOffService'];
function ToBuyController(ShoppingListCheckOffService) {
  var buyItem = this;
  buyItem.toBeBoughtItems = ShoppingListCheckOffService.getToBeBoughtItems();
  
  buyItem.addItem = function (itemIndex) {
    ShoppingListCheckOffService.addItem(itemIndex, buyItem.toBeBoughtItems[itemIndex]["name"], 
                                                   buyItem.toBeBoughtItems[itemIndex]["quantity"]);
    ShoppingListCheckOffService.removeItem(itemIndex);
    buyItem.errorMessage = buyItem.toBeBoughtItems.length == 0 ? "Everything is bought!" : "";
  }
}


AlreadyBoughtController.$inject = ['ShoppingListCheckOffService'];
function AlreadyBoughtController(ShoppingListCheckOffService) {
  var itemList = this;
  itemList.items = ShoppingListCheckOffService.getItems();
  itemList.errorMessage = itemList.items.length == 0 ? "Nothing bought yet." : "";
}

AddItemController.$inject = ['ShoppingListCheckOffService'];
function AddItemController(ShoppingListCheckOffService) {
  var addItem = this;
  addItem.itemName = "";
  addItem.itemQuantity = "";
  addItem.items = ShoppingListCheckOffService.getToBeBoughtItems();

  addItem.addItem = function () {
    ShoppingListCheckOffService.addItemIntotoBeBoughtItems(addItem.itemName, addItem.itemQuantity);
  }
}


function ShoppingListCheckOffService() {
  var service = this;

  // List of shopping items
  var items = [];
  var toBeBoughtItems = [{name: "cookies", quantity: 10}, {name: "cookies", quantity: 10}];

  service.addItem = function (itemIndex, name, quantity) {
    var item = {
      name: name,
      quantity: quantity,
      itemIndex: itemIndex
    };
    items.push(item);
  };

  service.addItemIntotoBeBoughtItems = function (name, quantity) {
    var item = {
      name: name,
      quantity: quantity,
    };
    toBeBoughtItems.push(item);
  };

  service.removeItem = function (itemIndex) {
    toBeBoughtItems.splice(itemIndex, 1);
  };

  service.getItems = function () {
    return items;
  };

  service.getToBeBoughtItems = function () {
    return toBeBoughtItems;
  };

  console.log(items);
}

})();
