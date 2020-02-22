// code that is shared between client and server, i.e. sent to both

// method definitions
Meteor.methods({
  // adding new comments
  addComment:function(comment){
    console.log("addComment method running!");
    if (this.userId){// we have a user
      comment.owner = this.userId;
        return Comments.insert(comment);
    }
    console.log("did not add comment as user not logged in.");
    return;
  },

  // adding new documents
  addDoc:function(){
    var doc;
    if (!this.userId){// not logged in
      return;
    }
    else {
      doc = {owner:this.userId, createdOn:new Date(),
            title:"my new doc"};
      var id = Documents.insert(doc);
      console.log("addDoc method: got an id "+id);
      return id;
    }
  },
  // changing doc privacy settings
  updateDocPrivacy:function(doc){
    console.log("updateDocPrivacy method");
    console.log(doc);
    var realDoc = Documents.findOne({_id:doc._id, owner:this.userId});
    if (realDoc){
      realDoc.isPrivate = doc.isPrivate;
      Documents.update({_id:doc._id}, realDoc);
    }
  },
// adding editors to a document
  addEditingUser:function(docid){
    var doc, user, eusers;
    doc = Documents.findOne({_id:docid});
    if (!doc){return;}// no doc give up
    if (!this.userId){return;}// no logged in user give up
    // now I have a doc and possibly a user
    user = Meteor.user().profile;
    eusers = EditingUsers.findOne({docid:doc._id});
    if (!eusers){
      eusers = {
        docid:doc._id,
        users:{},
      };
    }
    user.lastEdit = new Date();
    eusers.users[this.userId] = user;

    EditingUsers.upsert({_id:eusers._id}, eusers);
  }
})
