points = new Meteor.Collection('pointsCollection');

Meteor.methods({
  'clear': function () {
    points.remove({});
  }
});
