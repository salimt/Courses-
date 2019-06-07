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
 * Size object corresponding to <a href="https://developers.google.com/maps/documentation/javascript/reference#Size">google.maps.Size</a>
* 
* Two-dimensonal size, where width is the distance on the x-axis, and height is the distance on the y-axis.
* 
* @author Rob Terpilowski
 */
public class Size extends JavascriptObject {

    protected int width;
    protected int height;
    protected String widthUnit;
    protected String heightUnit;
    
    
    
    /**
     * 
     * @param width distance on x-axis
     * @param height distance on y-axis
     */
    public Size(int width, int height) {
        super(GMapObjectType.SIZE, width, height);
    }
    
    /**
     * 
     * @param width distance on x-axis
     * @param height distance on y-axis
     * @param widthUnit
     * @param heightUnit 
     */
    public Size( int width, int height, String widthUnit, String heightUnit ) {
        super( GMapObjectType.SIZE, width, height, widthUnit, heightUnit );
    }

    public int getWidth() {
        return getProperty("width", Integer.class);
    }

    public int getHeight() {
        return getProperty("height", Integer.class);
    }

    public String getWidthUnit() {
        return widthUnit;
    }

    public String getHeightUnit() {
        return heightUnit;
    }
    
    public boolean equals( Size other ) {
        return invokeJavascriptReturnValue("equals", Boolean.class, other);
    }
    
    
    @Override
    public String toString() {
        return invokeJavascriptReturnValue("toString", String.class, (Object)null);
    }
    
}
