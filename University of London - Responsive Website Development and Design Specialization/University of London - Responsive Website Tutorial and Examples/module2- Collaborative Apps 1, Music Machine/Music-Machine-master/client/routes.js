import { Router } from 'meteor/iron:router';

Router.configure({
    layoutTemplate: "appLayout"
})

Router.route('/', function(){
    this.render("playground", {to: "main"})
})
