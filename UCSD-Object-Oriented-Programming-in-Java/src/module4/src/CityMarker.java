import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author salimt
 *
 */
public class CityMarker extends SimplePointMarker {
	
	// The size of the triangle marker
	// It's a good idea to use this variable in your draw method
	public static final int TRI_SIZE = 5;  
	
	public CityMarker(Location location) {
		super(location);
	}
	
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	
	
	/**
	 * Implementation of method to draw marker on the map.
	 */
	public void draw(PGraphics pg, float x, float y) {
		// Save previous drawing style
		pg.pushStyle();
		
		// TODO: Add code to draw a triangle to represent the CityMarker
		// HINT: pg is the graphics object on which you call the graphics
		// methods.  e.g. pg.fill(255, 0, 0) will set the color to red
		// x and y are the center of the object to draw. 
		// They will be used to calculate the coordinates to pass
		// into any shape drawing methods.  
		// e.g. pg.rect(x, y, 10, 10) will draw a 10x10 square
		// whose upper left corner is at position x, y
		// Check out the processing documentation for more methods
        pg.fill(165,42,42);
        pg.triangle(x-TRI_SIZE, y+TRI_SIZE, x, y-TRI_SIZE,x+TRI_SIZE,y+TRI_SIZE);

		
		// Restore previous drawing style
		pg.popStyle();
	}
	
	/* Local getters for some city properties.  You might not need these 
	 * in module 4. 	 */
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	
	public float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}
	
}
