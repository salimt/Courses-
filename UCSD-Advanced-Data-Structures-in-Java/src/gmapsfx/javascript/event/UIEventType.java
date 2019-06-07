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

package gmapsfx.javascript.event;

/** Enum containing the strings for the event types passed to the Google Maps 
 * APIs for UI events. Whilst drawn from those for the Map object, they should 
 * be the same for most other objects.
 * <p>
 * See <a href="https://developers.google.com/maps/documentation/javascript/reference?csw=1#Map">Google Maps Javascript API V3 Reference</a>
 *
 * @author Geoff Capper
 */
public enum UIEventType {
	
	click, dblclick, mousemove, mouseup, mousedown, mouseover, mouseout, rightclick;
    
}
