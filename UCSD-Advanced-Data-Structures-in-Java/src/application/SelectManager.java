/** Class to manage items selected in the GUI
 * 
 * @author UCSD MOOC development team
 *
 */

package application;
import application.services.GeneralService;
import geography.GeographicPoint;
import gmapsfx.javascript.object.Marker;

public class SelectManager {
    private CLabel<GeographicPoint> pointLabel;
    private CLabel<GeographicPoint> startLabel;
    private CLabel<GeographicPoint> destinationLabel;
    private Marker startMarker;
    private Marker destinationMarker;
    private Marker selectedMarker;
    private MarkerManager markerManager;
    private DataSet dataSet;


    public SelectManager() {
        startMarker = null;
        destinationMarker = null;
        selectedMarker = null;
        pointLabel = null;
        startLabel = null;
        destinationLabel = null;
        dataSet = null;
    }


    public void resetSelect() {
        markerManager.setSelectMode(true);
    }
    public void clearSelected() {
    	selectedMarker = null;
    	pointLabel.setItem(null);
    }

    public void setAndDisplayData(DataSet data) {
    	setDataSet(data);
        //TODO - maybe if markerManager!= null?
        if(markerManager != null) {
            markerManager.displayDataSet();
        }
        else {
        	System.err.println("Error : Marker Manager is null.");
        }
    }

    public void setMarkerManager(MarkerManager manager) { this.markerManager = manager; }
    public void setPoint(GeographicPoint point, Marker marker) {
        // System.out.println("inSetPoint.. passed : " + point);
    	pointLabel.setItem(point);
        selectedMarker = marker;
    }
    public void setDataSet(DataSet dataSet) {
    	this.dataSet = dataSet;
    	if(markerManager != null) {
    		markerManager.setDataSet(dataSet);
    	}
    }

    public void setPointLabel(CLabel<GeographicPoint> label) { this.pointLabel = label; }
    public void setStartLabel(CLabel<GeographicPoint> label) { this.startLabel = label; }
    public void setDestinationLabel(CLabel<GeographicPoint> label) { this.destinationLabel = label; }

    public GeographicPoint getPoint() { return pointLabel.getItem(); }


	public GeographicPoint getStart(){return startLabel.getItem();}
	public GeographicPoint getDestination(){return destinationLabel.getItem();}
	public void setStart() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		startLabel.setItem(point);
            markerManager.setStart(point);
		}
	}

	public void setDestination() {
		if(pointLabel.getItem() != null) {
        	GeographicPoint point = pointLabel.getItem();
    		destinationLabel.setItem(point);
    		markerManager.setDestination(point);
		}
	}



}