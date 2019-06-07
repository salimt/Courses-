/** Basic Class to test UI components
 * 
 * @author UCSD MOOC development team
 *
 */
package application;

import gmapsfx.GoogleMapView;
import gmapsfx.MapComponentInitializedListener;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapOptions;
import gmapsfx.javascript.object.MapTypeIdEnum;
import gmapsfx.shapes.Polyline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApp extends Application
					 implements MapComponentInitializedListener {
	protected GoogleMapView mapComponent;
	protected GoogleMap map;

	@Override
	public void start(Stage primaryStage) throws Exception {
		mapComponent = new GoogleMapView();
		mapComponent.addMapInitializedListener(this);

		BorderPane bp = new BorderPane();
		Scene scene = new Scene(bp);
        bp.setCenter(mapComponent);
		primaryStage.setScene(scene);
        primaryStage.show();
	}

    public void mapInitialized() {
    	LatLong center = new LatLong(34.0219, -118.4814);

    	MapOptions options = new MapOptions();
		options.center(center)
		       .mapMarker(false)
		       .mapType(MapTypeIdEnum.ROADMAP)
		       //maybe set false
		       .mapTypeControl(true)
		       .overviewMapControl(false)
		       .panControl(true)
		       .rotateControl(false)
		       .scaleControl(false)
		       .streetViewControl(false)
		       .zoom(8)
		       .zoomControl(true);

        map = mapComponent.createMap(options);

        Polyline p = new Polyline();

    }

}
