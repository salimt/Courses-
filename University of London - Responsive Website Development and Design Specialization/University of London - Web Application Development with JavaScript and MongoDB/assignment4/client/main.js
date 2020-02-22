// this will configure the sign up field so it
// they only need a username
Accounts.ui.config({
  passwordSignupFields: 'USERNAME_ONLY',
});


Template.chatroomList.events({
    'click .js-toggle-chatform':function(){
        $('#chatroomForm').toggle();
    }
});

Template.chatroomList.helpers({
    chatrooms:function(){
        Meteor.subscribe("chatrooms");
        return Chatrooms.find();
    }
});

Template.messageList.events({
    'click .js-del-message':function(){
        Meteor.call('removeMessage', this._id, function(err, res){
            if (!res){
                alert('Can only delete your own ones...');
            }
        });
    }
});

Template.header.helpers({
    nickname:function(){
        if (Meteor.user()){
            return Meteor.user().username;
        }
    },
});

Template.messageList.helpers({
    messages:function(chatroomId){
        if (Meteor.user() && chatroomId){
            return Messages.find({chatroomId:chatroomId}, {sort: {createdOn: -1}});
        }
    }
});
