Meteor.methods({
    'insertMessage':function(message){
        console.log("If you manage to call the method, you'll see this message in the server console");
        if (!Meteor.user()){
            return;
        }
        else {
            return Messages.insert(message);
        }
    }
})
