package application;

import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TextProController {

	
	private final static double DEFAULT_SPACING = 55;
	private final static double CONTROL_HEIGHT = 132;
	private final static double SPACE_DIV = 8.5;
	private final static double BUTTON_WIDTH = 160.0;
	private final static double RBOX_THRESHOLD = 520;	 // threshold to change spacing of right VBox
	
	
	// used when showing new stage/scene
	private MainApp mainApp;
	
	// used for getting new objects
	private LaunchClass launch;
	
	// UI Controls
	private AutoSpellingTextArea textBox;
	
	@FXML
	private VBox leftPane;
	
	@FXML
	private VBox rightBox;
	
	@FXML
	private HBox container;
	
	@FXML
	private Label fLabel;
	
	@FXML
	private Pane bufferPane;
	
	@FXML
	private TextField fleschField;
	
	@FXML
	private CheckBox autocompleteBox;
	
	@FXML 
	private CheckBox spellingBox;
	
	//private Node 
	
	
	
	
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * 
     * Initialize and add text area to application
     */
	@FXML
	private void initialize() {
		// make field displaying flesch score read-only
		fleschField.setEditable(false);
		
		
		launch = new LaunchClass();
		
		// instantiate and add custom text area
		spelling.Dictionary dic = launch.getDictionary();
		textBox = new AutoSpellingTextArea(launch.getAutoComplete(), launch.getSpellingSuggest(dic), dic);
		textBox.setPrefSize(570, 492);
		textBox.setStyle("-fx-font-size: 14px");
		
		
		textBox.setWrapText(true);
		
		
		// add text area as first child of left VBox
		ObservableList<Node> nodeList = leftPane.getChildren();
		Node firstChild = nodeList.get(0);
		nodeList.set(0, textBox);
		nodeList.add(firstChild);
		
		VBox.setVgrow(textBox, Priority.ALWAYS);
		
		
		
		// ADD LISTENERS FOR ADJUSTING ON RESIZE
		
		container.widthProperty().addListener(li -> {
			
			if((container.getWidth() - leftPane.getPrefWidth()) < BUTTON_WIDTH) {
				rightBox.setVisible(false);
			}
			else {
				rightBox.setVisible(true);
			}
		});
		
		// function for setting spacing of rightBox
		Consumer<VBox> adjustSpacing = box ->  {
			if(container.getHeight() < RBOX_THRESHOLD) {
				rightBox.setSpacing((container.getHeight() - CONTROL_HEIGHT)/SPACE_DIV);
			}
			else {
				rightBox.setSpacing(DEFAULT_SPACING);
			}
		};
		
		container.heightProperty().addListener(li -> {
			adjustSpacing.accept(rightBox);
		});
		
		rightBox.visibleProperty().addListener( li -> {
			if(rightBox.isVisible()) {
				 container.getChildren().add(rightBox);
				 adjustSpacing.accept(rightBox);
			 }
			 else {
				 container.getChildren().remove(rightBox);
			 }	 
		});
	}
	
	
	
	
	/**
     * Is called by the main application to give a reference back to itself.
     * Also give reference to AutoSpellingTextArea
     * 
     * 
     * @param mainApp
     */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
	private void handleFleschIndex() {
		String text = textBox.getText();
		double fIndex = 0;
		
		// check if text input
		if(!text.equals("")) {
			
			// create Document representation of  current text
			document.Document doc = launch.getDocument(text);
			
			fIndex = doc.getFleschScore();
			
			//get string with two decimal places for index to
			String fString = String.format("%.2f", fIndex);
			
			// display string in text field
			fleschField.setText(fString);
			
		}
		else {
			// reset text field
			fleschField.setText("");
			mainApp.showInputErrorDialog("No text entered.");
			
		}
		
	}
	

	
	@FXML
	private void handleLoadText() {
		//return string??
		mainApp.showLoadFileDialog(textBox);
		
		
		//textBox.appendText(text);
		
		
	}
	
	@FXML
	private void handleEditDistance() {
		String selectedText = textBox.getSelectedText();
		mainApp.showEditDistanceDialog(selectedText);
		
	}
	
	@FXML
	private void handleMarkovText() {
		// get MTG object
		textgen.MarkovTextGenerator mtg = launch.getMTG();
		
		Task<textgen.MarkovTextGenerator> task = new Task<textgen.MarkovTextGenerator>() {
	        @Override
	        public textgen.MarkovTextGenerator call() {
	            // process long-running computation, data retrieval, etc...

	            mtg.retrain(textBox.getText());
	            return mtg;
	        }
		};
		
		// stage for load dialog
		final Stage loadStage = new Stage();
		
		// consume close request until task is finished
		loadStage.setOnCloseRequest( e -> {
			if(!task.isDone()) {
				e.consume();
			}
		});

		
		// show loading dialog when task is running
		task.setOnRunning( e -> {
			mainApp.showLoadStage(loadStage, "Training MTG...");
		});
		
		// MTG trained, close loading dialog, show MTG dialog
	    task.setOnSucceeded(e -> {
	    	loadStage.close();
	        textgen.MarkovTextGenerator result = task.getValue();
	        mainApp.showMarkovDialog(result);
	    });
	    
	   Thread thread  = new Thread(task);
	   thread.start();
	  
		
		
	}
	
	
	
	@FXML
	private void handleAutoComplete() {
		if(autocompleteBox.isSelected()) {
			textBox.setAutoComplete(true);
		}
		else {
			textBox.setAutoComplete(false);
		}
	}
	
	@FXML
	private void handleSpelling() {
		if(spellingBox.isSelected()) {
			textBox.setSpelling(true);
		}
		else {
			textBox.setSpelling(false);
		}
		
	}
	
	
	@FXML
	private void handleClear() {
		textBox.clear();
	}
	
	
}
