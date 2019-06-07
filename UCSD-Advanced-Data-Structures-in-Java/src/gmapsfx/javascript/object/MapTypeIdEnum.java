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

import gmapsfx.javascript.JavascriptEnum;

/**
 *
 * @author Rob Terpilowski
 */
public class MapTypeIdEnum extends JavascriptEnum {

    public static final String MAP_ENUM = "google.maps.MapTypeId";

    public static final MapTypeIdEnum TERRAIN = new MapTypeIdEnum("TERRAIN");
    public static final MapTypeIdEnum ROADMAP = new MapTypeIdEnum("ROADMAP");
    public static final MapTypeIdEnum SATELLITE = new MapTypeIdEnum("SATELLITE");
    public static final MapTypeIdEnum HYBRID = new MapTypeIdEnum("HYBRID");
    
    public static final MapTypeIdEnum[] ALL = { TERRAIN, ROADMAP, SATELLITE, HYBRID };

    protected MapTypeIdEnum(String value) {
        super(MAP_ENUM, value);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
    
}
