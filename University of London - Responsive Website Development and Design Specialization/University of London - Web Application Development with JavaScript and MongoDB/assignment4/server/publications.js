// the messages publicaction now takes a parameter
// so we can limit the set of messages
// that get sent to the client in one go.
Meteor.publish('messages.filtered',function (chatroomId) {
    if (this.userId){

        return Messages.find({});
        
        
    }
});


Meteor.publish("chatrooms", function(){
    if (this.userId){
        return Chatrooms.find({});
    }
});
