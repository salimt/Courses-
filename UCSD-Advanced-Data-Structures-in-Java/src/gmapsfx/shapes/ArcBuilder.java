/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmapsfx.shapes;

import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MVCArray;
import javafx.scene.shape.ArcType;

/**
 * Builds a Polygon or Polyline in the shape of an Arc. Based on the logic in
 * v3_polygon_exanple_arc.html from http://econym.org.uk/gmap/ which is based in
 * turn on the Latitude/longitude spherical geodesy formulae and scripts at
 * http://www.movable-type.co.uk/scripts/latlong.html (c) Chris Veness 2002-2010
 *
 * @author Geoff Capper
 */
public class ArcBuilder {

    private static final int DEFAULT_ARC_POINTS = 32;

    /**
     * Builds the path for a closed arc, returning a PolygonOptions that can be
     * further customised before use.
     *
     * @param center
     * @param start
     * @param end
     * @param arcType Pass in either ArcType.CHORD or ArcType.ROUND
     * @return PolygonOptions with the paths element populated.
     */
    public static final PolygonOptions buildClosedArc(LatLong center, LatLong start, LatLong end, ArcType arcType) {
        MVCArray res = buildArcPoints(center, start, end);
        if (ArcType.ROUND.equals(arcType)) {
            res.push(center);
        }
        return new PolygonOptions().paths(res);
    }

    /**
     * Builds the path for an open arc based on a PolylineOptions.
     *
     * @param center
     * @param start
     * @param end
     * @return PolylineOptions with the paths element populated.
     */
    public static final PolylineOptions buildOpenArc(LatLong center, LatLong start, LatLong end) {
        MVCArray res = buildArcPoints(center, start, end);
        return new PolylineOptions().path(res);
    }

    public static final MVCArray buildArcPoints(LatLong center, LatLong start, LatLong end) {
        return buildArcPoints(center, center.getBearing(start), center.getBearing(end), center.distanceFrom(start));
    }

    /**
     * Generates the points for an arc based on two bearings from a centre point
     * and a radius.
     *
     * @param center The LatLong point of the center.
     * @param startBearing North is 0 degrees, East is 90 degrees, etc.
     * @param endBearing North is 0 degrees, East is 90 degrees, etc.
     * @param radius In metres
     * @return An array of LatLong points in an MVC array representing the arc.
     * Using this method directly you will need to push the centre point onto
     * the array in order to close it, if desired.
     */
    public static final MVCArray buildArcPoints(LatLong center, double startBearing, double endBearing, double radius) {
        int points = DEFAULT_ARC_POINTS;

        MVCArray res = new MVCArray();

        if (startBearing > endBearing) {
            endBearing += 360.0;
        }
        double deltaBearing = endBearing - startBearing;
        deltaBearing = deltaBearing / points;
        for (int i = 0; (i < points + 1); i++) {
            res.push(center.getDestinationPoint(startBearing + i * deltaBearing, radius));
        }

        return res;

    }

}
