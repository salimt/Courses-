package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import textgen.MarkovTextGenerator;

public class MarkovController {
	private Stage dialogStage;
	private MainApp mainApp;
	private MarkovTextGenerator mtg;

	
	@FXML
	private TextField numWordsField;
	
	@FXML
	private TextArea resultBox;
	
	
	@FXML
	private void initialize() {
	}
	
	/**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks OK.
     */
    @FXML
    private void handleGenerate() {
    	if(isInputValid()) {
    		String mText = mtg.generateText(Integer.parseInt(numWordsField.getText()));
    		setResult(mText);
    	}
    	else {
    		// display error pop-up
    		mainApp.showInputErrorDialog("Invalid input.\nMust enter number > 0.");
    	}
            
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	public void setMTG(textgen.MarkovTextGenerator mtg) {
		this.mtg = mtg;
	}
    
	public void setResult(String result) {
		resultBox.setText(result);
	}
	
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
    
    
    /**
     * Checks if number of words input is valid.
     * 
     * valid == Not empty, numeric, >0
     * 
     * @return true if valid, false if not
     */
    private boolean isInputValid() {
    	String numString = numWordsField.getText();
    	return !(numString.equals("") || !isInteger(numString)
    			|| (Integer.parseInt(numString) <= 0));
    }
    
    /**
     * Checks if string is integer
     * 
     * @param str
     * @return true if string is able to be parsed as an integer.
     */
    public static boolean isInteger(String str) {  
        try  {  
            Integer.parseInt(str);  
        }  
        catch(NumberFormatException nfe) {  
            return false;  
        } 

        return true;  
    }

}
