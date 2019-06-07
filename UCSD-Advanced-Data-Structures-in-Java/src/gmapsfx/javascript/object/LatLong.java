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
 *
 * This file includes Java implementations of some of the v3_epolys.js 
 * extensions to LatLng for Javascript by Mike Williams and Larry Ross.
 * See included file src/main/resource/html/v3_epoly.js for their original 
 * licence.
 */
package gmapsfx.javascript.object;

import gmapsfx.javascript.JavascriptObject;
import netscape.javascript.JSObject;

/**
 *
 * @author Rob Terpilowski
 */
public class LatLong extends JavascriptObject {

    public static final double EarthRadiusMeters = 6378137.0; // meters


    public LatLong(double latitude, double longitude) {
        super(GMapObjectType.LAT_LNG, latitude, longitude);
    }

    public LatLong(JSObject jsObject) {
        super(GMapObjectType.LAT_LNG, jsObject);
    }

    public double getLatitude() {
        return invokeJavascriptReturnValue("lat", Number.class ).doubleValue();
    }

    public double getLongitude() {
        return invokeJavascriptReturnValue("lng", Number.class ).doubleValue();
    }

    /**
     * From v3_epoly.js, calculates the distance between this LatLong point and
     * another.
     *
     * @param end The end point to calculate the distance to.
     * @return The distance, in metres, to the end point.
     */
    public double distanceFrom(LatLong end) {

        double dLat = (end.getLatitude() - getLatitude()) * Math.PI / 180;
        double dLon = (end.getLongitude() - getLongitude()) * Math.PI / 180;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(getLatitude() * Math.PI / 180)
                * Math.cos(end.getLatitude() * Math.PI / 180)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EarthRadiusMeters * c;
        return d;
    }

    /**
     * Convenience method to convert the latitude of this LatLong to radians.
     *
     * @return The latitude value of this LatLong, as radians.
     */
    public double latToRadians() {
        return Math.toRadians(getLatitude());
    }

    /**
     * Convenience method to convert the longitude of this LatLong to radians.
     *
     * @return The longitude of this LatLong, as radians.
     */
    public double longToRadians() {
        return Math.toRadians(getLongitude());
    }

    /**
     * Calculates the LatLong position of the end point of a line the specified
     * distance from this LatLong, along the provided bearing, where North is 0,
     * East is 90 etc.
     *
     * @param bearing The bearing, in degrees, with North as 0, East as 90 etc.
     * @param distance The distance in metres.
     * @return A new LatLong indicating the end point.
     */
    public LatLong getDestinationPoint(double bearing, double distance) {

        double brng = Math.toRadians(bearing);

        double lat1 = latToRadians();
        double lon1 = longToRadians();

        double lat2 = Math.asin(Math.sin(lat1)
                * Math.cos(distance / EarthRadiusMeters)
                + Math.cos(lat1) * Math.sin(distance / EarthRadiusMeters)
                * Math.cos(brng));

        double lon2 = lon1 + Math.atan2(Math.sin(brng)
                * Math.sin(distance / EarthRadiusMeters) * Math.cos(lat1),
                Math.cos(distance / EarthRadiusMeters)
                - Math.sin(lat1) * Math.sin(lat2));

        return new LatLong(Math.toDegrees(lat2), Math.toDegrees(lon2));

    }

    /**
     * Calculates the bearing, in degrees, of the end LatLong point from this
     * LatLong point.
     *
     * @param end The point that the bearing is calculated for.
     * @return The bearing, in degrees, of the supplied point from this point.
     */
    public double getBearing(LatLong end) {
        if (this.equals(end)) {
            return 0;
        }

        double lat1 = latToRadians();
        double lon1 = longToRadians();
        double lat2 = end.latToRadians();
        double lon2 = end.longToRadians();

        double angle = -Math.atan2(Math.sin(lon1 - lon2) * Math.cos(lat2),
                Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(lon1 - lon2));

        if (angle < 0.0) {
            angle += Math.PI * 2.0;
        }
        if (angle > Math.PI) {
            angle -= Math.PI * 2.0;
        }

        return Math.toDegrees(angle);
    }

    
    

    @Override
    public String toString() {
        return "lat: " + String.format("%.8G", getLatitude()) + " lng: " + String.format("%.8G", getLongitude());
    }

}
