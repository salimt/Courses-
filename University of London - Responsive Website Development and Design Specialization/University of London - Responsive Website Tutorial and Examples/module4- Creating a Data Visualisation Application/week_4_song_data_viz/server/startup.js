
// define a startup script that 
// reads the JSON data files from the filesystem 
// and inserts them into the database if needed

if (Meteor.isServer){
	Meteor.startup(function(){
		if (!Songs.findOne()){
		console.log("no songs yet... creating from filesystem");
		// pull in the NPM package 'fs' which provides
		// file system functions
		var fs = Meteor.npmRequire('fs');
		// get a list of files in the folder private/jsonfiles, which
		// ends up as assets/app/jsonfiles in the app once it is built
		var files = fs.readdirSync('./assets/app/jsonfiles/');
		// iterate the files, each of which should be a 
		// JSON file containing song data.
		var inserted_songs = 0;
		for (var i=0;i<files.length; i++){
		//for (var i=0;i<1; i++){

		 	var filename = 'jsonfiles/'+files[i];
		 	// in case the file does not exist, put it in a try catch
		 	try{
		 		var song = JSON.parse(Assets.getText(filename));
		 		// now flatten the rhythm and tonal features
		 		// into a single set of properties
		 		var single_features = {};
		 		var array_features = {};
		 		var string_features = {};

		 		rhythm_keys = Object.keys(song.rhythm);
      			tonal_keys = Object.keys(song.tonal);
      			for (var j=0;j<rhythm_keys.length;j++){
      				console.log("type of "+rhythm_keys[j]+" is "+typeof(song.rhythm[rhythm_keys[j]]));
      				// only use features that are numbers ... ignore arrays etc. 
      				if (typeof(song.rhythm[rhythm_keys[j]]) === "number"){
      					single_features[rhythm_keys[j]] = song.rhythm[rhythm_keys[j]];
      				}
      				if (typeof(song.rhythm[rhythm_keys[j]]) === "object" && 
      					song.rhythm[rhythm_keys[j]].length != undefined){// its an array
      					array_features[rhythm_keys[j]] = song.rhythm[rhythm_keys[j]];
      				}
      				if (typeof(song.rhythm[rhythm_keys[j]]) === "string"){
      					string_features[rhythm_keys[j]] = song.rhythm[rhythm_keys[j]];
      				}
      	
      			}
      			for (var j=0;j<tonal_keys.length;j++){
      				console.log("type of "+tonal_keys[j]+" is "+typeof(song.tonal[tonal_keys[j]]));
      				if (typeof(song.tonal[tonal_keys[j]]) === "number"){
      					single_features[tonal_keys[j]] = song.tonal[tonal_keys[j]];
      				}
      				if (typeof(song.tonal[tonal_keys[j]]) === "object" && 
      					song.tonal[tonal_keys[j]].length != undefined){// its an array
      					array_features[tonal_keys[j]] = song.tonal[tonal_keys[j]];
      				}
      				if (typeof(song.tonal[tonal_keys[j]]) === "string"){
      					string_features[tonal_keys[j]] = song.tonal[tonal_keys[j]];
      				}
      			}
		 		// insert the song to the DB:
		 		// 
		 		song.single_features = single_features;
		 		song.array_features = array_features;
		 		song.string_features = string_features;
		 		
		 		Songs.insert(song);
		 		inserted_songs ++;
		 	}catch (e){
		 		console.log("error parsing file "+filename);
		 	}
		}
		console.log("Inserted "+inserted_songs+" new songs...");
	}
	})
}
