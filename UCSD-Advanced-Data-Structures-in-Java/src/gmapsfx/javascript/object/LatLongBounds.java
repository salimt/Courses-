/*
 * Copyright 2014 GMapsFX
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
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class LatLongBounds extends JavascriptObject {

    public LatLongBounds() {
        super(GMapObjectType.LAT_LNG_BOUNDS);
    }

    public LatLongBounds(LatLong sw, LatLong ne) {
        super(GMapObjectType.LAT_LNG_BOUNDS, sw, ne);
    }

    public LatLongBounds(JSObject obj) {
        super(GMapObjectType.LAT_LNG_BOUNDS, obj);
    }

    public LatLongBounds extend(LatLong point) {
    	Object obj = invokeJavascript("extend", point);
    	return new LatLongBounds((JSObject)obj);
    }

    public LatLong getNorthEast() {
        Object obj = invokeJavascript("getNorthEast");
        return new LatLong((JSObject) obj);
    }

    public LatLong getSouthWest() {
        Object obj = invokeJavascript("getSouthWest");
        return new LatLong((JSObject) obj);
    }

    @Override
    public String toString() {
        return "LatLongBounds[NE:" + getNorthEast() + ", SW:" + getSouthWest() + "]";
    }
}
