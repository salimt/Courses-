package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * Class to wrap the graph, current .map file map, and 
 * other pertinent information about the map data sets
 *
 * @author UCSD MOOC development team
 *
 */
public class DataSet {
	String filePath;
	roadgraph.MapGraph graph;
	Set<GeographicPoint> intersections;
    private HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  roads;
	boolean currentlyDisplayed;

	public DataSet (String path) {
        this.filePath = path;
        graph = null;
        roads = null;
        currentlyDisplayed = false;
	}

    public void setGraph(roadgraph.MapGraph graph) {
    	this.graph = graph;
    }

    public void setRoads(HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  roads) { this.roads = roads; }
    public roadgraph.MapGraph getGraph(){ return graph; }
    
    /** Return the intersections in this graph.
     * In order to keep it consistent, if getVertices in the graph returns something 
     * other than null (i.e. it's been implemented) we get the vertices from 
     * the graph itself.  But if the graph hasn't been implemented, we return 
     * the set of intersections we separately maintain specifically for this purpose.
     * @return The set of road intersections (vertices in the graph)
     */
    public Set<GeographicPoint> getIntersections() {
    	Set<GeographicPoint> intersectionsFromGraph = graph.getVertices();
    	if (intersectionsFromGraph == null) {
    		return intersections;
    	}
    	else {
    		return intersectionsFromGraph;
    	}
    }
    
    public HashMap<geography.GeographicPoint,HashSet<geography.RoadSegment>>  getRoads() { return this.roads; }

    public void initializeGraph() {
        graph = new roadgraph.MapGraph();
        roads = new HashMap<geography.GeographicPoint, HashSet<geography.RoadSegment>>();
        intersections = new HashSet<GeographicPoint>();
        //TODO: change to use intersections for points in graph.
    	GraphLoader.loadRoadMap(filePath, graph, roads, intersections);
    }

	public String getFilePath() {
		return this.filePath;
	}


    public Object[] getPoints() {
    	Set<geography.GeographicPoint> pointSet = roads.keySet();
    	return pointSet.toArray();
    }

    public boolean isDisplayed() {
    	return this.currentlyDisplayed;
    }

    public void setDisplayed(boolean value) {
    	this.currentlyDisplayed = value;
    }

}