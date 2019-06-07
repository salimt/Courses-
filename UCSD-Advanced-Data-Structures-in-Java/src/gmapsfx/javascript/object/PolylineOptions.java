/*
 * Copyright 2014 Geoff Capper.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gmapsfx.javascript.object;

import gmapsfx.javascript.JavascriptObject;

/**
 *
 * @author Geoff Capper
 */
public class PolylineOptions extends JavascriptObject {
    
    private boolean clickable;// Indicates whether this Polyline handles mouse events. Defaults to true.
    private boolean draggable;// If set to true, the user can drag this shape over the map. The geodesic property defines the mode of dragging. Defaults to false.
    private boolean editable;// If set to true, the user can edit this shape by dragging the control points shown at the vertices and on each segment. Defaults to false.
    private boolean geodesic;// When true, edges of the polygon are interpreted as geodesic and will follow the curvature of the Earth. When false, edges of the polygon are rendered as straight lines in screen space. Note that the shape of a geodesic polygon may appear to change when dragged, as the dimensions are maintained relative to the surface of the earth. Defaults to false.
    //private icons 	Array.<IconSequence> 	The icons to be rendered along the polyline.
    //map 	Map 	Map on which to display Polyline.
    private MVCArray path;// MVCArray.<LatLng>|Array.<LatLng> The ordered sequence of coordinates of the Polyline. This path may be specified using either a simple array of LatLngs, or an MVCArray of LatLngs. Note that if you pass a simple array, it will be converted to an MVCArray Inserting or removing LatLngs in the MVCArray will automatically update the polyline on the map.
    private String strokeColor;// The stroke color. All CSS3 colors are supported except for extended named colors.
    private double strokeOpacity; // The stroke opacity between 0.0 and 1.0.
    private double strokeWeight;// The stroke width in pixels.
    private boolean visible; // Whether this polyline is visible on the map. Defaults to true.
    private int zIndex;// The zIndex compared to other polys.
    
    
    public PolylineOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    public PolylineOptions clickable(boolean clickable) {
        setProperty("clickable", clickable);
        this.clickable = clickable;
        return this;
    }
    
    public PolylineOptions draggable(boolean draggable) {
        setProperty("draggable", draggable);
        this.draggable = draggable;
        return this;
    }
    
    public PolylineOptions editable(boolean editable) {
        setProperty("editable", editable);
        this.editable = editable;
        return this;
    }
    
    public PolylineOptions geodesic(boolean geodesic) {
        setProperty("geodesic", geodesic);
        this.geodesic = geodesic;
        return this;
    }
//    private double[] path;// MVCArray.<LatLng>|Array.<LatLng> The ordered sequence of coordinates of the Polyline. This path may be specified using either a simple array of LatLngs, or an MVCArray of LatLngs. Note that if you pass a simple array, it will be converted to an MVCArray Inserting or removing LatLngs in the MVCArray will automatically update the polyline on the map.
    public PolylineOptions path(MVCArray path) {
        setProperty("path", path);
        this.path = path;
        return this;
    }
    
    public PolylineOptions strokeColor(String strokeColor) {
        setProperty("strokeColor", strokeColor);
        this.strokeColor = strokeColor;
        return this;
    }
    
    public PolylineOptions strokeOpacity(double strokeOpacity) {
        setProperty("strokeOpacity", strokeOpacity);
        this.strokeOpacity = strokeOpacity;
        return this;
    }
    
    public PolylineOptions strokeWeight(double strokeWeight) {
        setProperty("strokeWeight", strokeWeight);
        this.strokeWeight = strokeWeight;
        return this;
    }
    
    public PolylineOptions visible(boolean visible) {
        setProperty("draggable", visible);
        this.visible = visible;
        return this;
    }
    
    public PolylineOptions zIndex(int zIndex) {
        setProperty("zIndex", zIndex);
        this.zIndex = zIndex;
        return this;
    }
    
    
}
