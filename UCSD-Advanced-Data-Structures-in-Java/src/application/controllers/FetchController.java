package application.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import com.sun.javafx.geom.Rectangle;

import application.DataSet;
import application.MapApp;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.LatLongBounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import mapmaker.MapMaker;

public class FetchController {
    private static final int ROW_COUNT = 5;
    private GeneralService generalService;
    private RouteService routeService;
    private Node container;
    private Button fetchButton;
    private Button displayButton;
    private ComboBox<DataSet> dataChoices;
    // maybe choice map
    private TextField writeFile;
    private String filename = "data.map";

    // path for mapfiles to load when program starts
    private String persistPath = "data/maps/mapfiles.list";


    public FetchController(GeneralService generalService, RouteService routeService, TextField writeFile,
    					   Button fetchButton, ComboBox<DataSet> cb, Button displayButton) {
        this.generalService = generalService;
        this.routeService = routeService;
        this.fetchButton = fetchButton;
        this.displayButton = displayButton;
        this.writeFile = writeFile;
        dataChoices = cb;
        setupComboCells();
        setupFetchButton();
        setupDisplayButton();
        loadDataSets();

    }

    private void loadDataSets() {
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(persistPath));
            String line = reader.readLine();
            while(line != null) {
            	dataChoices.getItems().add(new DataSet(GeneralService.getDataSetDirectory() + line));
                line = reader.readLine();
            }

            reader.close();
		} catch (IOException e) {
            // System.out.println("No existing map files found.");
			e.printStackTrace();
		}
    }
    private void setupComboCells() {
    	//dataChoices.setVisibleRowCount(ROW_COUNT);
    	dataChoices.setCellFactory(new Callback<ListView<DataSet>, ListCell<DataSet>>() {
        	@Override public ListCell<DataSet> call(ListView<DataSet> p) {
        		return new ListCell<DataSet>() {
        			{
                        super.setPrefWidth(100);
                        //getItem().getFileName());

        			}

                    @Override
                    protected void updateItem(DataSet item, boolean empty) {
                        super.updateItem(item, empty);
                    	if(empty || item == null) {
                            super.setText("None.");
                    	}
                    	else {
                        	super.setText(item.getFilePath().substring(GeneralService.getDataSetDirectory().length()));

                    	}
                    }
        		};

        	}
    	});

        dataChoices.setButtonCell(new ListCell<DataSet>() {
        	@Override
        	protected void updateItem(DataSet t, boolean bln) {
        		super.updateItem(t,  bln);
        		if(t!=null) {
        			setText(t.getFilePath().substring(GeneralService.getDataSetDirectory().length()));
        		}
        		else {
        			setText("Choose...");
        		}
        	}
        });
    }

    /**
     * Registers event to fetch data
     */
    private void setupFetchButton() {
    	fetchButton.setOnAction(e -> {
    		String fName = writeFile.getText();

    		// check for valid file name ___.map or mapfiles/___.map
    		if((generalService.checkDataFileName(fName)) != null) {
    			if (!generalService.checkBoundsSize(.1)) {
    				Alert alert = new Alert(AlertType.ERROR);
        			alert.setTitle("Size Error");
        			alert.setHeaderText("Map Size Error");
        			alert.setContentText("Map boundaries are too large.");
        			alert.showAndWait();
    			} else if (!generalService.checkBoundsSize(0.02)) {
                	Alert warning = new Alert(AlertType.CONFIRMATION);
                	warning.setTitle("Size Warning");
                	warning.setHeaderText("Map Size Warning");
                	warning.setContentText("Your map file may take a long time to download,\nand your computer may crash when you try to\nload the intersections. Continue?");
                	warning.showAndWait().ifPresent(response -> {
                		if (response == ButtonType.OK) {
                			generalService.runFetchTask(generalService.checkDataFileName(fName), dataChoices, fetchButton);
                		}
                	});
                } else {
                	generalService.runFetchTask(generalService.checkDataFileName(fName), dataChoices, fetchButton);
                }


    		}
    		else {
    		    Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Filename Error");
    			alert.setHeaderText("Input Error");
    			alert.setContentText("Check filename input. \n\n\n"
    								 + "Filename must match format : [filename].map."
    								 + "\n\nUse only uppercase and lowercase letters,\nnumbers, and underscores in [filename].");

    			alert.showAndWait();
    		}
    	});
    }

    /**
     * Registers event to fetch data
     */
    private void setupDisplayButton() {
    	displayButton.setOnAction( e -> {
            // System.out.println("In setup display button");
            DataSet dataSet = dataChoices.getValue();

            // was any dataset selected?
            if(dataSet == null) {
    		    Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Display Error");
    			alert.setHeaderText("Invalid Action :" );
    			alert.setContentText("No map file has been selected for display.");
    			alert.showAndWait();
            }
            else if(!dataSet.isDisplayed()) {
            	// TODO -- only time I need route service ....redo?
                if(routeService.isRouteDisplayed()) {
                	routeService.hideRoute();
                }
        		generalService.displayIntersections(dataSet);

            }
            else {
    		    Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Display Info");
    			alert.setHeaderText("Intersections Already Displayed" );
    			alert.setContentText("Data set : " + dataSet.getFilePath() + " has already been loaded.");
    			alert.showAndWait();
            }

            // TO TEST : only using test.map for intersections
        	//generalService.displayIntersections(new DataSet("my.map"));
    	});
    }




}
