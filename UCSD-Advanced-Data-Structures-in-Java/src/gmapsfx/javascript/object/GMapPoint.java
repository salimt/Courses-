/*
 * Copyright 2014 GMapsFX.
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
public class GMapPoint extends JavascriptObject {
    
    public GMapPoint(JSObject obj) {
        super(GMapObjectType.POINT, obj);
    }
    
    public double getX() {
        return getProperty("x", Double.class );
    }

    public double getY() {
        return getProperty("y", Double.class );
    }
    
    @Override
    public String toString() {
        return "GMapPoint[" + getX() + ", " + getY() + "]";
    }
    
}
