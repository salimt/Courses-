/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmapsfx.service.elevation;

import gmapsfx.javascript.JavascriptObject;
import gmapsfx.javascript.object.GMapObjectType;
import gmapsfx.javascript.object.LatLong;

/** Creates a request that can be passed in to the {@link ElevationService} to 
 * get the elevations for a number of locations.
 *
 * @author Geoff Capper
 */
public class LocationElevationRequest extends JavascriptObject {
    
    public LocationElevationRequest() {
        super(GMapObjectType.OBJECT);
    }
    
    public LocationElevationRequest(LatLong[] locations) {
        super(GMapObjectType.OBJECT);
        getJSObject().setMember("locations", getJSObject().eval("[]"));
        for (int i = 0; i < locations.length; i++) {
            getJSObject().eval(getVariableName()+".locations.push("+locations[i].getVariableName()+")");
        }
    }
    
}
