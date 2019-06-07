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

import gmapsfx.javascript.object.MVCArray;

/**
 *
 * @author Geoff Capper
 */
public class PolylineOptions extends MapShapeOptions<PolylineOptions> {
    
    // icons Array.<IconSequence> The icons to be rendered along the polyline.
    
    private MVCArray path;
    
    public PolylineOptions() {
    }
    
    public PolylineOptions path(MVCArray path) {
        setProperty("path", path);
        this.path = path;
        return this;
    }

    @Override
    protected PolylineOptions getMe() {
        return this;
    }
    
}
