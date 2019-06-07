/** JavaFX application which interacts with the Google
 * Maps API to provide a mapping interface with which
 * to test and develop graph algorithms and data structures
 * 
 * @author UCSD MOOC development team
 *
 */
package application;

import application.controllers.FetchController;
import application.controllers.RouteController;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.GoogleMapView;
import gmapsfx.MapComponentInitializedListener;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapOptions;
import gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MapApp extends Application
implements MapComponentInitializedListener {

	protected GoogleMapView mapComponent;
	protected GoogleMap map;
	protected BorderPane bp;
	protected Stage primaryStage;

	// CONSTANTS
	private static final double MARGIN_VAL = 10;
	private static final double FETCH_COMPONENT_WIDTH = 160.0;

	public static void main(String[] args){
		launch(args);
	}

	/**
	 * Application entry point
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		// MAIN CONTAINER
		bp = new BorderPane();

		// set up map
		mapComponent = new GoogleMapView();
		mapComponent.addMapInitializedListener(this);

		// initialize tabs for data fetching and route controls
		Tab routeTab = new Tab("Routing");

		// create components for fetch tab
		Button fetchButton = new Button("Fetch Data");
		Button displayButton = new Button("Show Intersections");
		TextField tf = new TextField();
		ComboBox<DataSet> cb = new ComboBox<DataSet>();

		// set on mouse pressed, this fixes Windows 10 / Surface bug
		cb.setOnMousePressed( e -> {
			cb.requestFocus();
		});

		HBox fetchControls = getBottomBox(tf, fetchButton);

		VBox fetchBox = getFetchBox(displayButton, cb);


		// create components for fetch tab
		Button routeButton = new Button("Show Route");
		Button hideRouteButton = new Button("Hide Route");
		Button resetButton = new Button("Reset");
		Button visualizationButton = new Button("Start Visualization");
		Image sImage = new Image(MarkerManager.startURL);
		Image dImage = new Image(MarkerManager.destinationURL);
		CLabel<geography.GeographicPoint> startLabel = new CLabel<geography.GeographicPoint>("Empty.", new ImageView(sImage), null);
		CLabel<geography.GeographicPoint> endLabel = new CLabel<geography.GeographicPoint>("Empty.", new ImageView(dImage), null);
		//TODO -- hot fix
		startLabel.setMinWidth(180);
		endLabel.setMinWidth(180);
		//        startLabel.setWrapText(true);
		//        endLabel.setWrapText(true);
		Button startButton = new Button("Start");
		Button destinationButton = new Button("Dest");

		// Radio buttons for selecting search algorithm
		final ToggleGroup group = new ToggleGroup();

		List<RadioButton> searchOptions = setupToggle(group);


		// Select and marker managers for route choosing and marker display/visuals
		// should only be one instance (singleton)
		SelectManager manager = new SelectManager();
		MarkerManager markerManager = new MarkerManager();
		markerManager.setSelectManager(manager);
		manager.setMarkerManager(markerManager);
		markerManager.setVisButton(visualizationButton);

		// create components for route tab
		CLabel<geography.GeographicPoint> pointLabel = new CLabel<geography.GeographicPoint>("No point Selected.", null);
		manager.setPointLabel(pointLabel);
		manager.setStartLabel(startLabel);
		manager.setDestinationLabel(endLabel);
		setupRouteTab(routeTab, fetchBox, startLabel, endLabel, pointLabel, routeButton, hideRouteButton,
				resetButton, visualizationButton, startButton, destinationButton, searchOptions);

		// add tabs to pane, give no option to close
		TabPane tp = new TabPane(routeTab);
		tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// initialize Services and controllers after map is loaded
		mapComponent.addMapReadyListener(() -> {
			GeneralService gs = new GeneralService(mapComponent, manager, markerManager);
			RouteService rs = new RouteService(mapComponent, markerManager);
			//System.out.println("in map ready : " + this.getClass());
			// initialize controllers
			new RouteController(rs, routeButton, hideRouteButton, resetButton, startButton, destinationButton, group, searchOptions, visualizationButton,
					startLabel, endLabel, pointLabel, manager, markerManager);
			new FetchController(gs, rs, tf, fetchButton, cb, displayButton);
		});

		// add components to border pane
		bp.setRight(tp);
		bp.setBottom(fetchControls);
		bp.setCenter(mapComponent);

		Scene scene = new Scene(bp);
		scene.getStylesheets().add("html/routing.css");
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	@Override
	public void mapInitialized() {

		LatLong center = new LatLong(32.8810, -117.2380);


		// set map options
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
		.zoom(14)
		.zoomControl(true);

		// create map;
		map = mapComponent.createMap(options);
		setupJSAlerts(mapComponent.getWebView());



	}


	// SETTING UP THE VIEW

	private HBox getBottomBox(TextField tf, Button fetchButton) {
		HBox box = new HBox();
		tf.setPrefWidth(FETCH_COMPONENT_WIDTH);
		box.getChildren().add(tf);
		fetchButton.setPrefWidth(FETCH_COMPONENT_WIDTH);
		box.getChildren().add(fetchButton);
		return box;
	}
	/**
	 * Setup layout and controls for Fetch tab
	 * @param fetchTab
	 * @param fetchButton
	 * @param displayButton
	 * @param tf
	 */
	private VBox getFetchBox(Button displayButton, ComboBox<DataSet> cb) {
		// add button to tab, rethink design and add V/HBox for content
		VBox v = new VBox();
		HBox h = new HBox();



		HBox intersectionControls = new HBox();
		//        cb.setMinWidth(displayButton.getWidth());
		cb.setPrefWidth(FETCH_COMPONENT_WIDTH);
		intersectionControls.getChildren().add(cb);
		displayButton.setPrefWidth(FETCH_COMPONENT_WIDTH);
		intersectionControls.getChildren().add(displayButton);

		h.getChildren().add(v);
		v.getChildren().add(new Label("Choose map file : "));
		v.getChildren().add(intersectionControls);

		//v.setSpacing(MARGIN_VAL);
		return v;
	}

	/**	
	 * Setup layout of route tab and controls
	 *
	 * @param routeTab
	 * @param box
	 */
	private void setupRouteTab(Tab routeTab, VBox fetchBox, Label startLabel, Label endLabel, Label pointLabel,
			Button showButton, Button hideButton, Button resetButton, Button vButton, Button startButton,
			Button destButton, List<RadioButton> searchOptions) {

		//set up tab layout
		HBox h = new HBox();
		// v is inner container
		VBox v = new VBox();
		h.getChildren().add(v);

		VBox selectLeft = new VBox();

		selectLeft.getChildren().add(startLabel);
		HBox startBox = new HBox();
		startBox.getChildren().add(startLabel);
		startBox.getChildren().add(startButton);
		startBox.setSpacing(20);

		HBox destinationBox = new HBox();
		destinationBox.getChildren().add(endLabel);
		destinationBox.getChildren().add(destButton);
		destinationBox.setSpacing(20);

		VBox markerBox = new VBox();
		Label markerLabel = new Label("Selected Marker : ");

		markerBox.getChildren().add(markerLabel);

		markerBox.getChildren().add(pointLabel);

		VBox.setMargin(markerLabel, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(pointLabel, new Insets(0,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(fetchBox, new Insets(0,0,MARGIN_VAL*2,0));


		HBox showHideBox = new HBox();
		showHideBox.getChildren().add(showButton);
		showHideBox.getChildren().add(hideButton);
		showHideBox.setSpacing(2*MARGIN_VAL);

		v.getChildren().add(fetchBox);
		v.getChildren().add(new Label("Start Position : "));
		v.getChildren().add(startBox);
		v.getChildren().add(new Label("Goal : "));
		v.getChildren().add(destinationBox);
		v.getChildren().add(showHideBox);
		for (RadioButton rb : searchOptions) {
			v.getChildren().add(rb);
		}
		v.getChildren().add(vButton);
		VBox.setMargin(showHideBox, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		VBox.setMargin(vButton, new Insets(MARGIN_VAL,MARGIN_VAL,MARGIN_VAL,MARGIN_VAL));
		vButton.setDisable(true);
		v.getChildren().add(markerBox);
		v.getChildren().add(resetButton);

		routeTab.setContent(h);


	}

	private void setupJSAlerts(WebView webView) {
		webView.getEngine().setOnAlert( e -> {
			Stage popup = new Stage();
			popup.initOwner(primaryStage);
			popup.initStyle(StageStyle.UTILITY);
			popup.initModality(Modality.WINDOW_MODAL);

			StackPane content = new StackPane();
			content.getChildren().setAll(
					new Label(e.getData())
					);
			content.setPrefSize(200, 100);

			popup.setScene(new Scene(content));
			popup.showAndWait();
		});
	}

	private LinkedList<RadioButton> setupToggle(ToggleGroup group) {

		// Use Dijkstra as default
		RadioButton rbD = new RadioButton("Dijkstra (includes Speed Limits)");
		rbD.setUserData("Dijkstra");
		rbD.setSelected(true);

		RadioButton rbA = new RadioButton("A*");
		rbA.setUserData("A*");

		RadioButton rbB = new RadioButton("BFS");
		rbB.setUserData("BFS");

		rbB.setToggleGroup(group);
		rbD.setToggleGroup(group);
		rbA.setToggleGroup(group);
		return new LinkedList<RadioButton>(Arrays.asList(rbB, rbD, rbA));
	}


	/*
	 * METHODS FOR SHOWING DIALOGS/ALERTS
	 */

	public void showLoadStage(Stage loadStage, String text) {
		loadStage.initModality(Modality.APPLICATION_MODAL);
		loadStage.initOwner(primaryStage);
		VBox loadVBox = new VBox(20);
		loadVBox.setAlignment(Pos.CENTER);
		Text tNode = new Text(text);
		tNode.setFont(new Font(16));
		loadVBox.getChildren().add(new HBox());
		loadVBox.getChildren().add(tNode);
		loadVBox.getChildren().add(new HBox());
		Scene loadScene = new Scene(loadVBox, 300, 200);
		loadStage.setScene(loadScene);
		loadStage.show();
	}

	public static void showInfoAlert(String header, String content) {
		Alert alert = getInfoAlert(header, content);
		alert.showAndWait();
	}

	public static Alert getInfoAlert(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static void showErrorAlert(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("File Name Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
