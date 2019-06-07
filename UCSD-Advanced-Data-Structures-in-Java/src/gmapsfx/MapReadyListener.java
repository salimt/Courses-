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

package gmapsfx;

/** A MapReadyListener is called when the MapOptions have been loaded and the 
 * Javascript map object is set up ready to be accessed. While the MapType is 
 * null many methods will cause errors, so this listener is notified when 
 * projection_changed returns a non-null value, as this is expected to happen 
 * after the MapType is set.
 *
 * @author Geoff Capper
 */
public interface MapReadyListener {
    
    public void mapReady();
    
}
