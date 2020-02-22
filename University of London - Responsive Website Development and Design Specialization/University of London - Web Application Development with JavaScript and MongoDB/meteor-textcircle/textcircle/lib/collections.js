// code sent to client and server
// which gets loaded before anything else (since it is in the lib folder)

this.Documents = new Mongo.Collection("documents");
EditingUsers = new Mongo.Collection("editingUsers");
Comments = new Mongo.Collection("comments");

// set up a schema controlling the allowable 
// structure of comment objects
Comments.attachSchema(new SimpleSchema({
  title: {
    type: String,
    label: "Title",
    max: 200
  },
  body:{
    type: String,
    label: "Comment",
    max: 1000  	
  },
  docid:{
  	type: String, 
  }, 
  owner:{
  	type: String, 
  }, 
  
}));
