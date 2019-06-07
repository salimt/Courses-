/**
 * @author: salimt
 */

package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.List;

public class MapGraphNode implements Comparable<MapGraphNode> {
    private List <MapGraphNode> neighbors;
    private String roadName;
    private String roadType;
    private double length;
    private GeographicPoint value;
    private double actualDist;

    public MapGraphNode(GeographicPoint loc, String roadName, String roadType, double length) {
        neighbors = new ArrayList <>();
        this.roadName = roadName;
        this.length = length;
        this.roadType = roadType;
        this.value = loc;
    }

    public MapGraphNode(GeographicPoint loc) {
        this.value = loc;
    }

    public void addEdge(GeographicPoint endPoint, String roadName, String roadType, double length){
        this.addNeighbor(new MapGraphNode(endPoint, roadName, roadType, length));
    }

    public String getRoadName() { return roadName; }

    public void setRoadName(String roadName) { this.roadName = roadName; }

    public String getRoadType() { return roadType; }

    public void setRoadType(String roadType) { this.roadType = roadType; }

    public void setActualDist (double actualDist) { this.actualDist = actualDist; }

    public void addNeighbor(MapGraphNode n) { neighbors.add(n); }

    public List <MapGraphNode> getNeighbor() { return neighbors; }

    public double getActualDist() { return actualDist; }

    public double getLength() { return length; }

    public void setLength(double length) { this.length = length; }

    public GeographicPoint getValue() { return value; }

    public void setValue(GeographicPoint value) { this.value = value; }

    @Override
    public int compareTo(MapGraphNode o) {
        return Double.compare(length, o.getLength());
    }

    public String toString() { return this.getValue().toString(); }
}
