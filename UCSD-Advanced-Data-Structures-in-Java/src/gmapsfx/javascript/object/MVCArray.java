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
package gmapsfx.javascript.object;

import gmapsfx.javascript.JavascriptObject;
import netscape.javascript.JSObject;

/**
 * This javascript object current takes JavascriptObjects as parameters, but
 * returns JSObjects, as it doesn't know what has been placed within it.
 *
 * @author Geoff Capper
 */
public class MVCArray extends JavascriptObject {

    public MVCArray() {
        this(new Object[]{});
    }

    public MVCArray(Object[] ary) {
        super(GMapObjectType.MVC_ARRAY, ary, true);
    }

    public MVCArray(JSObject obj) {
        super(GMapObjectType.MVC_ARRAY, obj);
    }

    /**
     * Removes all elements from the array.
     *
     */
    public void clear() {
        invokeJavascript("clear");
    }

    /**
     * Iterates over the elements within the array, calling the supplied
     * function for each member. The parameters to the callback function are:
     * func(element, index)
     *
     * @param func
     */
    public void forEach(String func) {
        invokeJavascript("forEach", func);
    }

    /**
     * Returns a JSObject representing the underlying array. Not entirely sure
     * how useful this would be for people.
     *
     * @return
     */
    public JSObject getArray() {
        return (JSObject) invokeJavascript("getArray");
    }

    /**
     * Returns the item at the specified index in the array.
     *
     * @param idx The index of the item to be returned.
     * @return The object at the specified index.
     */
    public JSObject getAt(int idx) {
        return (JSObject) invokeJavascript("getAt", idx);
    }

    /**
     * Gets the length of the array.
     *
     * @return
     */
    public int getLength() {
        return (int) invokeJavascript("getLength");
    }

    /**
     * Inserts the supplied element at the specified index in the array.
     *
     * @param idx The index at which to insert the item.
     * @param elem The item to be inserted.
     */
    public void insertAt(int idx, JavascriptObject elem) {
        invokeJavascript("insertAt", idx, elem);
    }

    /**
     * Removes and returns the last element of the array
     *
     * @return
     */
    public JSObject pop() {
        return (JSObject) invokeJavascript("pop");
    }

    /**
     * Adds the supplied element onto the end of the array, then returns the
     * length of the array.
     *
     * @param obj The Javascript object to be added to the array.
     * @return The length of the array after the addition.
     */
    public int push(JavascriptObject obj) {
        return (int) invokeJavascript("push", obj);
    }

    /**
     * Removes and discards the item at the specified index.
     *
     * @param idx The index of the object to be removed.
     */
    public void removeAt(int idx) {
        invokeJavascript("removeAt", idx);
    }

    /**
     * Sets the supplied object at the supplied index.
     *
     * @param idx The index at which the item should be set.
     * @param obj The object to be set at the supplied index.
     */
    public void setAt(int idx, JavascriptObject obj) {
        invokeJavascript("setAt", idx, obj);
    }

}
