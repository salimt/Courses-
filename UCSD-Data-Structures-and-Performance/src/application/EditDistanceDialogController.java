package application;

import java.util.List;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditDistanceDialogController {
	private Stage dialogStage;
	private MainApp mainApp;
	
	
	@FXML
	private TextField word1;
	
	@FXML
	private TextField word2;
	
	@FXML
	private Button okButton;
    
	
	@FXML
	private void initialize() {
       okButton.setDefaultButton(true);
	}
	
	/**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setField(String text) {
        word1.setText(text);
    }
    
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if(isInputValid()) {
    		
    		Task<List<String>> task = new Task<List<String>>() {
    	        @Override
    	        public List<String> call() {
    	        	// get word path
    	        	LaunchClass launch = new LaunchClass();
    	        	spelling.WordPath wp = launch.getWordPath();
    	    		List<String> path = wp.findPath(word1.getText(), word2.getText());
                    return path;
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
    			dialogStage.close();
    			mainApp.showLoadStage(loadStage, "Finding word path...");
    		});
    		
    		// findPath done executing, close loading dialog, show results
    	    task.setOnSucceeded(e -> {
    	    	loadStage.close();
	    		mainApp.showEDResult(task.getValue());
    	    });
    	    
    	   Thread thread  = new Thread(task);
    	   thread.start();
    		
    	}
    	else {
    		// display error pop-up
    		mainApp.showInputErrorDialog("You must input two words for Edit Distance.");
    	}
            
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * 
     * @param mainApp
     */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
    
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    private boolean isInputValid() {
    	return !(word1.getText().equals("") || word2.getText().equals(""));
    }

}
