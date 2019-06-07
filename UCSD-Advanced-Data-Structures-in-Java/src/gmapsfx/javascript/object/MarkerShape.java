package gmapsfx.javascript.object;

import gmapsfx.javascript.JavascriptArray;
import gmapsfx.javascript.JavascriptObject;

public class MarkerShape extends JavascriptObject {
    private JavascriptArray coords;
    private String type;

    public MarkerShape() {
    	super(GMapObjectType.OBJECT);
    }

    public MarkerShape coords(JavascriptArray coords) {
    	setProperty("coords", coords);
    	this.coords = coords;
    	return this;
    }

    public MarkerShape type(String type) {
    	setProperty("type", type);
    	this.type = type;
        return this;
    }
}
