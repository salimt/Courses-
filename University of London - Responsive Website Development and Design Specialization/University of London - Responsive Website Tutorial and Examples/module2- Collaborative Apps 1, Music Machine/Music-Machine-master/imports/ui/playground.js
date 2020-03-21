//playground.js

import './playground.html';
import { Template } from 'meteor/templating';
import '../api/musicMachine.js';
import '../api/maxim.js';
import { _ } from 'meteor/underscore'

// it replaces
// Original line - pg_context = new webkitAudioContext() || new AudioContext;
// Replaced by below try except block

try {
    pg_context = new webkitAudioContext();
    } catch (e){
    if (e instanceof ReferenceError) {
      pg_context = new AudioContext;
    }
} // catch

//Now we can create an instance of our waveform generator and play it.

// waveform = new Synth(pg_context);

// Add instruments
drums = new Maxim();
bass = new Maxim();
arp = new Maxim();
cymbal = new Maxim();
snaredrum = new Maxim();

// Load instruments
drumPlayer = drums.loadFile("/drums1.wav");
drumPlayer.loop
bassPlayer = bass.loadFile("/bassline.wav");
bassPlayer.loop
arpPlayer = arp.loadFile("/arp.wav");
arpPlayer.loop
cymbalPlayer = cymbal.loadFile("/cymbal1.wav");
cymbalPlayer.loop
snaredrumPlayer = snaredrum.loadFile("/snaredrum1.wav");
snaredrumPlayer.loop

// Play and stop functions
stopOrPlayDrums = function(volume) {
  drumPlayer.volume(volume);
}

stopOrPlayBassline = function(volume) {
  bassPlayer.volume(volume)
}

stopOrPlayArp = function(volume) {
  arpPlayer.volume(volume)
}

stopOrPlayCymbal = function(volume) {
  cymbalPlayer.volume(volume)
}


stopOrPlaySnaredrum = function(volume) {
  snaredrumPlayer.volume(volume)
}


playAll = function() {
	drumPlayer.play();
	bassPlayer.play();
  arpPlayer.play();
  cymbalPlayer.play();
  snaredrumPlayer.play();
}


stopAll = function() {
	drumPlayer.stop();
	bassPlayer.stop();
  arpPlayer.stop();
  cymbalPlayer.stop();
  snaredrumPlayer.stop();
}


setSpeed = function(speed) {
	drumPlayer.speed(speed);
	bassPlayer.speed(speed);
	arpPlayer.speed(speed);
	cymbalPlayer.speed(speed);
	snaredrumPlayer.speed(speed);
}


Template.playground.helpers({

    "startdac": function () {
      var starter = MusicMachine.findOne();

      if (starter) {
        Session.set('startdac', starter.start)
        if (starter.start==1) {
          playAll();
        }
        else if (starter.start==0) {
          stopAll();
        }
      }
      return Session.get('startdac');
    },

    "drums": function () {
      var starter = MusicMachine.findOne();
      if (starter) {
        Session.set('drums', starter.drums)
        stopOrPlayDrums(starter.drums)
      }
      return Session.get('drums');
    },

    "bassline": function () {
      var starter = MusicMachine.findOne();
      if (starter) {
        Session.set('bassline', starter.bassline)
        stopOrPlayBassline(starter.bassline)
      }
      return Session.get('bassline');
    },

    "arp": function () {
      var starter = MusicMachine.findOne();
      if (starter) {
        Session.set('arp', starter.arp)
        stopOrPlayArp(starter.arp)
      }
      return Session.get('arp');
	},

    "cymbal": function () {
      var starter = MusicMachine.findOne();
      if (starter) {
        Session.set('cymbal', starter.cymbal)
        stopOrPlayCymbal(starter.cymbal)
      }
      return Session.get('cymbal');
    },
    
    "snaredrum" : function() {
      var starter = MusicMachine.findOne();
      if (starter) {
        Session.set('snaredrum', starter.snaredrum)
        stopOrPlaySnaredrum(starter.snaredrum)
      }
      return Session.get('snaredrum')
    },

    "sliderValue":  function() { 
      var slider = MusicMachine.findOne();
      if (slider) { 
        Template.instance().$('#slider').data('uiSlider').value(slider.slide);
        setSpeed(slider.slide/50);
        return slider.slide;
        }
    },
});

Template.playground.events({

    "click .js-masterButton": function () {
        var c = Session.get('startdac')
        var mach = MusicMachine.findOne({});
        if (c === 0) {
            Session.set('startdac', 1)
            MusicMachine.update({ _id: mach._id }, {$set: {start: 1}});
        } else {
            Session.set('startdac', 0)
            MusicMachine.update({ _id: mach._id }, {$set: {start: 0}});
        }
    },

    "click .js-controlDrums": function () {
        var c = Session.get('drums');
        var mach = MusicMachine.findOne({});

        if (c === 0){
            Session.set('drums', 1);
            MusicMachine.update({_id:mach._id}, {$set: {drums: 1}});
        } else {
            Session.set('drums', 0);
            MusicMachine.update({_id:mach._id}, {$set: {drums: 0}});
        }
    },

    "click .js-controlBass": function () {
        var c = Session.get('bassline');
        var mach = MusicMachine.findOne({});

        if (c === 0){
            Session.set('bassline', 1);
            MusicMachine.update({_id:mach._id}, {$set: {bassline: 1}});
        } else {
            Session.set('bassline', 0);
            MusicMachine.update({_id:mach._id}, {$set: {bassline: 0}});
        }
    },

    "click .js-controlArp": function () {
        var c = Session.get('arp');
        var mach = MusicMachine.findOne({});

        if (c === 0){
          Session.set('arp', 1);
          MusicMachine.update({_id:mach._id}, {$set: {arp: 1}});
        } else {
          Session.set('arp', 0);
          MusicMachine.update({_id:mach._id}, {$set: {arp: 0}});
        }
    },

    "click .js-controlCymbal": function () {
        var c = Session.get('cymbal');
        var mach = MusicMachine.findOne({});

        if (c === 0){
            Session.set('cymbal', 1);
            MusicMachine.update({_id:mach._id}, {$set: {cymbal: 1}});
        } else {
            Session.set('cymbal', 0);
            MusicMachine.update({_id:mach._id}, {$set: {cymbal: 0}});
        }
    },

    "click .js-controlSnaredrum": function () {
        var c = Session.get('snaredrum');
        var mach = MusicMachine.findOne({});

        if (c === 0){
            Session.set('snaredrum', 1);
            MusicMachine.update({_id:mach._id}, {$set: {snaredrum: 1}});
        } else {
            Session.set('snaredrum', 0);
            MusicMachine.update({_id:mach._id}, {$set: {snaredrum: 0}});
        }
    },

 });

 Template.playground.onRendered(function() {
	$('h2').hide();
    var handler = _.throttle(function(event, ui) {
        var val = MusicMachine.findOne({});
        MusicMachine.update({ _id: val._id }, {$set: {slide: ui.value}});
	}, 50, { leading: false });
	
    
    if (!this.$('#slider').data('uiSlider')) {
        $("#slider").slider({
            slide: handler,
            min: 0,
            max: 100
        });
    }
});
