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
public class PolygonOptions extends FillableMapShapeOptions<PolygonOptions> {
    
    private MVCArray paths;
    
    public PolygonOptions() {
    }
    
    public PolygonOptions paths(MVCArray paths) {
        setProperty("path", paths);
        this.paths = paths;
        return this;
    }
    
    @Override
    protected PolygonOptions getMe() {
        return this;
    }
    
}
