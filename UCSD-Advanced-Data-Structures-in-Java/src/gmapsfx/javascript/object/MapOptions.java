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
 *
 * @author robt
 */
public class MapOptions extends JavascriptObject {
    
    protected LatLong center;
    protected MapTypeIdEnum mapType;
    protected boolean mapMarker;
    protected boolean overviewMapControl;
    protected boolean panControl;
    protected boolean rotateControl;
    protected boolean scaleControl;
    protected boolean streetViewControl;
    protected int zoom;
    protected boolean zoomControl;
    protected boolean mapTypeControl;
    

    public MapOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    
    public MapOptions center( LatLong center ) {
        setProperty("center", center);
        this.center = center;
        return this;
    }
    
    public MapOptions mapMarker( boolean mapMarker ) {
        setProperty("mapMarker", mapMarker );
        this.mapMarker = mapMarker;
        return this;
    }
    
    public MapOptions mapType( MapTypeIdEnum mapType ) {
        setProperty( "mapTypeId", mapType );
        this.mapType = mapType;
        return this;
    }
    
    public MapOptions overviewMapControl( boolean overviewMapControl ) {
        setProperty("overviewMapControl", overviewMapControl );
        this.overviewMapControl = overviewMapControl;
        return this;
    }
    
    public MapOptions panControl( boolean panControl ) {
        setProperty( "panControl", panControl);
        this.panControl = panControl;
        return this;
    }
    
    public MapOptions rotateControl( boolean rotateControl ) {
        setProperty( "rotateControl", rotateControl );
        this.rotateControl = rotateControl;
        return this;
    }
    
    public MapOptions scaleControl( boolean scaleControl ) {
        setProperty( "scaleControl", scaleControl );
        this.scaleControl = scaleControl;
        return this;
    }
    
    public MapOptions streetViewControl( boolean streetViewControl ) {
        setProperty( "streetViewControl", streetViewControl );
        this.streetViewControl = streetViewControl;
        return this;
    }
    
    public MapOptions zoom( int zoom ) {
        setProperty( "zoom", zoom );
        this.zoom = zoom;
        return this;
    }
    
    public MapOptions zoomControl( boolean zoomControl ) {
        setProperty( "zoomControl", zoomControl );
        this.zoomControl = zoomControl;
        return this;
    }
    
    public MapOptions mapTypeControl( boolean mapTypeControl ) {
        setProperty( "mapTypeControl", mapTypeControl);
        this.mapTypeControl = mapTypeControl;
        return this;
    }
    
}
