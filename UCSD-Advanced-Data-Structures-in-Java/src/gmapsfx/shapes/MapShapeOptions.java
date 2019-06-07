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

package gmapsfx.shapes;

import gmapsfx.javascript.JavascriptObject;
import gmapsfx.javascript.object.GMapObjectType;

/** Abstract base class for our shape options, as they all have a lot of 
 * common properties.
 *
 * @author Geoff Capper
 * @param <T>
 */
public abstract class MapShapeOptions<T extends MapShapeOptions> extends JavascriptObject {
    
    private boolean clickable;
    private boolean draggable;
    private boolean editable;
    private boolean geodesic;
    private String strokeColor;
    private double strokeOpacity;
    private double strokeWeight;
    private boolean visible;
    private int zIndex;
    
//    private T me;
    
    public MapShapeOptions() {
        super(GMapObjectType.OBJECT);
    }
    
    protected abstract T getMe();
    
    public T clickable(boolean clickable) {
        setProperty("clickable", clickable);
        this.clickable = clickable;
        return getMe();
    }
    
    public T draggable(boolean draggable) {
        setProperty("draggable", draggable);
        this.draggable = draggable;
        return getMe();
    }
    
    public T editable(boolean editable) {
        setProperty("editable", editable);
        this.editable = editable;
        return getMe();
    }
    
    public T geodesic(boolean geodesic) {
        setProperty("geodesic", geodesic);
        this.geodesic = geodesic;
        return getMe();
    }
    
    public T strokeColor(String strokeColor) {
        setProperty("strokeColor", strokeColor);
        this.strokeColor = strokeColor;
        return getMe();
    }
    
    public T strokeOpacity(double strokeOpacity) {
        setProperty("strokeOpacity", strokeOpacity);
        this.strokeOpacity = strokeOpacity;
        return getMe();
    }
    
    public T strokeWeight(double strokeWeight) {
        setProperty("strokeWeight", strokeWeight);
        this.strokeWeight = strokeWeight;
        return getMe();
    }
    
    public T visible(boolean visible) {
        setProperty("draggable", visible);
        this.visible = visible;
        return getMe();
    }
    
    public T zIndex(int zIndex) {
        setProperty("zIndex", zIndex);
        this.zIndex = zIndex;
        return getMe();
    }
    
    
}
