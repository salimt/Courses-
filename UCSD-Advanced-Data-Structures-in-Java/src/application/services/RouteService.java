package application.services;

import application.MapApp;
import application.MarkerManager;
import application.RouteVisualization;
import application.controllers.RouteController;
import gmapsfx.GoogleMapView;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import gmapsfx.javascript.object.MVCArray;
import gmapsfx.shapes.Polyline;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class RouteService {
	private GoogleMap map;

    // static variable
    private MarkerManager markerManager;
    private Polyline routeLine;
    private RouteVisualization rv;

	public RouteService(GoogleMapView mapComponent, MarkerManager manager) {
		this.map = mapComponent.getMap();
        this.markerManager = manager;

	}
    // COULD SEPARATE INTO ROUTE SERVICES IF CONTROLLER
	// GETS BIG
	// initialize??

	// add route polyline to map
	//DISPLAY ROUTE METHODS
	/**
	 * Displays route on Google Map
	 * @return returns false if route fails to display
	 */
	private boolean displayRoute(List<LatLong> route) {

        if(routeLine != null) {
        	removeRouteLine();
        }
		routeLine = new Polyline();
		MVCArray path = new MVCArray();
		LatLongBounds bounds = new LatLongBounds();
		for(LatLong point : route)  {
			path.push(point);
            bounds = bounds.extend(point);
		}
		routeLine.setPath(path);

		map.addMapShape(routeLine);

		//System.out.println(bounds.getNorthEast());
		//EXCEPTION getBounds() messed up??
        //System.out.println(routeLine.getBounds());


		markerManager.hideIntermediateMarkers();
		map.fitBounds(bounds);
    	markerManager.disableVisButton(false);
		return true;
	}

    public void hideRoute() {
    	if(routeLine != null) {
        	map.removeMapShape(routeLine);
        	if(markerManager.getVisualization() != null) {
        		markerManager.clearVisualization();
        	}
            markerManager.restoreMarkers();
        	markerManager.disableVisButton(true);
            routeLine = null;
    	}
    }

    public void reset() {
        removeRouteLine();
    }

    public boolean isRouteDisplayed() {
    	return routeLine != null;
    }
    public boolean displayRoute(geography.GeographicPoint start, geography.GeographicPoint end, int toggle) {
        if(routeLine == null) {
        	if(markerManager.getVisualization() != null) {
        		markerManager.clearVisualization();
        	}

        	if(toggle == RouteController.DIJ || toggle == RouteController.A_STAR ||
        			toggle == RouteController.BFS) {
        		markerManager.initVisualization();
            	Consumer<geography.GeographicPoint> nodeAccepter = markerManager.getVisualization()::acceptPoint;
            	List<geography.GeographicPoint> path = null;
            	if (toggle == RouteController.BFS) {
            		path = markerManager.getDataSet().getGraph().bfs(start, end, nodeAccepter);
            	}
            	else if (toggle == RouteController.DIJ) {
            		path = markerManager.getDataSet().getGraph().dijkstra(start, end, nodeAccepter);
            	}
            	else if (toggle == RouteController.A_STAR) {
            		path = markerManager.getDataSet().getGraph().aStarSearch(start, end, nodeAccepter);
            	}

            	if(path == null) {
                    // System.out.println("In displayRoute : PATH NOT FOUND");
                    MapApp.showInfoAlert("Routing Error : ", "No path found");
                	return false;
                }
                // TODO -- debug road segments
            	List<LatLong> mapPath = constructMapPath(path);
                //List<LatLong> mapPath = new ArrayList<LatLong>();
                //for(geography.GeographicPoint point : path) {
                //    mapPath.add(new LatLong(point.getX(), point.getY()));
                //}


                markerManager.setSelectMode(false);
                return displayRoute(mapPath);
    		}

    		return false;
        }
        return false;
    }




    /**
     * Construct path including road regments
     * @param path - path with only intersections
     * @return list of LatLongs corresponding the path of route
     */
    private List<LatLong> constructMapPath(List<geography.GeographicPoint> path) {
    	List<LatLong> retVal = new ArrayList<LatLong>();
        List<geography.GeographicPoint> segmentList = null;
    	geography.GeographicPoint curr;
    	geography.GeographicPoint next;

    	geography.RoadSegment chosenSegment = null;;

        for(int i = 0; i < path.size() - 1; i++) {
            double minLength = Double.MAX_VALUE;
        	curr = path.get(i);
        	next = path.get(i+1);

        	if(markerManager.getDataSet().getRoads().containsKey(curr)) {
        		HashSet<geography.RoadSegment> segments = markerManager.getDataSet().getRoads().get(curr);
        		Iterator<geography.RoadSegment> it = segments.iterator();

        		// get segments which are
            	geography.RoadSegment currSegment;
                while(it.hasNext()) {
                    //System.out.println("new segment");
                	currSegment = it.next();
                	if(currSegment.getOtherPoint(curr).equals(next)) {
                        //System.out.println("1st check passed : other point correct");
                		if(currSegment.getLength() < minLength) {
                            //System.out.println("2nd check passed : length less");
                			chosenSegment = currSegment;
                		}
                	}
                }

                if(chosenSegment != null) {
                    segmentList = chosenSegment.getPoints(curr, next);
                    for(geography.GeographicPoint point : segmentList) {
                        retVal.add(new LatLong(point.getX(), point.getY()));
                    }
                }
                else {
                	System.err.println("ERROR in constructMapPath : chosenSegment was null");
                }
        		// find

        	}
        }

        // System.out.println("NOW there are " + retVal.size() + " points");
    	return retVal;
    }


	private void removeRouteLine() {
        if(routeLine != null) {
    		map.removeMapShape(routeLine);
        }
	}

//    private void setMarkerManager(MarkerManager manager) {
//    	this.markerManager = manager;
//    }




}


