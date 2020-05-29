// this collection contains all the songs
Songs = new Mongo.Collection("songs");
// this variable will store the visualisation so we can delete it when we need to 
var visjsobj;

var featureData = new Array();

if (Meteor.isClient){

////////////////////////////
///// helper functions for the vis control form
////////////////////////////
  Template.song_viz_controls.helpers({
    // returns an array of the names of all features of the requested type
    get_feature_names : function(type){
      var feat_field;
      if (type == "single"){
        feat_field = "single_features";
      }
      // pull an example song from the database
      // - we'll use this to find the names of all the single features
      song = Songs.findOne();
      if (song != undefined){// looks good! 
        // get an array of all the song feature names 
        // (an array of strings)
        features = Object.keys(song[feat_field]);
        features_a = new Array();
        // create a new array containing
        // objects that we can send to the template
        // since we can't send an array of strings to the template
        for (var i=0;i<features.length;i++){
            features_a[i] = {name:features[i]};
        }
        return features_a;
      }
      else {// no song available, return an empty array for politeness
        return [];
      }
    },
  });

////////////////////////////
///// helper functions for the feature list display template
////// (provide the data for that list of songs)
////////////////////////////

// helper that provides an array of feature_values
// for all songs of the currently selected type
// this is used to feed the template that displays the big list of 
// numbers
  Template.song_feature_list.helpers({
    "get_all_feature_values":function(){
      if (Session.get("feature") != undefined){
        var songs = Songs.find({});
        var features = new Array();
        var ind = 0;
        // build an array of data on the fly for the 
        // template consisting of 'feature' objects
        // describing the song and the value it has for this particular feature
        songs.forEach(function(song){
          //console.log(song);
            features[ind] = {
              artist:song.metadata.tags.artist,
              title:song.metadata.tags.title, 
              value:song[Session.get("feature")["type"]][Session.get("feature")["name"]]
            };
            ind ++;
        })
        return features;
      }   
      else {
        return [];
      }
    }
  })

////////////////////////////
///// event handlers for the viz control form
////////////////////////////

  Template.song_viz_controls.events({
    // event handler for when user changes the selected
    // option in the drop down list
    "change .js-select-single-feature":function(event){
      event.preventDefault();
      var feature = $(event.target).val();
      Session.set("feature", {name:feature, type:"single_features"});
    }, 
    // event handler for when user changes the selected
    // option in the drop down list
    "change .js-select-single-feature-comp":function(event){
      event.preventDefault();
      var feature = $(event.target).val();
      Session.set("featureComp", {name:feature, type:"single_features"});
    }, 
    // event handler for when the user clicks on the 
    // blobs button
     "click .js-show-blobs":function(event){
      event.preventDefault();
      initBlobVis();
    }, 
    // event handler for when the user clicks on the 
    // timeline button
     "click .js-show-timeline":function(event){
      event.preventDefault();
      initDateVis();
    }, 

    // event handler for when the user clicks on the 
    // timeline button
     "click .js-show-3d":function(event){
      event.preventDefault();
      init3dVis();
    }, 
  }); 
}



////////////////////////////
///// functions that set up and display the visualisation
////////////////////////////


// function that creates a new timeline visualisation
function initDateVis(){
  // clear out the old visualisation if needed
  if (visjsobj != undefined){
    visjsobj.destroy();
  }
  var songs = Songs.find({});
  var ind = 0;
  // generate an array of items
  // from the songs collection
  // where each item describes a song plus the currently selected
  // feature
  var items = new Array();

  var highestVal = 0;
  var lowestVal = 0;
  var highestLabel = "";
  var lowestLabel = "";

  // iterate the songs collection, converting each song into a simple
  // object that the visualiser understands
  songs.forEach(function(song){
    if (song.metadata.tags.date != undefined && 
      song.metadata.tags.date[0] != undefined ){
      var label = "ind: "+ind;
      if (song.metadata.tags.title != undefined){// we have a title
        label = song.metadata.tags.artist[0] + " - " + 
        song.metadata.tags.title[0];
      }  
      var value = song[Session.get("feature")["type"]][Session.get("feature")["name"]];
      var valComp = Session.get("featureComp");
      //var valComp = song.metadata.tags.date[0] + "-01-01";
      console.log("dasdada" + valComp);

      var xVal = valComp != undefined ? song[Session.get("featureComp")["type"]][Session.get("featureComp")["name"]] : song.metadata.tags.date[0] + "-01-01";

      if (value>highestVal) {
        highestVal = value;
        highestLabel = label;
      } if (value<lowestVal || lowestVal==0) {
        lowestVal = value;
        lowestLabel = label;
      } 
      
      // here we create the actual object for the visualiser
      // and put it into the items array
      items[ind] = {
        x: xVal, 
        y: value, 
        // slighlty hacky label -- check out the vis-label
        // class in song_data_viz.css 
        label:{content:label, className:'vis-label', xOffset:-5}, 
      };
      ind ++ ;
  }
  });
  // set up the data plotter
  var options = {
    style:'bar', 
  };
  // get the div from the DOM that we are going to 
  // put our graph into 
  var container = document.getElementById('visjs');
  showStats(highestVal, highestLabel, lowestVal, lowestLabel);
  // create the graph
  visjsobj = new vis.Graph2d(container, items, options);
  // tell the graph to set up its axes so all data points are shown
  visjsobj.fit();

}

function showStats(highestVal, highestLabel, lowestVal, lowestLabel) {
  document.getElementById("mytableid").innerHTML = ("<b>Highest Rated Song: <font color='red'>" + highestLabel + "</font> with a value of <font color='red'>" + highestVal + "</font>.<br><br>Lowest Rated Song: <font color='red'>" + lowestLabel + "</font> with a value of <font color='red'>" + lowestVal + "</font>.<br><br>Average of the set: <font color='red'>" + ((highestVal+lowestVal)/2)) + "</font>.<br><br></b>";

}

// function that creates a new blobby visualisation
function initBlobVis(){
  // clear out the old visualisation if needed
  if (visjsobj != undefined){
    visjsobj.destroy();
  }
  // find all songs from the Songs collection
  var songs = Songs.find({});
  var nodes = new Array();
  var ind = 0;

  var highestVal = 0;
  var lowestVal = 0;
  var highestLabel = "";
  var lowestLabel = "";
  // iterate the songs, converting each song into 
  // a node object that the visualiser can understand
    songs.forEach(function(song){
      // set up a label with the song title and artist
     var label = "ind: "+ind;
     if (song.metadata.tags.title != undefined){// we have a title
          label = song.metadata.tags.artist[0] + " - " + 
          song.metadata.tags.title[0];
      } 
      // figure out the value of this feature for this song
      var value = song[Session.get("feature")["type"]][Session.get("feature")["name"]];

      if (value>highestVal) {
        highestVal = value;
        highestLabel = label;
      } if (value<lowestVal || lowestVal==0) {
        lowestVal = value;
        lowestLabel = label;
      } 

      // create the node and store it to the nodes array
        nodes[ind] = {
          id:ind, 
          label:label, 
          value:value,
        }
        ind ++;
    })
    // edges are used to connect nodes together. 
    // we don't need these for now...
    edges =[
    ];
    // this data will be used to create the visualisation
    var data = {
      nodes: nodes,
      edges: edges
    };
    // options for the visualisation
     var options = {
      nodes: {
        shape: 'dot',
      }
    };
    // get the div from the dom that we'll put the visualisation into
    container = document.getElementById('visjs');
    showStats(highestVal, highestLabel, lowestVal, lowestLabel);
    // create the visualisation
    visjsobj = new vis.Network(container, data, options);
}

