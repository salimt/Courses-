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

import gmapsfx.javascript.object.GMapObjectType;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapShape;
import netscape.javascript.JSObject;

/**
 *
 * @author Geoff Capper
 */
public class Circle extends MapShape {
    
    public Circle() {
        super(GMapObjectType.CIRCLE);
    }
    
    public Circle(CircleOptions opts) {
        super(GMapObjectType.CIRCLE, opts);
    }
    
    //getCenter
    public LatLong getCenter() {
        return new LatLong((JSObject) invokeJavascript("getCenter"));
    }
    
    //getRadius
    public double getRadius() {
        return (double) invokeJavascript("getRadius");
    }
    
    //setCenter
    public void setCenter(LatLong center) {
        invokeJavascript("setCenter", center);
    }
    
    //setRadius
    public void setRadius(double radius) {
        invokeJavascript("setRadius", radius);
    }
    
}
