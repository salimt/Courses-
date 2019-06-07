/*
 * Copyright 2014 Lynden, Inc.
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
 * Options for setting an InfoWindow on a Map.  This class corresponds to <a href="https://developers.google.com/maps/documentation/javascript/reference#InfoWindowOptions">google.maps.InfoWindowOptions"</a> in
 * the Google Maps JavaScript API.
 * 
 * @author Rob Terpilowski
 */
public class InfoWindowOptions extends JavascriptObject {

    /**
     * Default constructor
     */
    public InfoWindowOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    /**
     *Content to display in the InfoWindow. This can be an HTML element, a plain-text string, or a string containing HTML.
     * The InfoWindow will be sized according to the content.
     * @param content
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions content( String content ) {
        setProperty("content", content);
        return this;
    }
    
    /**
     * Disable auto-pan on open. By default, the info window will pan the map so that it is fully visible when it opens.
     * @param disabled true if it should be disabled
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions disableAutoPan( boolean disabled ) {
        setProperty("disableAutoPan", disabled);
        return this;
    }
    
    /**
     * Maximum width of the infowindow, regardless of content's width. This value is only considered if it is set before a call to open. 
     * To change the maximum width when changing content, call close, setOptions, and then open.
     * @param width The max width
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions maxWidth( int width ) {
        setProperty( "maxWidth", width );
        return this;
    }
    
    

    /**
     * The offset, in pixels, of the tip of the info window from the point on the map at whose geographical coordinates the info window is anchored. 
     * If an InfoWindow is opened with an anchor, the pixelOffset will be calculated from the anchor's anchorPoint property.
     * @param size The offset in pixels.
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions pixelOffset( Size size ) {
        setProperty( "pixelOffset", size );
        return this;
    }
    
    
    /**
     * The LatLng at which to display this InfoWindow. 
     * If the InfoWindow is opened with an anchor, the anchor's position will be used instead.
     * @param latlong The position of the window
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions position( LatLong latlong ) {
        setProperty( "position", latlong );
        return this;
    }
    
    /**
     * All InfoWindows are displayed on the map in order of their zIndex, with higher values displaying in front of InfoWindows with lower values. 
     * By default, InfoWindows are displayed according to their latitude, with InfoWindows of lower latitudes appearing in front of InfoWindows at higher latitudes. 
     * InfoWindows are always displayed in front of markers.
     * @param index The index to set this window to
     * @return this instance of the InfoWindowOptions
     */
    public InfoWindowOptions zIndex( int index ) {
        setProperty( "zIndex", index );
        return this;
    }
    
    
}
