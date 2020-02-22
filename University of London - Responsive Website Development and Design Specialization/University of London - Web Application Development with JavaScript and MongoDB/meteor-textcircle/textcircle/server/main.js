// code that is only sent to the server. 

Meteor.startup(function () {
  // create a starter doc if necessary
  if (!Documents.findOne()){// no documents yet!
      Documents.insert({title:"my new document"});
  }
});


// publish read access to collections

// all visible docs 
Meteor.publish("documents", function(){
  return Documents.find({
   $or:[
    {isPrivate:{$ne:true}}, 
    {owner:this.userId}
    ] 
  });
})  
// users editing docs
Meteor.publish("editingUsers", function(){
  return EditingUsers.find();
})

// coments on docs
Meteor.publish("comments", function(){
  return Comments.find();
})