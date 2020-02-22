Messages = new Mongo.Collection("messages");

if (Meteor.isClient){

    // this will configure the sign up field so it
    // they only need a username
    Accounts.ui.config({
      passwordSignupFields: 'USERNAME_ONLY',
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

    Template.messageForm.events({
        // this event listener is triggered when they click on
        // the post! button on the message form template

        'click .js-save-message':function(event){
            var messageText = $('#message-text-input').val();
            // notice how tihs has changed since the lsat time
            // now we read the username from the Meteor.user()
            var messageNickname = "Anon";
            if (Meteor.user()){
                messageNickname = Meteor.user().username;
            }
            var message = {messageText:messageText,
                            nickname:messageNickname,
                            createdOn:new Date()};
          Meteor.call('insertMessage', message, function(err, res){
                        if (!res){
                            // nothing came back - warn the user with alert
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
        // this helper provides the list of messages for the
        // messgaeList template
        messages:function(){
            if(Meteor.user()){
                Meteor.subscribe("messages");
                return Messages.find({}, {sort: {createdOn: -1}});
            }

        }
    });

}
