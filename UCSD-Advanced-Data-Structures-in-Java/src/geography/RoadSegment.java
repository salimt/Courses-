package geography;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * A segment of the road that includes the intersection end points
 * as well as all the minor points that make up the intermediate geometry.
 * @author Christine
 *
 */

public class RoadSegment {

	private GeographicPoint point1;
	private GeographicPoint point2;
	
	private List<GeographicPoint> geometryPoints;
	
	private String roadName;
	private String roadType;
	
	// Length in km
	private double length;
	
	public RoadSegment(GeographicPoint pt1, GeographicPoint pt2, 
						List<GeographicPoint> geometry, String roadName,
						String roadType, double length)
	{
		point1 = pt1;
		point2 = pt2;
		geometryPoints = new ArrayList<GeographicPoint>(geometry);
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	
	/** Return all of the points from start to end in that order
	 * on this segment.
	 * @param start
	 * @param end
	 * @return
	 */
	public List<GeographicPoint> getPoints(GeographicPoint start, 
											GeographicPoint end)
	{
		List<GeographicPoint> allPoints = new ArrayList<GeographicPoint>();
		if (point1.equals(start) && point2.equals(end)) {
			allPoints.add(start);
			allPoints.addAll(geometryPoints);
			allPoints.add(end);
		}
		else if (point2.equals(start) && point1.equals(end)) {
			allPoints.add(end);
			allPoints.addAll(geometryPoints);
			allPoints.add(start);
			Collections.reverse(allPoints);
		}
		else {
			throw new IllegalArgumentException("Start and end points do not "
					+ "match end points of segment");
		}
		
		return allPoints;
	}
	
	/** Two road segments are equal if they have the same start and end points
	 *  and they have the same road name.
	 */
	public boolean equals(Object o)
	{
		if (!(o instanceof RoadSegment)) {
			return false;
		}
		
		RoadSegment other = (RoadSegment)o;
		boolean ptsEqual = false;
		if (other.point1.equals(this.point1) && other.point2.equals(this.point2)) {
			ptsEqual = true;
		}
		if (other.point2.equals(this.point1) && other.point1.equals(this.point2))
		{
			ptsEqual = true;
		}
		return this.roadName.equals(other.roadName) && ptsEqual && this.length == other.length;
	}
	
	// get hashCode
	public int hashCode()
	{
		return point1.hashCode() + point2.hashCode();
	}
	
	// return road segment as String
	public String toString()
	{
		String toReturn = this.roadName + ", " +this.roadType;
		toReturn += " [" + point1;
		for (GeographicPoint p : this.geometryPoints) {
			toReturn += "; " + p;
		}
		toReturn += "; " + point2 + "]";
		
		return toReturn;
	}

	// get the length of the road segment
	public double getLength() { return this.length; }
	
	
	// given one end, return the other.
	public geography.GeographicPoint getOtherPoint(geography.GeographicPoint point) {
		if(point.equals(point1)) {
			return point2;
		}
		if(point.equals(point2)) {
			return point1;
		}

		System.out.println("ERROR!! : in RoadSegment::getOtherPoint Neither point matched");
		return null;
	}


	
}
