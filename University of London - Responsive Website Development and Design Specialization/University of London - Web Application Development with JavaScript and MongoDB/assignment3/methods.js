Meteor.methods({
    'insertMessage':function(message){
        if (!Meteor.user()){
            return;
        }
        else {
            return Messages.insert(message);
        }
    }
})

Meteor.methods({
    'removeMessage':function(id){
        if (!Meteor.user()){
            return;
        }
        else {
            var msg = Messages.findOne({_id:id});
            if (msg.nickname == Meteor.user().username){
                    Messages.remove({_id:id});
                    return true;
            }
        }
    }
})
