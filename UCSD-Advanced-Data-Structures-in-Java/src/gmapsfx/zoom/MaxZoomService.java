/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmapsfx.zoom;

import gmapsfx.javascript.JavascriptObject;
import gmapsfx.javascript.object.GMapObjectType;
import gmapsfx.javascript.object.LatLong;
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class MaxZoomService extends JavascriptObject {
    
    private MaxZoomServiceCallback callback;
    
    public MaxZoomService() {
        super(GMapObjectType.MAX_ZOOM_SERVICE);
    }
    
    public void getMaxZoomAtLatLng(LatLong loc, MaxZoomServiceCallback callback) {
        
        this.callback = callback;
        
        JSObject doc = (JSObject) getJSObject().eval("document");
        doc.setMember(getVariableName(), this);
        
        StringBuilder r = new StringBuilder(getVariableName())
              .append(".")
              .append("getMaxZoomAtLatLng(")
              .append(loc.getVariableName())
              .append(", ")
              .append("function(result) {document.")
              .append(getVariableName())
              .append(".processResponse(result);});");
        
//        System.out.println("MaxZoomService direct call: " + r.toString());
        
        getJSObject().eval(r.toString());
        
    }
    
    /** Processess the Javascript response and generates the required objects 
     * that are then passed back to the original callback.
     * 
     * @param result
     */
    public void processResponse(Object result) {
        if (result instanceof JSObject) {
            MaxZoomResult mzr = new MaxZoomResult((JSObject) result);
            callback.maxZoomReceived(mzr);
        }
    }
    
}
