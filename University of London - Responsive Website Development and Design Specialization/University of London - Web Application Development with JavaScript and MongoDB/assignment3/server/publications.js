
// public sets of editing users
Meteor.publish("messages", function(){
    if(this.userId){
    	return Messages.find({});
    } else {
    	return;
    }
   

	
})
