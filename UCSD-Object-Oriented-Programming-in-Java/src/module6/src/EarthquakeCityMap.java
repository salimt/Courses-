import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author salimt
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	

	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;

	// A List of airport and routes markers
    private List<Marker> airportList;
    List<Marker> routeList;

	private CommonMarker lastSelected;
	private CommonMarker lastClicked;


	public void setup() {
		// (1) Initializing canvas and map tiles
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new OpenStreetMap.OpenStreetMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
		// one of the lines below.  This will work whether you are online or offline
		//earthquakesURL = "test1.atom";
		//earthquakesURL = "test2.atom";

        // get features from airport data
        List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");

        // list for markers, hashmap for quicker access when matching with routes
        airportList = new ArrayList<Marker>();
        HashMap <Integer, Location> airports = new HashMap<Integer, Location>();

        // create markers from features
        for(PointFeature feature : features) {
            AirportMarker m = new AirportMarker(feature);

            m.setRadius(5);
            airportList.add(m);

            // put airport in hashmap with OpenFlights unique id for key
            airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
        }

        // parse route data
        List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
        routeList = new ArrayList<Marker>();
        for(ShapeFeature route : routes) {

            // get source and destination airportIds
            int source = Integer.parseInt((String)route.getProperty("source"));
            int dest = Integer.parseInt((String)route.getProperty("destination"));

            // get locations for airports on route
            if(airports.containsKey(source) && airports.containsKey(dest)) {
                route.addLocation(airports.get(source));
                route.addLocation(airports.get(dest));
            }

            SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());


            //UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
            routeList.add(sl);
        }

		// Uncomment this line to take the quiz
		earthquakesURL = "quiz2.atom";

		// (2) Reading in earthquake data and geometric properties
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    // could be used for debugging
	    printQuakes();
	 		
	    // (3) Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
        map.addMarkers(airportList);
        map.addMarkers(routeList);
	    
	    sortAndPrint(5);

	}  // End setup


    boolean clicked = false;
    boolean forRoute = false;
	public void draw() {
		background(0);
		map.draw();
		addKey();

        showButtons();
        if(mousePressed && clicked==false && (mouseX > 25 && mouseX < 25 + 150 && mouseY > 320 && mouseY < 320 + 60)){ clicked=true; }
        else if(mousePressed && clicked==true && (mouseX > 25 && mouseX < 25 + 150 && mouseY > 320 && mouseY < 320 + 60)){ clicked = false;}
        else if(mousePressed && clicked==true && forRoute==false &&(mouseX > 25 && mouseX < 25 + 150 && mouseY > 400 && mouseY < 400 + 60)){ forRoute=true; }
        else if(mousePressed && clicked==true && forRoute==true && (mouseX > 25 && mouseX < 25 + 150 && mouseY > 400 && mouseY < 400 + 60)){ forRoute=false; }


        if(lastClicked!=null) {
            showInformation();
        }
	}

    private void sortAndPrint(int numToPrint){
        List<EarthquakeMarker> eq = new ArrayList <>();
        for(Marker m: quakeMarkers){
            eq.add((EarthquakeMarker) m);
        }
        Collections.sort(eq);

        for(int i=0; i<numToPrint; i++){
            if(i==quakeMarkers.size()){
                break;
            }System.out.println(eq.get(i));
        }

    }
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
    public void mouseMoved()
    {
        // clear the last selection
        if (lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;

        }
        selectMarkerIfHover(quakeMarkers);
        selectMarkerIfHover(cityMarkers);
        //loop();
    }

	// If there is a marker selected
    private void selectMarkerIfHover(List<Marker> markers)
    {
        // Abort if there's already a marker selected
        if (lastSelected != null) {
            return;
        }

        for (Marker m : markers)
        {
            CommonMarker marker = (CommonMarker)m;
            if (marker.isInside(map,  mouseX, mouseY)) {
                lastSelected = marker;
                marker.setSelected(true);
                return;
            }
        }
    }

	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked()
	{
        if(lastSelected!=null){
            checkEarthquakesForClick();
            checkCitiesForClick();
        }else if(!clicked){
            unhideMarkers();
            forRoute=false;
            lastClicked=null;
        }if(clicked){
            hideAllMarkers(true);
            showInformation();
        }
	}
	
	// Helper method that will check if a city marker was clicked on
	// and respond appropriately
	private void checkCitiesForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker marker : cityMarkers) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)marker;
				// Hide all the other earthquakes and hide
				for (Marker mhide : cityMarkers) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker mhide : quakeMarkers) {
					EarthquakeMarker quakeMarker = (EarthquakeMarker)mhide;
					if (quakeMarker.getDistanceTo(marker.getLocation()) 
							> quakeMarker.threatCircle()) {
						quakeMarker.setHidden(true);
					}
				}
				return;
			}
		}		
	}
	
	// Helper method that will check if an earthquake marker was clicked on
	// and respond appropriately
	private void checkEarthquakesForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : quakeMarkers) {
			EarthquakeMarker marker = (EarthquakeMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
				// Hide all the other earthquakes and hide
				for (Marker mhide : quakeMarkers) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker mhide : cityMarkers) {
                    if (mhide.getDistanceTo(marker.getLocation())
                            > marker.threatCircle()) {
                        mhide.setHidden(true);
                    }
                }
				return;
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(false);
		}
			
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
	}

	// helper method to draw key in GUI
	private void addKey() {

	    fill(255);
	    text("@author: salimt", 600,25);

	    if(!clicked) {
            // Remember you can use Processing's graphics methods here
            fill(255, 250, 240);

            int xbase = 25;
            int ybase = 50;

            rect(xbase, ybase, 150, 250);

            fill(0);
            textAlign(LEFT, CENTER);
            textSize(12);
            text("Earthquake Key", xbase + 25, ybase + 25);

            fill(150, 30, 30);
            int tri_xbase = xbase + 35;
            int tri_ybase = ybase + 50;
            triangle(tri_xbase, tri_ybase - CityMarker.TRI_SIZE, tri_xbase - CityMarker.TRI_SIZE,
                    tri_ybase + CityMarker.TRI_SIZE, tri_xbase + CityMarker.TRI_SIZE,
                    tri_ybase + CityMarker.TRI_SIZE);

            fill(0, 0, 0);
            textAlign(LEFT, CENTER);
            text("City Marker", tri_xbase + 15, tri_ybase);

            text("Land Quake", xbase + 50, ybase + 70);
            text("Ocean Quake", xbase + 50, ybase + 90);
            text("Size ~ Magnitude", xbase + 25, ybase + 110);

            fill(255, 255, 255);
            ellipse(xbase + 35,
                    ybase + 70,
                    10,
                    10);
            rect(xbase + 35 - 5, ybase + 90 - 5, 10, 10);

            fill(color(255, 255, 0));
            ellipse(xbase + 35, ybase + 140, 12, 12);
            fill(color(0, 0, 255));
            ellipse(xbase + 35, ybase + 160, 12, 12);
            fill(color(255, 0, 0));
            ellipse(xbase + 35, ybase + 180, 12, 12);

            textAlign(LEFT, CENTER);
            fill(0, 0, 0);
            text("Shallow", xbase + 50, ybase + 140);
            text("Intermediate", xbase + 50, ybase + 160);
            text("Deep", xbase + 50, ybase + 180);

            text("Past hour", xbase + 50, ybase + 200);

            fill(255, 255, 255);
            int centerx = xbase + 35;
            int centery = ybase + 200;
            ellipse(centerx, centery, 12, 12);

            strokeWeight(2);
            line(centerx - 8, centery - 8, centerx + 8, centery + 8);
            line(centerx - 8, centery + 8, centerx + 8, centery - 8);
        }else{
            fill(255, 250, 240);;
            rect(25, 50, 150, 250);
            textAlign(LEFT, CENTER);
            textSize(12);
            fill(11);
            ellipse(25+35, 50+50, 5, 5);
            text("Airport", 25+50, 50+50);
            text("Route", 25+50, 130);
            fill(130);
            line(50, 130, 70, 130);
        }

	}

    // loop over and hide/unhide airport and route lists
    private void hideMarkers(boolean status) {
        for(Marker marker : airportList){
            marker.setHidden(status);
        }if(forRoute && clicked){
            for(Marker marker : routeList){
                marker.setHidden(false);
            }
        }if(!forRoute || !clicked){
            for(Marker marker : routeList){
                marker.setHidden(true);
            }
        }
    }

    // loop over and hide quake/city markers
    private void hideAllMarkers(boolean status){
        for(Marker marker : quakeMarkers) {
            marker.setHidden(status);
        }
        for(Marker marker : cityMarkers) {
            marker.setHidden(status);
        }
    }

    // shows airport/airport route buttons on click
	private void showButtons(){
        fill(255, 250, 240);
        rect(25, 320, 150, 60);

        fill(0);
        textAlign(LEFT, CENTER);
        textSize(13);

        if(clicked) {
            text("HIDE AIRPORTS", 25+25, 320+25);
            fill(255, 250, 240);
            rect(25, 400, 150, 60);
            fill(0);
            if(forRoute==false){
                text("SHOW AIRPORT ROUTES", 25, 400+25);
            }else if(forRoute){
                text("HIDE AIRPORT ROUTES", 30, 400+25);
            }hideMarkers(false);

        }if(!clicked){
            text("SHOW AIRPORTS", 25+25, 320+25);
            hideMarkers(true);
        }
    }

    // shows information for clicked earthquake/city
	private void showInformation(){
	    if(!clicked){
            fill(255, 250, 240);
            int xbase = 25;
            int ybase = 400;
            rect(xbase, ybase, 150, 250);

            if(lastClicked instanceof CityMarker){
                fill(0);
                textAlign(LEFT, CENTER);
                textSize(13);
                text(lastClicked.getProperty("name").toString(), xbase+25, ybase+25);
                textSize(11);

                int numOfEQs = 0;
                float totalMag = 0;
                Marker mostRecent = null;
                for(Marker eq: quakeMarkers){
                    if(!eq.isHidden()){
                        numOfEQs++;
                        totalMag+=Float.parseFloat(eq.getProperty("magnitude").toString());
                        if(eq.getProperty("age").equals("Past Hour")){
                            mostRecent = eq;
                        }else if(eq.getProperty("age").equals("Past Day")){
                            mostRecent = eq;
                        }if(mostRecent == null){ mostRecent = eq; }
                    }
                }
                fill(0);
                text("Number of Nearby EQs: ", xbase+5, ybase+85);
                text("Avg. Magnitude: ", xbase+5, ybase+110);
                text("The Most Recent EQ: ", xbase+5, ybase+135);
                fill(255,0,0);
                text(numOfEQs, textWidth("Number of Nearby EQs: ")+xbase+5, ybase+85);
                text(totalMag/numOfEQs, textWidth("Avg. Magnitude: ")+xbase+5, ybase+110);
                if(mostRecent==null){ text("There are none.", xbase+5, ybase+160); }
                else{text(mostRecent.getProperty("title").toString(), xbase+5, ybase+160); }
            }else{
                fill(0);
                EarthquakeMarker eqMarker = (EarthquakeMarker)lastClicked;
                text(eqMarker.getTitle(), xbase+2, ybase+25);

                text("Cities that are affected:", xbase+5, ybase+85);
                int ybasse = ybase+105;
                int num = 1;
                fill(255,0,0);
                for(Marker cm: cityMarkers){
                    if(!cm.isHidden()){
                        text(num + "- " + cm.getProperty("name").toString() + ", " + cm.getProperty("country"), xbase+10, ybasse);
                        ybasse+=20;
                        num++;
                    }
                }if(num==1){ text("None are affected.", xbase+10, ybasse); }
            }
        }

    }
	
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.
	private boolean isLand(PointFeature earthquake) {
		
		// IMPLEMENT THIS: loop over all countries to check if location is in any of them
		// If it is, add 1 to the entry in countryQuakes corresponding to this country.
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		
		// not inside any country
		return false;
	}
	
	// prints countries with number of earthquakes
	// You will want to loop through the country markers or country features
	// (either will work) and then for each country, loop through
	// the quakes to count how many occurred in that country.
	// Recall that the country markers have a "name" property, 
	// And LandQuakeMarkers have a "country" property set.
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers)
			{
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
	}
	
	
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake feature if 
	// it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

}
