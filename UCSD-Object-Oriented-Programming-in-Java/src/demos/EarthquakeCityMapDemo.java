package demos;

//Java utilities libraries
import java.util.ArrayList;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.core.Coordinate;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMapDemo
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMapDemo extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	//private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		// Assume online
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
//		map = new UnfoldingMap(this, 200, 50, 700, 500, new OpenStreetMap.OpenStreetMapProvider() );
//	    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));

	    map.zoomToLevel(1);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    
	    /* For demo purposes */
	    
	    /* Note that different parts of the video are included below in comments */
	    
	    //STAGE 1: Markers (not associated with Features)
	    
	    // Create a marker at a specific location in the world, and format it
//	    Location valLoc = new Location(-38.14f,-73.03f);
//	    Marker val = new SimplePointMarker(valLoc);
//	    map.addMarker(val);
	    
	    //STAGE 2: Features with rich data, then Marker
	    
	    // First create Feature for details of Valdivia earthquake
//	    Location valLoc = new Location(-38.14f,-73.03f);
//	    PointFeature valEq = new PointFeature(valLoc);
//	    valEq.addProperty("title", "Valdivia, Chile");
//	    valEq.addProperty("magnitude", "9.5");
//	    valEq.addProperty("date", "May 22, 1960");
//	    
//	    Marker valMk = new SimplePointMarker(valLoc, valEq.getProperties());
//	    map.addMarker(valMk);
	    
	   //STAGE 3: List of Features, then list of Markers (ADTs)
	   // cf. http://earthquake.usgs.gov/earthquakes/world/10_largest_world.php
	   // Goal: display all earthquakes at or above magnitude 9.0
	    
	    PointFeature valdiviaEq = new PointFeature(new Location(-38.14f,-73.03f));
	    valdiviaEq.addProperty("title", "Valdivia, Chile");
	    valdiviaEq.addProperty("magnitude", "9.5");
	    valdiviaEq.addProperty("date", "March 22, 1960");
	    valdiviaEq.addProperty("year", 1960);
	    
	    PointFeature alaskaEq = new PointFeature(new Location(61.02f,-147.65f));
	    alaskaEq.addProperty("title", "1964 Great Alaska Earthquake");
	    alaskaEq.addProperty("magnitude", "9.2");
	    alaskaEq.addProperty("date", "March 28, 1964"); 
	    alaskaEq.addProperty("year", 1964);

	    PointFeature sumatraEq = new PointFeature(new Location(3.30f,95.78f));
	    sumatraEq.addProperty("title", "Off the West Coast of Northern Sumatra");
	    sumatraEq.addProperty("magnitude", "9.1");
	    sumatraEq.addProperty("date", "February 26, 2004");
	    sumatraEq.addProperty("year", 2004);

	    
	    PointFeature japanEq = new PointFeature(new Location(38.322f,142.369f));
	    japanEq.addProperty("title", "Near the East Coast of Honshu, Japan");
	    japanEq.addProperty("magnitude", "9.0");
	    japanEq.addProperty("date", "March 11, 2011");
	    japanEq.addProperty("year", 2011);

	    
	    PointFeature kamchatkaEq = new PointFeature(new Location(52.76f,160.06f));
	    kamchatkaEq.addProperty("title", "Kamchatka");
	    kamchatkaEq.addProperty("magnitude", "9.0");
	    kamchatkaEq.addProperty("date", "November 4, 1952");
	    kamchatkaEq.addProperty("year", 1952);

	    
	    List<PointFeature> bigEarthquakes = new ArrayList<PointFeature>();
	    bigEarthquakes.add(valdiviaEq);
	    bigEarthquakes.add(alaskaEq);
	    bigEarthquakes.add(sumatraEq);
	    bigEarthquakes.add(japanEq);
	    bigEarthquakes.add(kamchatkaEq);
//	    
	    List<Marker> markers = new ArrayList<Marker>();
	    for (PointFeature eq: bigEarthquakes) {
	    	markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
	    }
	    map.addMarkers(markers);
//	    
//	    
//	    // STAGE 4: format markers on whether "historical" or "recent"
//	    // Here is an example of how to use Processing's color method to generate 
//	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    int gray = color(150,150,150);
	    
	    for (Marker mk :markers) {
	    	if ( (int) mk.getProperty("year") > 2000 ) {
	    		mk.setColor(yellow);
	    	}
	    	else {
	    		mk.setColor(gray);
	    	}
	    }
//	    
	    
	    //List<Marker> markers = new ArrayList<Marker>();
	    
	    // The List you will populate with new SimplePointMarkers
	    //List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    //List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    //if (earthquakes.size() > 0) {
	    //	PointFeature f = earthquakes.get(0);
	    //	System.out.println(f.getProperties());
	    //	Object magObj = f.getProperty("magnitude");
	    //	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    //}
	    


	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
	
	}
}
