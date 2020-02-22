Messages = new Mongo.Collection("messages");
Chatrooms = new Mongo.Collection("chatrooms");

// set up a schema controlling the allowable
// structure of Chatroom objects
Chatrooms.attachSchema(new SimpleSchema({
    title: {
        type: String,
        label: "Title",
        max: 200
    },
    description: {
        type: String,
        label: "Description",
        max: 1000
    },
    createdBy: {
        type: String,
        autoform: {
            type: "hidden",
            label: false
        },
        defaultValue: 'anon'
    },
}));

Messages.attachSchema(new SimpleSchema({
    messageText: {
        type: String,
        label: "Message",
        max: 200
    },
    nickname: {
        type: String,
        autoform: {
            type: "hidden",
            label: false
        },
        defaultValue: '0'
    },
    createdOn: {
        type: Date,
        autoform: {
            type: "hidden",
            label: false
        },
        defaultValue: new Date(),
    },
    chatroomId: {
        type: String,
        autoform: {
            type: "hidden",
            label: false
        },
        defaultValue: '0'
    },

}));
