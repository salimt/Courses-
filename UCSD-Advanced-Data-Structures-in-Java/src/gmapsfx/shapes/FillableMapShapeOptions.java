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

/** Options for a MapShape that is fillable.
 *
 * @author Geoff Capper
 * @param <T>
 */
public abstract class FillableMapShapeOptions<T extends FillableMapShapeOptions> extends MapShapeOptions<T> {
    
    private String fillColor;
    private double fillOpacity;
    private StrokePosition strokePosition;
    
    public FillableMapShapeOptions() {
    }
    
    public T fillColor(String fillColor) {
        setProperty("fillColor", fillColor);
        this.fillColor = fillColor;
        return getMe();
    }
    
    public T fillOpacity(double fillOpacity) {
        setProperty("fillOpacity", fillOpacity);
        this.fillOpacity = fillOpacity;
        return getMe();
    }
    
    public T strokePosition(StrokePosition strokePosition) {
        setProperty("strokePosition", strokePosition.name());
        this.strokePosition = strokePosition;
        return getMe();
    }
    
}
