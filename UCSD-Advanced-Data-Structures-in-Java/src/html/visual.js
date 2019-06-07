var delay = 300;
var map, markers;
var markerURL = "http://maps.google.com/mapfiles/kml/paddle/red-diamond-lv.png";
var destURL = "http://maps.google.com/mapfiles/kml/pal2/icon5.png";

function visualizeSearch(mapParam, markersParam) {
	markers = markersParam;
	map = mapParam;	
	drop();
}

function displayMarker(marker, timeout, URL) {
	window.setTimeout(function() {
		marker.setIcon(URL);
		marker.setMap(map);
	}, timeout);
}

function drop() {
	var i;
	for(i = 1; i < markers.length - 1; ++i) {
		displayMarker(markers[i], i*delay, markerURL);
	}
	displayMarker(markers[markers.length - 1], i*delay, destURL);
	i++;
	displayAlert(markers.length, i*delay);
}

function displayAlert(length, delay) {
	////window.setTimeout(function() {
		alert(length + " nodes visited in search.");
	//}, delay);
}